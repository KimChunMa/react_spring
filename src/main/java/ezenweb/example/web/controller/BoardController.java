package ezenweb.example.web.controller;

import ezenweb.example.web.domain.border.BoardDto;
import ezenweb.example.web.domain.border.BoardEntity;
import ezenweb.example.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody(메소드위에 하나하나 써야됨 )
@Slf4j
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    //1. 카테고리 등록
    @PostMapping("/category/write")
    public void categoryWrite(@RequestBody BoardDto boardDto){
        boolean result = boardService.categoryWrite(boardDto);
        log.info("board " + boardDto);
    }

    //2. 게시물 쓰기
    @PostMapping("/write")
    public Boolean write(@RequestBody BoardDto boardDto){
        log.info("board " + boardDto);
        boolean result = boardService.write(boardDto);
        return false;
    }

    //3. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards(){
        List<BoardDto> result = boardService.myboards();
        return null;
    }

}
