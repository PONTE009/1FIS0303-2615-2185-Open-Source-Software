package upc.service.taller.alumno.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import upc.service.taller.alumno.entity.UsuarioEntity;
import upc.service.taller.alumno.repository.UsuarioRepository;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
                usuario.getUserName(),
                usuario.getPassword(),
                Boolean.TRUE.equals(usuario.getActivo()),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
