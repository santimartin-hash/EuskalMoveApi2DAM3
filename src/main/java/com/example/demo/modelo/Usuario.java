package com.example.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.example.demo.DTO.UsuarioDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id;

    @Column(length = 100, unique = true, nullable = false) // El email debe ser único y no nulo
    private String email;

    @Column(nullable = false) // El nombre no debe ser nulo
    private String nombre;

    @Column(nullable = false) // La contraseña no debe ser nula
    private String contrasena;

    @Column(nullable = false) // El rol no debe ser nulo
    private boolean Admin; // Puede ser "ADMIN", "USER", etc.
    
    public Usuario(UsuarioDTO usuarioDTO) {
        this.id = usuarioDTO.getId();
        this.email = usuarioDTO.getEmail();
        this.nombre = usuarioDTO.getNombre();
        this.contrasena = usuarioDTO.getContrasena();
        this.Admin = usuarioDTO.isAdmin();
    }
}
