package ezenweb.example.web.controller;

import ezenweb.example.web.domain.border.BoardDto;
import ezenweb.example.web.domain.border.BoardEntity;
import ezenweb.example.web.domain.border.CategoryDto;
import ezenweb.example.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.ClassParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController // @Controller + @ResponseBody(메소드위에 하나하나 써야됨 )
@Slf4j
@RequestMapping("/board")
@CrossOrigin( origins = "http://localhost:3000")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // -------------------------------view 변환 -------------------------
    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/board/list.html");
    }

    // ------------------------------- model 반환 ------------------------

    //1. 카테고리 등록
    @PostMapping("/category/write")
    public Boolean categoryWrite(@RequestBody BoardDto boardDto){
        System.out.println("-----------------");
        System.out.println(boardService.categoryWrite(boardDto));
        return boardService.categoryWrite(boardDto);
    }

    //2. 카테고리 출력 [반환 타입 : {1 : 공지사항 , 2 : 자유게시판}]
        //List : {값, 값, 값, 값}
        //Map {키:값 , 키:값, 키:값}
    @GetMapping("/category/list")
    public List<CategoryDto> categoryList(){
        log.info("c board Dto : ");
        System.out.println("-----------");
        System.out.println(boardService.categoryList());
        return  boardService.categoryList();
    }



    //3. 게시물 쓰기 //body {"btitle":"제목", "bcontent": "내 용" , "cno":"번호" }
    @PostMapping("/write") //요청한 json필드명과 dto 필드명 일치시 자동 매핑
    public byte write( @RequestBody BoardDto boardDto ){ log.info("c board dto : " + boardDto );
        byte result = boardService.write( boardDto );
        return result;
    }

    //4. 카테고리별 게시물 출력
    @GetMapping("/list")
    public List<BoardDto> list(@RequestParam int cno){
        log.info("list cno : "+cno);
        List<BoardDto> result = boardService.list(cno);
        return result;
    }


    //3. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards(){
        List<BoardDto> result = boardService.myboards();
        return result;
    }

    /* -------------------------- 과제 ------------------------*/
    //4. 게시물 상세 출력
    @GetMapping("/s_board")
    public BoardDto s_board(@RequestParam int bno){
        return boardService.s_board(bno);
    }

    //5. 게시물 삭제
    @DeleteMapping("/b_del")
    public boolean b_del(@RequestParam int bno){
        return boardService.b_del(bno);
    }



}
