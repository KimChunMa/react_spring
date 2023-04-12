package ezenweb.example.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration // 스프링 빈 등록 [MVC 컴포넌트]
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); 생성자
        http
                .csrf() //사이트 간 요청 위조 [post,put http 사용 불가]
                .ignoringAntMatchers("/member/info") //특정 매핑 URL 허용
                .ignoringAntMatchers("/member/login")
                .and() // 기능 추가/구분 할때 사용되는 메소드
                    .formLogin()
                        .loginPage("/memeber/login") // 로그인페이지로 사용할 URL
                        .loginProcessingUrl("/member/login") //로그인처리할 매핑 URL
                        .defaultSuccessUrl("/") //로그인 성공시 매핑 url 
                        .failureUrl("/member/login")//로그인 실패시 이동할 매핑 URL
                .usernameParameter("memail") // 로그인시 사용될 계정 아이디의 필드명
                    .passwordParameter("mpw") // 로그인시 사용될 계정 패스워드 필드명
                
                .and() 
                    .logout()
                        .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout"))//로그아웃 처리를 요청할 매핑 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공했을때 이동할 매핑 URL
                        .invalidateHttpSession(true) ; //세션 초기화
    }
}