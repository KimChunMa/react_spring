package ezenweb.example.web.controller;

import ezenweb.example.web.domain.Todo.TodoDto;
import ezenweb.example.web.domain.member.MemberDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:3000") // 해당 컨트롤러는 http://localhost:3000 요청 CORS 정책 적용
public class TodoController {
    @GetMapping("")
    public List<TodoDto> get(){ //dto, 서비스 ,리포지토리, 엔티티, 작업
        //테스트 서비스에게 전달받은 과정

        List<TodoDto> list = new ArrayList<>();
        list.add(new TodoDto(1,"게시물1",true));
        list.add(new TodoDto(2,"게시물1",true));
        list.add(new TodoDto(3,"게시물1",true));



        //과제 서비스 구현

        return null;
    }

    @PostMapping("")
    public boolean post(@RequestBody MemberDto memberdto){
        return true;
    }

    @PutMapping("")
    public  boolean put(){
        return true;
    }

    @DeleteMapping("")
    public  boolean delete( @RequestParam int id ){
        return true;
    }
}
