package com.cursojava.curso.Dao;

import com.cursojava.curso.Models.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> getUsuarios();

    void eliminar(Long id)  ;


    void registrar(Usuario user);

    Usuario verificar(Usuario user);
}
