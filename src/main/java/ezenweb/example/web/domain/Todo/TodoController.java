package ezenweb.example.web.domain.Todo;

import ezenweb.example.web.domain.Todo.TodoDto;
import ezenweb.example.web.domain.Todo.TodoEntity;
import ezenweb.example.web.domain.Todo.TodoRepository;
import ezenweb.example.web.domain.Todo.TodoService;
import ezenweb.example.web.domain.border.PageDto;
import ezenweb.example.web.domain.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
//@CrossOrigin(origins = "http://localhost:3000") // 해당 컨트롤러는 http://localhost:3000 요청 CORS 정책 적용
public class TodoController {

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TodoService service;

    //모든 게시판 출력
    @GetMapping("")
    public TodoPageDto get(@RequestParam  int page){return service.get(page);}

    @PostMapping("")
    public boolean post(@RequestBody TodoDto dto){
        return service.post(dto);
    }

    @PutMapping("")
    public  boolean put(@RequestBody TodoDto dto){
        return service.put(dto);
    }

    @DeleteMapping("")
    public boolean delete( @RequestParam String id ){
        System.out.println("---------------------------------");
        System.out.println("들어온 값"+id);
        return service.delete(id);
    }
}
