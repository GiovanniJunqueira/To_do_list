package br.com.GiovanniJunqueira.To_do_list.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel usermodel){
        var user = this.userRepository.findByUsername(usermodel.getUsername());
        // tratar exceções dps
        if (user != null){
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("Usuário já existe");
        }
        var passwordcripto = BCrypt.withDefaults().hashToString(12,usermodel.getPassword().toCharArray());
        usermodel.setPassword(passwordcripto);
        var user01 = this.userRepository.save(usermodel);
        return ResponseEntity.status(HttpStatus.CREATED).body(usermodel);
    }

    @GetMapping("allUsers")
    public  ResponseEntity getAllUsers(){
        var allUsers = userRepository.findAll();
        if (allUsers.isEmpty()){
            throw new RuntimeException("Doesn't have Users");
        }
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity getUserById(@PathVariable UUID idUser){
        var userId = userRepository.findById(idUser);
        if (userId.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return ResponseEntity.ok().body(userId);

    }
}
