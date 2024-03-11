package br.com.GiovanniJunqueira.To_do_list.task;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity create(@RequestBody TaskModel taskModel){
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

}
