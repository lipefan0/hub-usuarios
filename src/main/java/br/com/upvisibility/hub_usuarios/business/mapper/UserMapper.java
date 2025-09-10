package br.com.upvisibility.hub_usuarios.business.mapper;

import br.com.upvisibility.hub_usuarios.business.request.EnderecoRequest;
import br.com.upvisibility.hub_usuarios.business.request.UserRequest;
import br.com.upvisibility.hub_usuarios.business.response.EnderecoResponse;
import br.com.upvisibility.hub_usuarios.business.response.UserResponse;
import br.com.upvisibility.hub_usuarios.infra.entity.EnderecoEntity;
import br.com.upvisibility.hub_usuarios.infra.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserMapper {

    public static UserEntity toEntity(UserRequest userRequest) {
        return UserEntity.builder()
                .nome(userRequest.nome())
                .email(userRequest.email())
                .senha(userRequest.senha())
                .celular(userRequest.celular())
                .numeroDocumento(userRequest.numeroDocumento())
                .tipoPessoa(userRequest.tipoPessoa())
                .enderecos(toListEnderecoEntity(userRequest.enderecos()))
                .build();
    }

    public static UserResponse toResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .nome(userEntity.getNome())
                .email(userEntity.getEmail())
                .numeroDocumento(userEntity.getNumeroDocumento())
                .tipoPessoa(userEntity.getTipoPessoa())
                .celular(userEntity.getCelular())
                .enderecos(toListEnderecoResponse(userEntity.getEnderecos()))
                .build();
    }

    // toEnderecoEntity

    public static EnderecoEntity toEnderecoEntity(EnderecoRequest request) {
        if (request == null) return null;

        return EnderecoEntity.builder()
                .logradouro(request.logradouro())
                .numero(request.numero())
                .complemento(request.complemento())
                .bairro(request.bairro())
                .cidade(request.cidade())
                .uf(request.uf())
                .cep(request.cep())
                .build();
    }

    // toEnderecoResponse
    public static EnderecoResponse toEnderecoResponse(EnderecoEntity entity) {
        if (entity == null) return null;

        return EnderecoResponse.builder()
                .logradouro(entity.getLogradouro())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .bairro(entity.getBairro())
                .cidade(entity.getCidade())
                .uf(entity.getUf())
                .cep(entity.getCep())
                .build();
    }

    // toListEnderecoEntity
    public static List<EnderecoEntity> toListEnderecoEntity(List<EnderecoRequest> requests) {
        if (requests == null) return new ArrayList<>();

        return requests.stream()
                .map(UserMapper::toEnderecoEntity)
                .toList();
    }


    // toListEnderecoResponse
    public static List<EnderecoResponse> toListEnderecoResponse(List<EnderecoEntity> entities) {
        if (entities == null) return new ArrayList<>();

        return entities.stream()
                .map(UserMapper::toEnderecoResponse)
                .toList();
    }

}
