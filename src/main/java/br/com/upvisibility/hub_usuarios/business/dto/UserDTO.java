package br.com.upvisibility.hub_usuarios.business.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String celular;
    private String numeroDocumento;
    private String tipoPessoa;
    private List<EnderecoDTO> enderecos;
}
