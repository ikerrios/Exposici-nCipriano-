package graphql;

import com.cipriano.demo.domain.Libro;
import com.cipriano.demo.domain.Prestamo;
import com.cipriano.demo.repo.*;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BibliotecaGraphql {
    private final LibroRepository libroRepo;
    private final AutorRepository autorRepo;
    private final CategoriaRepository catRepo;
    private final PrestamoRepository prestamoRepo;
    private final UsuarioRepository usuarioRepo;

    public BibliotecaGraphql(LibroRepository libroRepo, AutorRepository autorRepo,
                             CategoriaRepository catRepo, PrestamoRepository prestamoRepo,
                             UsuarioRepository usuarioRepo) {
        this.libroRepo = libroRepo; this.autorRepo = autorRepo; this.catRepo = catRepo;
        this.prestamoRepo = prestamoRepo; this.usuarioRepo = usuarioRepo;
    }

    @QueryMapping
    public List<Libro> libros(@Argument String titulo,
                                                      @Argument Long autorId,
                                                      @Argument Long categoriaId){
        if (titulo!=null) return libroRepo.findByTituloContainingIgnoreCase(titulo);
        if (autorId!=null) return libroRepo.findByAutor_Id(autorId);
        if (categoriaId!=null) return libroRepo.findByCategoria_Id(categoriaId);
        return libroRepo.findAll();
    }

    @QueryMapping
    public com.example.demo.domain.Libro libro(@Argument Long id){ return libroRepo.findById(id).orElse(null); }

    @QueryMapping
    public List<com.example.demo.domain.Autor> autores(){ return autorRepo.findAll(); }

    @QueryMapping
    public List<Prestamo> prestamos(@Argument Long usuarioId){
        return (usuarioId!=null) ? prestamoRepo.findByUsuario_Id(usuarioId) : prestamoRepo.findAll();
    }

    @MutationMapping
    public Prestamo crearPrestamo(@Argument Long usuarioId, @Argument Long libroId){
        var libro = libroRepo.findById(libroId).orElseThrow();
        var usuario = usuarioRepo.findById(usuarioId).orElseThrow();
        if(!libro.isDisponible()) throw new IllegalStateException("Libro no disponible");
        libro.setDisponible(false);
        return prestamoRepo.save(Prestamo.builder().libro(libro).usuario(usuario).fechaInicio(LocalDate.now()).build());
    }

    @MutationMapping
    public Prestamo devolverPrestamo(@Argument Long id){
        var p = prestamoRepo.findById(id).orElseThrow();
        p.setDevuelto(true); p.setFechaFin(LocalDate.now());
        p.getLibro().setDisponible(true);
        return prestamoRepo.save(p);
    }
}
