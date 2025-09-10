package br.com.upvisibility.hub_usuarios.business.request;

public record LoginRequest(
        String email,
        String senha
) {
}
