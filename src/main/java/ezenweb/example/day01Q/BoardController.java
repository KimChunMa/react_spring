package ezenweb.example.day01Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController //MVC 컨테이너 빈 등록
public class BoardController {

    // private BoardRepository boardrepository; // 객체선언 null 에러

    @Autowired  //빈에 등록된 생성자 찾아서 자동 대입
    private BoardRepository boardrepository;

    @GetMapping("/")
    public String index(){
        BoardEntity entity = BoardEntity.builder()
                .btitle("제목")
                .bcontent("내용")
                .build();
        boardrepository.save(entity);
        return "메인페이지";
    }

}
