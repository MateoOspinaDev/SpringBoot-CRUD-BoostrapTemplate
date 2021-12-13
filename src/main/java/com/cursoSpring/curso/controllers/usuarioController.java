package com.cursoSpring.curso.controllers;
//Controlador, encargado de crear las rutas del back end
import com.cursoSpring.curso.dao.UsuarioDao;
import com.cursoSpring.curso.models.Usuario;
import com.cursoSpring.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class usuarioController {

    @Autowired //Inyectamos el usuarioDaoImp
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value="api/usuario/{id}")//el servidor asignará el id que pongamos después del "/"
    public Usuario getUsuario(@PathVariable long id){//creamos una variable dinámica para la ruta
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Mateo");
        usuario.setApellido("Ospina");
        usuario.setEmail("dak.ospina@outlook.com");
        usuario.setTelefono("3265498");
        return usuario;
    }
    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token) {
        if (!validarToken(token)) { return null; }

        return usuarioDao.getUsuarios();
    }


    @RequestMapping(value="api/usuario45")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setApellido("Mateo");
        usuario.setApellido("Ospina");
        usuario.setEmail("dak.ospina@outlook.com");
        usuario.setTelefono("3265498");
        return usuario;
    }

    @RequestMapping(value="api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization") String token,
                         @PathVariable Long id) {//Crearemos un metodo para eliminar un usuario
        if (!validarToken(token)) { return ; }
        usuarioDao.eliminar(id);
    }

    private boolean validarToken(String token){
        String usuarioId=jwtUtil.getKey(token);
        return usuarioId != null;
    }

    @RequestMapping(value="api/usuarios", method = RequestMethod.POST)
    public void registrarUsuarios(@RequestBody Usuario usuario){//Esta anotación convierte el JSON que recibe en objeto usuario
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);//variable que hará el proceso de encriptacion
        String hash=argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);//LA contraseña ahora será el valor encriptado
        usuarioDao.registrar(usuario);
    }


}
