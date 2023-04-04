package ezenweb.example.Day02;

// 클래스 첫글자는 대문자로
//변수, 객체명, 함수명 = 카멜표기법
//패키지, URL = 소문자

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStart {
    public static void main(String[] args) {
        SpringApplication.run(SpringStart.class);
    }
}

/*
    크롬 / ajax / js ------------- 요청-------------> 서블릿 컨테이너 ---------> Dispatcher Servlet
                       http://localhost:8080/orange                          핸들러 매핑으로 해당 컨트롤러(스프링 빈 등록) 검색
                                                                  ---------> Mapping 검색
                                                                             @RequestMapping(value ="/orange")
                       <-------------------- 응답 -----------------

* */
