package ezenweb.example.Day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//스프링 관리하는 IO 컨테이너 빈[객체] 등록
@Controller // 서버로부터 HTTP 요청이 왔을때 해당 클래스로 핸들러 매핑
@Slf4j // 스프링 로그 메소드 제공 [레벨 : trace < debug < info < warn < error ]
public class MappingController {
    @RequestMapping(value = "/orange", method = RequestMethod.GET)
    @ResponseBody
    public String getOrange() {
        System.out.println("클라이언트로부터 getOrange 요청!");
        log.trace("get");
        return "정상";
    }

    @RequestMapping(value = "/orange", method = RequestMethod.POST)
    @ResponseBody
    public String postOrange() {
        System.out.println("클라이언트로부터 postOrange 요청!");
        log.debug("post");
        return "정상";
    }

    @RequestMapping(value = "/orange", method = RequestMethod.PUT)
    @ResponseBody
    public String putOrange() {
        System.out.println("클라이언트로부터 PutOrange 요청!");
        log.warn("put");
        return "정상";
    }

    @RequestMapping(value = "/orange", method = RequestMethod.DELETE)
    @ResponseBody
    public String delOrange() {
        System.out.println("클라이언트로부터 delOrange 요청!");
        log.info("del");
        return "정상";
    }

}