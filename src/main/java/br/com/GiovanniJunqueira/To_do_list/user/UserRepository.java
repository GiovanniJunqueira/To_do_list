package br.com.GiovanniJunqueira.To_do_list.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository <UserModel, UUID>{
   UserModel findByUsername (String username);
   Optional<UserModel> findById (UUID id);
}
