package ezenweb.example.web.controller;

import ezenweb.example.web.domain.border.*;
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
public class BoardController {

    @Autowired
    private BoardService boardService;

    // -------------------------------view 변환 -------------------------
  /*  @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/board/list.html");
    }
*/
    // ------------------------------- model 반환 ------------------------

    //1. 카테고리 등록
    @PostMapping("/category/write")
    public Boolean categoryWrite(@RequestBody BoardDto boardDto){
        return boardService.categoryWrite(boardDto);
    }

    //2. 카테고리 출력 [반환 타입 : {1 : 공지사항 , 2 : 자유게시판}]
        //List : {값, 값, 값, 값}
        //Map {키:값 , 키:값, 키:값}
    @GetMapping("/category/list")
    public List<CategoryDto> categoryList(){
        System.out.println(boardService.categoryList());
        return  boardService.categoryList();
    }



    //3. 게시물 쓰기 //body {"btitle":"제목", "bcontent": "내 용" , "cno":"번호" }
    @PostMapping("") //요청한 json필드명과 dto 필드명 일치시 자동 매핑
    public byte write( @RequestBody BoardDto boardDto ){ log.info("c board dto : " + boardDto );
        byte result = boardService.write( boardDto );
        return result;
    }

    //4. 카테고리별 게시물 출력
    @GetMapping("")
    public PageDto list(PageDto pageDto ){
        System.out.println("-------------- dto 출력 ------------------");
        System.out.println(pageDto);
        log.info("pageDto : "+pageDto);
        return boardService.list(pageDto);
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

    //6. 게시물 수정
    @PutMapping("/b_modify")
    public boolean b_modify(@RequestBody BoardDto boardDto){
        System.out.println("---------- 수정 ------- ");
        System.out.println(boardDto);
        return boardService.b_modify(boardDto);
    }

    //7. 댓글 등록
    @PostMapping("/reply")
    public boolean post_reply(@RequestBody ReplyDto replyDto){
        System.out.println("---------- Con 등록 ----------");
        System.out.println(replyDto);
        return boardService.post_reply(replyDto);
    }

    //8.댓글 출력
    @GetMapping("/reply")
    public  List<ReplyDto>  get_reply(@RequestParam int bno){

        return boardService.get_reply(bno);
    }

    //9. 댓글 삭제
    @DeleteMapping("/reply")
    public  boolean del_reply(int rno){
        return boardService.del_reply(rno);
    }

    //10. 댓글 수정
    @PutMapping("/reply")
    public boolean putReply(@RequestBody ReplyDto replyDto){
        System.out.println("-------------- dto ----------");
        System.out.println(replyDto);
        return boardService.putReply(replyDto);
    }
}
