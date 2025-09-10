package br.com.upvisibility.hub_usuarios.business.response;

import lombok.Builder;

@Builder
public record EnderecoResponse(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String cep,
        String uf
) {
}
