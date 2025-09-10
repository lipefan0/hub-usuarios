package br.com.upvisibility.hub_usuarios.business.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
        Long id,
        String nome,
        String email,
        String celular,
        String numeroDocumento,
        String tipoPessoa,
        List<EnderecoResponse> enderecos
) {

}
