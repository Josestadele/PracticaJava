package com.cursojava.curso.Controllers;

import com.cursojava.curso.Dao.UsuarioDao;
import com.cursojava.curso.Models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value="api/usuario/{id}")
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario= new Usuario();
        usuario.setId(id);
        usuario.setNombre("Jose");
        usuario.setApellido("Gonzalez");
        usuario.setEmail("josestadele13@gmail.com");
        usuario.setPassword("123456789@");
        usuario.setTelefono("04242232774");

        return usuario;
    }

    @RequestMapping(value="api/usuario" , method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token){
        String userID=jwtUtil.getKey(token);

        if (userID==null){
            return new ArrayList<>();
        }


        return usuarioDao.getUsuarios();

    }

    @RequestMapping(value="api/usuario" , method = RequestMethod.POST)
    public void registrarUsuario ( @RequestBody Usuario usuario){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);

    }

    @RequestMapping(value="api/usuario/{id}", method = RequestMethod.DELETE)
    public void eliminar (@PathVariable Long id){
        usuarioDao.eliminar(id);
    }
}
