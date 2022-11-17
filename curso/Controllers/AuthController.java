package com.cursojava.curso.Controllers;

import com.cursojava.curso.Dao.UsuarioDao;
import com.cursojava.curso.Models.Usuario;
import com.cursojava.curso.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario user){
        Usuario usuario = usuarioDao.verificar(user);
       if( usuario!=null ){

           String response= jwtUtil.create(String.valueOf(usuario.getId()),user.getEmail());

           return response;
       }
        return "FAIL";
    }


}
