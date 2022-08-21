package com.cursoSpring.curso.UsuarioService;

import com.cursoSpring.curso.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUsuarioService {
    List<Usuario> getUsuarios();
    Usuario obtenerUsuarioPorEmail(String email);
    void eliminar(Long id);
    void registrar(Usuario usuario);
    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}

