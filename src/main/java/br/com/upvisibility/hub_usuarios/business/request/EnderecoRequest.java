package br.com.upvisibility.hub_usuarios.business.request;

import lombok.*;

@Builder
public record EnderecoRequest(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String cep,
        String uf
) {}
