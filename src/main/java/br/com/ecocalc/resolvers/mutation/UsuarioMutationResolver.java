package br.com.ecocalc.resolvers.mutation;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import br.com.ecocalc.domains.*;
import br.com.ecocalc.services.*;
import br.com.ecocalc.repositories.*;
import br.com.ecocalc.services.AuthenticationService;
import br.com.ecocalc.payload.response.JwtResponse;
import br.com.ecocalc.security.jwt.JwtUtils;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.GraphQLException;

import java.util.Optional;
import java.time.OffsetDateTime;


@Component
public class UsuarioMutationResolver implements  GraphQLMutationResolver{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
	private AuthenticationService authenticationService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UsuarioService usuarioService;

    public Usuario cadastrarUsuario(String nome, String sobrenome, String email, String senha) {
        return usuarioService.cadastrarUsuario(nome, sobrenome, email, senha);
    }

    public JwtResponse signin(String login, String senha) {
        try {
			JwtResponse jwtResponse = authenticationService.authenticateUser(login, senha);
            Optional<Usuario> optionalUsuario = usuarioService.findByEmail(login);

            if(!optionalUsuario.isPresent()){
                throw new GraphQLException("user_not_found");
            }

            Usuario usuario = optionalUsuario.get();
            usuarioService.salvar(usuario);
            
            return jwtResponse;
		} catch (Exception e) {
			throw new GraphQLException("bad_credentials");
		}
    }

    public Usuario registerUltimoAcesso(Long id){
        return usuarioService.registerUltimoAcesso(id);
    }
}