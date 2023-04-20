package ezenweb.example.web.domain.Todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    //1.게시판 출력
    @Transactional
    public  List<TodoDto> get(){
        List<TodoEntity> list = new ArrayList<>();
        List<TodoDto> dlist = new ArrayList<>();

        list =  todoRepository.findAll();

        list.forEach( o->{
           dlist.add(o.toDto());
        });

        return dlist;
    }

    //2. 등록
    public boolean post(TodoDto dto){
        todoRepository.save( dto.toEntity());
        return true;
    }

    //3. 수정
    public boolean put(TodoDto dto){
        TodoEntity entity =
                todoRepository.findByiid(dto.getIid());

        if(entity != null){
           entity.setTitle( dto.getTitle());
           return true;
        }

        return false;
    }

    //4. 삭제
    public boolean delete( String iid ){
        //1. 일치하는 ID 찾고
        System.out.println("-------------------------------");
        System.out.println(iid);
        TodoEntity todoEntity = todoRepository.findByiid(iid);
        System.out.println(todoEntity);
        if(todoEntity != null){ //2. 찾으면 삭제
            todoRepository.delete(todoEntity);
            return true;
        }
        return false;
    }


}
