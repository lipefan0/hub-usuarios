package br.com.upvisibility.hub_usuarios.controller;

import br.com.upvisibility.hub_usuarios.business.UserService;
import br.com.upvisibility.hub_usuarios.business.request.EnderecoRequest;
import br.com.upvisibility.hub_usuarios.business.request.LoginRequest;
import br.com.upvisibility.hub_usuarios.business.request.UserRequest;
import br.com.upvisibility.hub_usuarios.business.response.UserResponse;
import br.com.upvisibility.hub_usuarios.infra.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserData(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getAuthenticatedUser(token));
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @PutMapping("/me/editar")
    public ResponseEntity<UserResponse> updateUserData(
            @RequestBody UserRequest request,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(userService.updateUser(token, request));
    }

    @PostMapping("/me/endereco")
    public ResponseEntity<UserResponse> adicionarEndereco(
            @RequestBody EnderecoRequest request,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(userService.addAddress(token, request));
    }

    @PutMapping("/me/endereco/{id}")
    public ResponseEntity<UserResponse> atualizarEndereco(
            @RequestBody EnderecoRequest request,
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(userService.updateAddress(token, id, request));
    }

    @DeleteMapping("/me/endereco/{id}")
    public ResponseEntity<UserResponse> deletarEndereco(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(userService.deleteAddress(token, id));
    }

}
