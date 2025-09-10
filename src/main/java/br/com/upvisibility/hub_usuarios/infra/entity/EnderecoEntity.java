package br.com.upvisibility.hub_usuarios.infra.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "db_enderecos")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String cep;
    private String uf;
    @Column(name = "usuario_id")
    private Long usuarioId;
}
