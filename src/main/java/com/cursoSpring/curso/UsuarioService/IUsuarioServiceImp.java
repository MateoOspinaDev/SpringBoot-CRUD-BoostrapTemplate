package com.cursoSpring.curso.UsuarioService;


import com.cursoSpring.curso.models.Usuario;
import com.cursoSpring.curso.repository.UsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IUsuarioServiceImp implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
    @Override
    public void registrar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        Usuario usuarioDB = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioDB == null) {
            return null;
        }

        String passwordHashed = usuarioDB.getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, usuario.getPassword())) {
            return usuarioDB;
        }
        return null;
    }
}

