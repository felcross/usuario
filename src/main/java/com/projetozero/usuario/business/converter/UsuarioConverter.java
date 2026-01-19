package com.projetozero.usuario.business.converter;


import com.projetozero.usuario.business.dtos.EnderecoDTO;
import com.projetozero.usuario.business.dtos.TelefoneDTO;
import com.projetozero.usuario.business.dtos.UsuarioDTO;
import com.projetozero.usuario.infrastructure.entity.Telefone;
import com.projetozero.usuario.infrastructure.entity.Usuario;
import com.projetozero.usuario.infrastructure.entity.Endereco;
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
                .enderecos(paralistaEndereco(usuarioDTO.getEndereco()))
                .telefones(paralistaTelefone(usuarioDTO.getTelefone()))
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
                .endereco(paralistaEnderecoDTO(usuario.getEnderecos()))
                .telefone(paralistaTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paralistaEnderecoDTO(List<Endereco> enderecoList) {
        return enderecoList.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
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
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }
}
