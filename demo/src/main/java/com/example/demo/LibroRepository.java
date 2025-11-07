package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

// No hace falta @Repository: JpaRepository ya lo marca como componente
public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Si quieres queries: List<Libro> findByAutor(String autor);
}
