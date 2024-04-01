package br.com.GiovanniJunqueira.To_do_list.task;

import br.com.GiovanniJunqueira.To_do_list.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser);
    Optional<TaskModel> findById(UUID id);
    //porque usar o optional?
}
