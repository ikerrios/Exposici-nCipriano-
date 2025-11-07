package demolist.biblioteca.graphql;

import demolist.biblioteca.model.Libro;
import demolist.biblioteca.repository.LibroRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class LibroResolver {

    private final LibroRepository repo;

    public LibroResolver(LibroRepository repo) {
        this.repo = repo;
    }

    @QueryMapping
    public java.util.List<Libro> libros() {
        return repo.findAll();
    }

    @QueryMapping
    public Libro libro(@Argument Long id) {
        return repo.findById(id).orElse(null);
    }

    @MutationMapping
    public Libro crearLibro(@Argument String nombre, @Argument String autor, @Argument String descripcion) {
        return repo.save(new Libro(null, nombre, autor, descripcion));
    }

    @MutationMapping
    public Libro actualizarLibro(@Argument Long id, @Argument String nombre, @Argument String autor, @Argument String descripcion) {
        return repo.findById(id).map(l -> {
            if (nombre != null) l.setNombre(nombre);
            if (autor != null) l.setAutor(autor);
            if (descripcion != null) l.setDescripcion(descripcion);
            return repo.save(l);
        }).orElse(null);
    }

    @MutationMapping
    public Boolean eliminarLibro(@Argument Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}