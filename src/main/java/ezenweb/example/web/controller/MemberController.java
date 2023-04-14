package ezenweb.example.web.controller;

import ezenweb.example.web.domain.member.MemberDto;
import ezenweb.example.web.domain.member.MemberEntity;
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
    public Resource getLogin(){return new ClassPathResource("templates/member/login.html");}

    //2 있을때 객체[빈] 자동생성
    @Autowired
    MemberService mservice;


    //1. 회원가입 C
    @PostMapping("/info") // URL 같아도 HTTP메소드 다르므로 식별 가능
    public boolean write(@RequestBody MemberDto memberDto){ // java 클래스내 메소드 이름은 중복 불가능
        log.info(" member info write : " + memberDto);
       boolean result = mservice.write(memberDto);
        return result;
    }

    //2 호출 R
    @GetMapping("/info")
    public MemberDto info(){
        MemberDto result = mservice.info(); return result;
    }

    //3 수정 U
    @PutMapping("/info")
    public boolean update(@RequestBody MemberDto memberDto){
        log.info(" member info update : " + memberDto);
        boolean result = mservice.update(memberDto);
        return false;
    }
/*

    //4 탈퇴 D
    @DeleteMapping("/info")
    public boolean delete(@RequestParam int mno){
        boolean result = mservice.delete(mno);
        return true;
    }
*/


    // ------------------ 과제 -----------------------

    //아이디찾기 페이지 이동
    @GetMapping("/findid")
    public Resource findid(){return new ClassPathResource("templates/member/findid.html");}


    @PostMapping("/findId")
    public String findId(@RequestBody MemberDto memberDto){
        System.out.println("아이디 찾기 입력받은 것 :"+memberDto);
        return mservice.findId(memberDto.getMname(), memberDto.getMphone());
    }

    //비밀번호찾기 페이지 이동
    @GetMapping("/findpw")
    public Resource findpw(){return new ClassPathResource("templates/member/findpw.html");}


    @PostMapping("/findPw")
    public String findPw(@RequestBody MemberDto memberDto){
        System.out.println("비번 찾기 입력받은 것 :"+memberDto);
        return mservice.findPw(memberDto.getMemail(), memberDto.getMphone());
    }

    //회원탈퇴 페이지 이동
    @GetMapping("/delete")
    public Resource delete(){return new ClassPathResource("templates/member/delete.html");}

    @DeleteMapping("/mdelete")
    public boolean mdelete(@RequestParam String mpw, @RequestParam String memail ){
        boolean result = mservice.mdelete(memail ,mpw);
        return result;
    }

    //회원수정 페이지 이동
    @GetMapping("/mupdate")
    public Resource update(){return new ClassPathResource("templates/member/update.html");}

    @PutMapping("/mupdate")
    public boolean mUpdate(@RequestParam String mphone, @RequestParam String mname ){

        boolean result = mservice.mUpdate(mphone,mname);
        return true;
    }



/*
    //------------------- 스프링 시큐리티 사용 -------------------
    @PostMapping("/login")
    public boolean login(@RequestBody MemberDto memberDto){
        boolean result = mservice.login(memberDto);
        return result;
    }

    //로그아웃
    @GetMapping("/logout")
    public boolean logout(){
        return mservice.logout();
    }
    */
}
