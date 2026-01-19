package com.projetozero.usuario.business;


import com.projetozero.usuario.infrastructure.entity.Usuario;
import com.projetozero.usuario.infrastructure.exception.ConflictException;
import com.projetozero.usuario.infrastructure.exception.ResourceNotFoundException;
import com.projetozero.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//Usar no lugar do autowired pra injeção de dep. porem só atrib com final garantindo a imutabilidade
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario salvarUsuario(Usuario usuario) {
        try {
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);

        } catch (ConflictException e) {
            throw new ConflictException("Email já existe", e.getCause());
        }

    }

    public void emailExiste(String email) {
        try {

            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já existe" + email);
            }

        } catch (ConflictException e) {
            throw new ConflictException("Email já existe", e.getCause());
        }

    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void  deleterUsuarioPoremail(String email) {
         usuarioRepository.deleteByEmail(email);
    }
}
