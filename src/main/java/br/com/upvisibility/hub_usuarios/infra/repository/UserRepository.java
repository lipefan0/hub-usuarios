package br.com.upvisibility.hub_usuarios.infra.repository;

import br.com.upvisibility.hub_usuarios.infra.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
