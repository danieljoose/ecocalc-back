package br.com.ecocalc.resolvers.mutation;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.services.*;
import br.com.ecocalc.repositories.*;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;

import java.util.Optional;


@Component
public class UsuarioMutationResolver implements  GraphQLMutationResolver{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Usuario cadastrarUsuario(String nome, String sobrenome, String email, String senha) {
        return usuarioService.cadastrarUsuario(nome, sobrenome, email, senha);
    }

}