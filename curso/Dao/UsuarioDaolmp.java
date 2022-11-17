package com.cursojava.curso.Dao;

import com.cursojava.curso.Models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaolmp implements UsuarioDao{

    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
        String query ="FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public void eliminar(Long id){
        Usuario usuario= entityManager.find(Usuario.class,id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario user) {
        System.out.println("informacion usuario");
        System.out.println(user.getNombre());
        System.out.println(user.getApellido());
        System.out.println(user.getEmail());
        entityManager.merge(user);
    }

    @Override
    public Usuario verificar(Usuario user) {
        String query ="FROM Usuario where email= :email";
         List<Usuario> lista =entityManager.createQuery(query)
                 .setParameter("email",user.getEmail())
                 .getResultList();

         if(lista.isEmpty()){return null;}

        String passwordHased = lista.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        if(argon2.verify(passwordHased,user.getPassword())){
            return lista.get(0);
        }

        return null;
    }


}
