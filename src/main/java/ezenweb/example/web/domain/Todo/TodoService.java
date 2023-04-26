package ezenweb.example.web.domain.Todo;

import ezenweb.example.Day04.domain.entity.product.ProductEntity;
import ezenweb.example.web.domain.border.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public TodoPageDto get(int page){
        System.out.println("-------------");
        System.out.println("page : " + page);
        Pageable pageable = PageRequest.of(
                page-1,5, Sort.by(Sort.Direction.DESC, "id") ) ;

        Page<TodoEntity> todoEntityPage = todoRepository.findAll(pageable);

        List<TodoDto> dlist = new ArrayList<>();

        todoEntityPage.forEach( o->{
           dlist.add(o.toDto());
        });

        return TodoPageDto.builder()
                .totalPage(todoEntityPage.getTotalPages())
                .totalCount(todoEntityPage.getTotalElements())
                .tododtoList(dlist)
                .page(page).build();
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
