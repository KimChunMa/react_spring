package ezenweb.example.Day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

//스프링 관리하는 IO 컨테이너 빈[객체] 등록
@RestController // @ResponseBody + @Controller
@Slf4j
public class MappingController3 {

    @GetMapping(value = "/blue")
    public String getRed() {
        System.out.println("클라이언트로부터 getOrange 요청!");
        log.info("get");
        return "정상";
    }

    @PostMapping(value = "/blue")
    public String postRed() {
        System.out.println("클라이언트로부터 postOrange 요청!");
        log.info("post");
        return "정상";
    }

    @PutMapping(value = "/blue")
    public String putRed() {
        System.out.println("클라이언트로부터 PutOrange 요청!");
        log.info("put");
        return "정상";
    }

    @DeleteMapping(value = "/blue")
    public String delRed() {
        System.out.println("클라이언트로부터 delOrange 요청!");
        log.info("del");
        return "정상";
    }

}