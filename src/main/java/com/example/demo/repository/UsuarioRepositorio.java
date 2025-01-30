package com.example.demo.repository;

import com.example.demo.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	
    Usuario findByEmail(String email);
    
    Usuario findByEmailAndContrasena(String email, String contrasena);
}
