package ezenweb.example.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration // 컴포넌트 증록
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Override // View 컨트롤러 오버라이딩 [이유: 리액트 view와 연결하기 위함]
    public void addViewControllers(ViewControllerRegistry registry) { // 미리 구성된 스프링 mvc 설정 연결 클래스
        registry.addViewController( "/{spring:\\w+}").setViewName("forward:/");
        registry.addViewController( "/**/{spring:\\w+}").setViewName("forward:/");
        registry.addViewController( "/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}").setViewName("forward:/");

    }
}
