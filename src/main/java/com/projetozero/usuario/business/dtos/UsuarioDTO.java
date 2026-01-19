package com.projetozero.usuario.business.dtos;


import lombok.*;

import java.util.List;

import com.projetozero.usuario.business.dtos.EnderecoDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDTO> endereco;
    private List<TelefoneDTO> telefone;
}
