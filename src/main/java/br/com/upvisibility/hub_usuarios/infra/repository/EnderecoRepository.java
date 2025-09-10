package br.com.upvisibility.hub_usuarios.infra.repository;

import br.com.upvisibility.hub_usuarios.infra.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {
}
