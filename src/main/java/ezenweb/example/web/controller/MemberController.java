package ezenweb.example.web.controller;

import ezenweb.example.web.domain.member.MemberDto;
import ezenweb.example.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody(메소드위에 하나하나 써야됨 )
@Slf4j
@RequestMapping("/member")
public class MemberController {

    //회원가입창 이동
    @GetMapping("/signup")
    public Resource getSignup(){
        return new ClassPathResource("templates/member/signup.html");
    }

    //로그인창
    @GetMapping("/login")
    public Resource getLogin(){
        return new ClassPathResource("templates/member/login.html");
    }


    //1 @Autowired 없이
    MemberService service = new MemberService();

    //2 있을때 객체[빈] 자동생성
    @Autowired
    MemberService mservice;


    //1. 회원가입 C
    @PostMapping("/info") // URL 같아도 HTTP메소드 다르므로 식별 가능
    public boolean write(@RequestBody MemberDto memberDto){ // java 클래스내 메소드 이름은 중복 불가능
        log.info(" member info write : " + memberDto);
        boolean result = mservice.write(memberDto);
        return false;
    }

    //2 호출 R
    @GetMapping("/info")
    public MemberDto info(){
        MemberDto result = mservice.info(); return result;
    }

    //로그아웃
    @GetMapping("/logout")
    public boolean logout(){
        return mservice.logout();
    }

    //3 수정 U
    @PutMapping("/info")
    public boolean update(@RequestBody MemberDto memberDto){
        log.info(" member info update : " + memberDto);
        boolean result = mservice.update(memberDto);
        return false;
    }

    //4 탈퇴 D
    @DeleteMapping("/info")
    public boolean delete(@RequestParam int mno){
        boolean result = mservice.delete(mno);
        return true;
    }

    //------------------- 스프링 시큐리티 사용 -------------------
    @PostMapping("/login")
    public boolean login(@RequestBody MemberDto memberDto){
        boolean result = mservice.login(memberDto);
        return result;
    }
}
