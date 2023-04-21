package ezenweb.example.web.domain.Todo;

import ezenweb.example.Day04.domain.entity.product.ProductEntity;
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
    @Transactional
    public boolean post(TodoDto dto){
        todoRepository.save( dto.toEntity());
        return true;
    }

    //3. 수정
    @Transactional
    public boolean put(TodoDto dto){

        Optional<TodoEntity> optionalTodoEntity =
                todoRepository.findById(dto.getId());

        if(optionalTodoEntity.isPresent()){
            TodoEntity entity = optionalTodoEntity.get();
            System.out.println(entity);
           entity.setTitle( dto.getTitle());
            System.out.println(entity);
           return true;
        }

        return false;
    }

    //4. 삭제
    @Transactional
    public boolean delete( String id ){
        //1. 일치하는 ID 찾고
        System.out.println(id);
        TodoEntity todoEntity = todoRepository.findById(id).get();
        System.out.println(todoEntity);
        if(todoEntity != null){ //2. 찾으면 삭제
            todoRepository.delete(todoEntity);
            return true;
        }
        return false;
    }


}
