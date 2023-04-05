package ezenweb.example.Day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController //  // @ResponseBody + @Controller MVC Controller
@Slf4j // 로그
@RequestMapping("/notes") // 공통 URL
public class NoteController {
    @Autowired // 생성자 자동 주입 [단 스프링컨테이너에 등록이 된 경우만]
    NoteService noteService;
    // ----------------------------- RESTful API -----------------------------
    //1. 쓰기
    @PostMapping("/write") //http://localhost:8080/note/write
    public boolean write(@RequestBody NoteDto noteDto){  log.info("write in : " + noteDto);

        //2. @autowired
        boolean result = noteService.write(noteDto);
        //* response.Writer.println("result") == return
        return true;
    }

    //2. 출력
    @GetMapping("get") //http://localhost:8080/note/get
    public ArrayList<NoteDto> get(){log.info(" get in");
        ArrayList<NoteDto>  result =  noteService.get();
        return result;
    }

    //3. 삭제
    @DeleteMapping("/delete") //http://localhost:8080/note/delete?nno=1
    public boolean delete(@RequestParam int nno){    log.info(" delete in : " + nno);
        boolean result = noteService.delete(nno);
        return result;
    }

    //4. 수정
    @PutMapping("/update") //http://localhost:8080/note/update
    public boolean update(@RequestBody NoteDto dto){
        log.info(" update in : " + dto );

        boolean result = noteService.update(dto);

        return result;
    }





}
