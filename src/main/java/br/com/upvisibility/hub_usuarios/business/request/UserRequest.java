package br.com.upvisibility.hub_usuarios.business.request;

import br.com.upvisibility.hub_usuarios.business.dto.EnderecoDTO;
import lombok.*;

import java.util.List;

@Builder
public record UserRequest(
        String nome,
        String email,
        String senha,
        String celular,
        String numeroDocumento,
        String tipoPessoa,
        List<EnderecoRequest> enderecos
) {

}
