package com.example.demo.service;

import com.example.demo.modelo.Usuario;
import com.example.demo.DTO.UsuarioDTO;
import com.example.demo.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Convertir de Usuario a UsuarioDTO
    public UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getNombre(),
            usuario.getContrasena(),
            usuario.isAdmin()
        );
    }

    // Convertir de UsuarioDTO a Usuario
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        return new Usuario(
            usuarioDTO.getId(),
            usuarioDTO.getEmail(),
            usuarioDTO.getNombre(),
            usuarioDTO.getContrasena(),
            usuarioDTO.isAdmin()
        );
    }

    // Obtener Usuario por ID
    public UsuarioDTO getUsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepositorio.findById(id);
        return usuario.map(this::toDTO).orElse(null); // Retorna el DTO si se encuentra el usuario
    }

    // Obtener Usuario por Email
    public UsuarioDTO getUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        return toDTO(usuario); // Devuelve el DTO
    }

    // Crear un Usuario
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = toEntity(usuarioDTO);
        usuario = usuarioRepositorio.save(usuario);
        return toDTO(usuario); // Devuelve el DTO después de ser guardado
    }

    // Eliminar Usuario por ID
    public void deleteUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    // Obtener todos los usuarios
    public Iterable<UsuarioDTO> findAll() {
        Iterable<Usuario> usuarios = usuarioRepositorio.findAll();
        return toDTOList(usuarios);
    }

    // Convertir Iterable<Usuario> a Iterable<UsuarioDTO>
    private Iterable<UsuarioDTO> toDTOList(Iterable<Usuario> usuarios) {
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOList.add(toDTO(usuario));
        }
        return usuarioDTOList;
    }
    
    // Método para actualizar un usuario por ID
    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> optionalUsuario = usuarioRepositorio.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setContrasena(usuarioDTO.getContrasena());
            usuario.setAdmin(usuarioDTO.isAdmin());
            usuarioRepositorio.save(usuario);
            return toDTO(usuario);
        } else {
            return null;
        }
    }
    
    public UsuarioDTO authenticate(String email, String contrasena) {
        Usuario usuario = usuarioRepositorio.findByEmailAndContrasena(email, contrasena);
        if (usuario != null) {
            return new UsuarioDTO(usuario);
        }
        return null;
    }
}