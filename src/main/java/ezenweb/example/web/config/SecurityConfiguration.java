package ezenweb.example.web.config;

import ezenweb.example.web.controller.AuthSucessFailHandler;
import ezenweb.example.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration // 스프링 빈 등록 [MVC 컴포넌트]
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthSucessFailHandler authSucessFailHandler;

    @Autowired
    private MemberService memberService;


    //인증 [로그인] 관련 보안 담당 메소드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( memberService).passwordEncoder( new BCryptPasswordEncoder() );
        //auth.userDetailsService() : UserDetailsService 가 구현된 서비스 대입
        //서비스 안에서 로그인 패스워드 검증시 사용된 암호화 객체 대입
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); 생성자
        http
                //권한에 따른 HTTP GET 요청 제한
                .authorizeHttpRequests() // HTTP 인증 요청
                    .antMatchers("/member/info/mypage") // 인증시에만 사용할 URL
                    .hasRole("user") // 위 URL 패턴에 요청할수 있는 권한명

                .antMatchers("/admin/**") // localohst:8080/admin/ ~~ 이하 페이지 막기
                    .hasRole("admin")
                .antMatchers("/board/**")
                    .hasRole("user")
                .antMatchers("/**") // localhost:8080 ~ 이하 페이지는 권한 해제
                    .permitAll()//권한 해제


                    // 토큰 (ROLE_user) : ROLE_ 제외한 권한명 작성
                .and()
                    .csrf() //사이트 간 요청 위조 [post,put http 사용 불가]
                        .ignoringAntMatchers("/member/info") //특정 매핑 URL 허용
                        .ignoringAntMatchers("/member/login")
                        .ignoringAntMatchers("/member/findPw")
                        .ignoringAntMatchers("/member/findId")
                        .ignoringAntMatchers("/member/mdelete")
                        .ignoringAntMatchers("/member/mupdate")
                        .ignoringAntMatchers("/board/category/write")
                        .ignoringAntMatchers("/board/write")
                        .ignoringAntMatchers("/board/b_del")
                        .ignoringAntMatchers("/todo")
                        .ignoringAntMatchers("/member/find")

                .and() // 기능 추가/구분 할때 사용되는 메소드
                    .formLogin()
                        .loginPage("/member/login") // 로그인페이지로 사용할 URL
                        .loginProcessingUrl("/member/login") //로그인처리할 매핑 URL
                        //.defaultSuccessUrl("/") //로그인 성공시 매핑 url
                        .successHandler(authSucessFailHandler)
                        //.failureUrl("/member/login")//로그인 실패시 이동할 매핑 URL
                        .failureHandler(authSucessFailHandler)
                    .usernameParameter("memail") // 로그인시 사용될 계정 아이디의 필드명
                    .passwordParameter("mpw") // 로그인시 사용될 계정 패스워드 필드명


                .and() 
                    .logout()
                        .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout"))//로그아웃 처리를 요청할 매핑 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공했을때 이동할 매핑 URL
                        .invalidateHttpSession(true)  //세션 초기화


                .and()
                    .oauth2Login() // 소셜 로그인
                    .defaultSuccessUrl("/") // 로그인성공시 이동할 매핑 URL
                    .successHandler(authSucessFailHandler)
                    .userInfoEndpoint() // 스프링 시큐리티로 들어올 수 있도록 시큐리티 로그인 엔드포인트[종착점]
                    .userService(memberService); // oauth2 서비스를 처리할 서비스 구현

        http.cors(); // cors 정책 사용
    }//configure e

    //스프링 시큐리티에 cors 정책 설정 [리액트가 요청 ]
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(Arrays.asList("HEAD","GET","POST","PUT","DELETE") );
        corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type","Cache-COntrol","Authorization"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }


}// class e
