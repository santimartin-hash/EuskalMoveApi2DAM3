package com.example.demo.DTO;



import com.example.demo.modelo.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String email;
    private String nombre;
    private String contrasena;
    private boolean Admin;
    
    // Constructor that accepts a Usuario object
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nombre = usuario.getNombre();
        this.contrasena = usuario.getContrasena();
        this.Admin = usuario.isAdmin();
    }
}
