package br.com.GiovanniJunqueira.To_do_list.task;

import br.com.GiovanniJunqueira.To_do_list.user.UserModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Data
@Entity (name="tab_task")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID idUser;
    @Length (max = 25)
    private String title;
    @Length (max = 100)
    private String description;
    private LocalDate date;
    private LocalDate endDate;
}
