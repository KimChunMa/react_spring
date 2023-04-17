package ezenweb.example.web.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//인증 성공했을떄, 실패 했을때 시큐리티의 컨트롤 재구현
// AuthenticationSuccessHandler: 인증성공했을때 인터페이스
// on AuthenticationSuccess: 인증 성공시 실행되는 메소드
// AuthenticationFailureHandler: 인증 실패시 인터페이스

@Component
@Slf4j
public class AuthSucessFailHandler implements AuthenticationSuccessHandler , AuthenticationFailureHandler {
    //@autowired 불가능
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override //인수 request, response, authentication
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("--------------------------------");
        log.info("authentication :"+authentication);

        String json = objectMapper.writeValueAsString("성공");

        response.setCharacterEncoding("UTF-8");
        response.setContentType(("application/json"));
        response.getWriter().println(json);
    }

    @Override  //인수 request, response, exception
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("--------------------------------");
        log.info("authentication :"+exception.toString());

        String json = objectMapper.writeValueAsString("실패");

        response.setCharacterEncoding("UTF-8");
        response.setContentType(("application/json"));
        response.getWriter().println(json);
    }
}
