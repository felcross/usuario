package com.projetozero.usuario.business;


import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import com.projetozero.usuario.controller.dtos.EnderecoDTO;
import com.projetozero.usuario.controller.dtos.TelefoneDTO;
import com.projetozero.usuario.controller.dtos.UsuarioConverter;
import com.projetozero.usuario.controller.dtos.UsuarioDTO;
import com.projetozero.usuario.infrastructure.entity.Endereco;
import com.projetozero.usuario.infrastructure.entity.Telefone;
import com.projetozero.usuario.infrastructure.entity.Usuario;
import com.projetozero.usuario.infrastructure.exception.ConflictException;
import com.projetozero.usuario.infrastructure.exception.ResourceNotFoundException;
import com.projetozero.usuario.infrastructure.repository.EnderecoRepository;
import com.projetozero.usuario.infrastructure.repository.TelefoneRepository;
import com.projetozero.usuario.infrastructure.repository.UsuarioRepository;
import com.projetozero.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//Usar no lugar do autowired pra injeção de dep. porem só atrib com final garantindo a imutabilidade
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        try {
            emailExiste(usuarioDTO.getEmail());
            usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

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




    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try{
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado" + email)));
        }
        catch(ResourceNotFoundException e)
        {throw new ResourceNotFoundException("Email não encontrado" + email);}
    }





    public void  deleterUsuarioPoremail(String email) {
         usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarUsuario(String token, UsuarioDTO usuarioDTO){
       //Busca através do token(não precisa passar o email)
        String email = jwtUtil.extractUsername(token.substring(7));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        //busca de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));

       //mescla informações do DTO e do BD para atualizar
        Usuario  usuario = usuarioConverter.updateUsuario(usuarioDTO,usuarioEntity);

        //salva os dados e converte para DTO
        return usuarioConverter.paraUsuarioDTO( usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizarEndereco(Long id, EnderecoDTO enderecoDTO){
       ///busquei por id
        Endereco entity = enderecoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Id não encontrado" + id));
      // atualiza e monta o endereço que será salvo.
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO,entity);
     // salva no BD e converte pra DTO
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO atualizarTelefone(Long id, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Id não encontrado" + id));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO,entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));

    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado + email"));
        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto,usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado + email"));
        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto,usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }


}
