package com.projetozero.usuario.controller.dtos;



import com.projetozero.usuario.controller.dtos.EnderecoDTO;
import com.projetozero.usuario.controller.dtos.TelefoneDTO;
import com.projetozero.usuario.controller.dtos.UsuarioDTO;
import com.projetozero.usuario.infrastructure.entity.Endereco;
import com.projetozero.usuario.infrastructure.entity.Telefone;
import com.projetozero.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    // Conversão de DTO para Entidade
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paralistaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paralistaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paralistaEndereco(List<EnderecoDTO> enderecoDTOList) {
        return enderecoDTOList.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cep(enderecoDTO.getCep())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .complemento(enderecoDTO.getComplemento())
                .build();
    }

    public List<Telefone> paralistaTelefone(List<TelefoneDTO> telefoneDTOList) {
        return telefoneDTOList.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    // Conversão de Entidade para DTO
    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paralistaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paralistaTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paralistaEnderecoDTO(List<Endereco> enderecoList) {
        return enderecoList.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .complemento(endereco.getComplemento())
                .build();
    }

    public List<TelefoneDTO> paralistaTelefoneDTO(List<Telefone> telefoneList) {
        return telefoneList.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO,Usuario entity){
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome():entity.getNome())
                .id(entity.getId())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail():entity.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha():entity.getSenha())
                .build();
    }
}