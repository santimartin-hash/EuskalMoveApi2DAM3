
package com.example.demo.controller;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.DTO.UsuarioDTO;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuario Controller", description = "Controlador para manejar los usuarios de la base de datos")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Obtener una lista de todos los usuarios",
               description = "Este endpoint devuelve una lista de todos los usuarios.")
    @GetMapping
    public ResponseEntity<Iterable<UsuarioDTO>> getUsuarios() {
        Iterable<UsuarioDTO> usuarios = usuarioService.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un usuario por ID",
               description = "Este endpoint devuelve un usuario específico por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(
            @Parameter(description = "ID del usuario a obtener", required = true) @PathVariable Long id) {
        UsuarioDTO usuarioDTO = usuarioService.getUsuarioById(id);
        if (usuarioDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un usuario por email",
               description = "Este endpoint devuelve un usuario específico por su email.")
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> getUsuarioByEmail(
            @Parameter(description = "Email del usuario a obtener", required = true) @PathVariable String email) {
        UsuarioDTO usuarioDTO = usuarioService.getUsuarioByEmail(email);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo usuario",
               description = "Este endpoint permite crear un nuevo usuario en la base de datos.")
    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(
            @Parameter(description = "Objeto Usuario para almacenar en la base de datos", required = true) @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO createdUsuario = usuarioService.createUsuario(usuarioDTO);
        return new ResponseEntity<>(createdUsuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar un usuario por ID",
               description = "Este endpoint permite eliminar un usuario específico por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar", required = true) @PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Actualizar un usuario existente",
               description = "Este endpoint permite actualizar un usuario existente en la base de datos.")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Objeto Usuario actualizado", required = true) @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO updatedUsuario = usuarioService.updateUsuario(id, usuarioDTO);
        if (updatedUsuario != null) {
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Autenticar un usuario",
            description = "Este endpoint permite autenticar un usuario con su email y contraseña.")
 @PostMapping("/login")
 public ResponseEntity<LoginResponseDTO> login(
         @Parameter(description = "Objeto Login con email y contraseña", required = true) @RequestBody LoginDTO loginDTO) {
     UsuarioDTO usuarioDTO = usuarioService.authenticate(loginDTO.getEmail(), loginDTO.getContrasena());
     if (usuarioDTO != null) {
         LoginResponseDTO responseDTO = new LoginResponseDTO();
         responseDTO.setEmail(usuarioDTO.getEmail());
         responseDTO.setNombre(usuarioDTO.getNombre());
         responseDTO.setAdmin(usuarioDTO.isAdmin());
         responseDTO.setStatus("OK");
         return new ResponseEntity<>(responseDTO, HttpStatus.OK);
     } else {
         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
 }
}
