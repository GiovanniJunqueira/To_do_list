package br.com.GiovanniJunqueira.To_do_list.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@Getter
@Setter
@Entity(name="tab_user")
public class UserModel {
    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;
    private String name;
    @Column(unique = true)
    private String username;
    private String password;
}
