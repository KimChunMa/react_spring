package ezenweb.example.Day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//스프링 관리하는 IO 컨테이너 빈[객체] 등록
@RestController // @ResponseBody + @Controller
@Slf4j
public class MappingController2 {
    @RequestMapping(value = "/red", method = RequestMethod.GET)
    public String getRed() {
        System.out.println("클라이언트로부터 getOrange 요청!");
        log.info("get");
        return "정상";
    }

    @RequestMapping(value = "/red", method = RequestMethod.POST)
    public String postRed() {
        System.out.println("클라이언트로부터 postOrange 요청!");
        log.info("post");
        return "정상";
    }

    @RequestMapping(value = "/red", method = RequestMethod.PUT)
    public String putRed() {
        System.out.println("클라이언트로부터 PutOrange 요청!");
        log.info("put");
        return "정상";
    }

    @RequestMapping(value = "/red", method = RequestMethod.DELETE)
    public String delRed() {
        System.out.println("클라이언트로부터 delOrange 요청!");
        log.info("del");
        return "정상";
    }

}