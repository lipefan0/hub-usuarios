package br.com.upvisibility.hub_usuarios.business;

import br.com.upvisibility.hub_usuarios.business.mapper.UserMapper;
import br.com.upvisibility.hub_usuarios.business.request.UserRequest;
import br.com.upvisibility.hub_usuarios.business.response.UserResponse;
import br.com.upvisibility.hub_usuarios.infra.entity.UserEntity;
import br.com.upvisibility.hub_usuarios.infra.exception.ConflictException;
import br.com.upvisibility.hub_usuarios.infra.repository.UserRepository;
import br.com.upvisibility.hub_usuarios.infra.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * Cria um novo usuário
     */
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        // Verifica se email já existe
        validateEmailNotExists(userRequest.email());

        // Converte Request para Entity
        UserEntity userEntity = UserMapper.toEntity(userRequest);

        // Criptografa a senha
        userEntity.setSenha(passwordEncoder.encode(userEntity.getSenha()));

        // Salva o usuário
        UserEntity savedUser = userRepository.save(userEntity);

        // Retorna Response
        return UserMapper.toResponse(savedUser);
    }

    /**
     * Atualiza dados do usuário autenticado
     */
    @Transactional
    public UserResponse updateUser(String token, UserRequest userRequest) {
        // Extrai email do token
        String email = jwtUtil.extractUsername(token.substring(7));

        // Busca usuário existente
        UserEntity existingUser = findUserByEmail(email);

        // Se está tentando alterar o email, verifica se o novo email já existe
        if (userRequest.email() != null && !userRequest.email().equals(existingUser.getEmail())) {
            validateEmailNotExists(userRequest.email());
        }

        // Atualiza os campos (apenas os não nulos)
        updateUserFields(existingUser, userRequest);

        // Atualiza endereços se fornecidos
        if (userRequest.enderecos() != null) {
            updateUserAddresses(existingUser, userRequest);
        }

        // Salva as alterações
        UserEntity updatedUser = userRepository.save(existingUser);

        return UserMapper.toResponse(updatedUser);
    }

    /**
     * Busca usuário por email para uso interno
     */
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ConflictException("Usuário não encontrado com o email: " + email));
    }

    /**
     * Busca usuário por email e retorna Response
     */
    public UserResponse getUserByEmail(String email) {
        UserEntity user = findUserByEmail(email);
        return UserMapper.toResponse(user);
    }

    /**
     * Busca usuário autenticado via token
     */
    public UserResponse getAuthenticatedUser(String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        return getUserByEmail(email);
    }

    /**
     * Deleta usuário autenticado
     */
    @Transactional
    public void deleteAuthenticatedUser(String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        UserEntity user = findUserByEmail(email);
        userRepository.delete(user);
    }

    // Métodos auxiliares privados

    private void validateEmailNotExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Email já cadastrado: " + email);
        }
    }

    private void updateUserFields(UserEntity existingUser, UserRequest userRequest) {
        if (userRequest.nome() != null) {
            existingUser.setNome(userRequest.nome());
        }
        if (userRequest.email() != null) {
            existingUser.setEmail(userRequest.email());
        }
        if (userRequest.senha() != null) {
            existingUser.setSenha(passwordEncoder.encode(userRequest.senha()));
        }
        if (userRequest.celular() != null) {
            existingUser.setCelular(userRequest.celular());
        }
        if (userRequest.numeroDocumento() != null) {
            existingUser.setNumeroDocumento(userRequest.numeroDocumento());
        }
        if (userRequest.tipoPessoa() != null) {
            existingUser.setTipoPessoa(userRequest.tipoPessoa());
        }
    }

    private void updateUserAddresses(UserEntity existingUser, UserRequest userRequest) {
        // Remove endereços antigos
        if (existingUser.getEnderecos() != null) {
            existingUser.getEnderecos().clear();
        }

        // Adiciona novos endereços
        if (userRequest.enderecos() != null && !userRequest.enderecos().isEmpty()) {
            existingUser.setEnderecos(UserMapper.toListEnderecoEntity(userRequest.enderecos()));
            // Define o usuarioId para cada endereço (importante para o relacionamento)
            existingUser.getEnderecos().forEach(endereco -> endereco.setUsuarioId(existingUser.getId()));
        }
    }
}
