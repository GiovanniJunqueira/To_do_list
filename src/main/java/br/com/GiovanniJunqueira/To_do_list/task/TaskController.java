package br.com.GiovanniJunqueira.To_do_list.task;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);
        var task01 = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskModel);
    }
 
    @GetMapping("/get")
    public ResponseEntity getAllTasks(){
        var allTasks = taskRepository.findAll();
        if (allTasks.isEmpty()){
            throw new RuntimeException("Doesn't have tasks");
        }
        return ResponseEntity.ok(allTasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity getTaskById(@PathVariable UUID taskId){
        var idTask = taskRepository.findById(taskId);
        if (idTask.isEmpty()) {
            throw new RuntimeException("Task Not Found");
        }
        return ResponseEntity.ok().body(idTask);
    }

    @GetMapping("/userTasks")
    public ResponseEntity<List<TaskModel>> getUserTasks(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var userTasks = taskRepository.findByIdUser((UUID) idUser);
        return ResponseEntity.ok().body(userTasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> update(@RequestBody TaskModel taskModel, @PathVariable UUID id,  HttpServletRequest request) throws Exception {
        if(id == null)throw new Exception("Id not found");
        var task = this.taskRepository.findById(id).orElse(null);


        if(task == null)throw new Exception("Task not found");

        var IdUser = request.getAttribute("userId");
        if(!task.getIdUser().equals(IdUser)){
            throw new Exception("Change anauthorized");
        }

        return ResponseEntity.ok().body(this.taskRepository.save(task));
    }

    @DeleteMapping("/{taskId}")
    public  ResponseEntity deleteTask(@PathVariable UUID taskId, HttpServletRequest request){
        var task = this.taskRepository.findById(taskId).orElse(null);
        if (task == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        var idUser = request.getAttribute("idUser");
        if (!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This isn't your task");
        }
        this.taskRepository.delete(task);
        return ResponseEntity.ok().body("Task deleted");
    }
}
