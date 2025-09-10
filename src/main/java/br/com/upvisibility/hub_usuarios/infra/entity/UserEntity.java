package br.com.upvisibility.hub_usuarios.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "db_usuarios")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", length = 255)
    private String nome;
    @Column(name = "email_usuario", unique = true)
    private String email;
    private String senha;
    private String celular;
    @Column(name = "numero_documento",  unique = true)
    private String numeroDocumento;
    @Column(name = "tipo_pessoa")
    private String tipoPessoa;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<EnderecoEntity> enderecos;
}
