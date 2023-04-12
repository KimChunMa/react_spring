package ezenweb.example.web.service;

import ezenweb.example.web.domain.MemberEntityRepository;
import ezenweb.example.web.domain.member.MemberDto;
import ezenweb.example.web.domain.member.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberEntityRepository memberEntityRepository;

    @Autowired
    private HttpServletRequest request;


    //1. 회원가입
    @Transactional
    public boolean write( MemberDto memberDto){
        MemberEntity entity =
               memberEntityRepository.save(memberDto.toEntity());
        if(entity.getMno() > 0){return true;}
        return false;
    }

    //*2 로그인
    @Transactional
    public boolean login(MemberDto memberDto){
      /*
        //이메일로 엔티티 찾기
        MemberEntity entity =
                memberEntityRepository.findByMemail(memberDto.getMemail());

        //패스워드 검증
        if(entity.getMpw().equals(memberDto.getMpw() ) ){
            request.getSession().setAttribute("login",entity.getMno());
        }*/
        
        /*
        //한번에 검증
        Optional<MemberEntity> result =
                memberEntityRepository.findByMemailAndMpw(memberDto.getMemail() , memberDto.getMpw());
        log.info("result : " + result);
        if(result.isPresent()){
            request.getSession().setAttribute("login",result.get().getMno());
            return true;
        }*/

        //한번에찾기?
        boolean result = memberEntityRepository.existsByMemailAndMpw(memberDto.getMemail() , memberDto.getMpw() );
        if(result==true){
            request.getSession().setAttribute("login", memberDto.getMemail());
            return true;
        }


        return false;
    }
    
    //2.[세션에 존재하는 회원] 호출
    @Transactional
    public MemberDto info( ){

        String memail = (String) request.getSession().getAttribute("login");

        if(memail != null){
            MemberEntity entity = memberEntityRepository.findByMemail(memail);
            return entity.toDto();
        }
        return null;
    }

    //2.[세션에 존재하는정보 제거] 로그아웃
    @Transactional
    public boolean logout(){
        request.getSession().setAttribute("login",null);
        return true;
    }

    //2. 회원정보 호출
/*    @Transactional
    public MemberDto info( int mno){
        Optional<MemberEntity> entityOptional =
                memberEntityRepository.findById(mno);

        if(entityOptional.isPresent()) {
            MemberEntity entity = entityOptional.get();
            return entity.toDto();
        }
        return null;
    }*/

    //3 수정
    @Transactional
    public boolean update( MemberDto memberDto){
        Optional<MemberEntity> entityOptional =
                memberEntityRepository.findById(memberDto.getMno());

        if( entityOptional.isPresent()){
            MemberEntity entity = entityOptional.get();
            entity.setMname(memberDto.getMname());
            entity.setMrole(memberDto.getMrole());
            entity.setMpw(memberDto.getMpw());
            entity.setMphone(memberDto.getMphone());
        }
        return false;
    }

    //4 탈퇴
    @Transactional
    public boolean delete( int mno){
        Optional<MemberEntity> entityOptional =
                memberEntityRepository.findById(mno);

        if(entityOptional.isPresent()){
           MemberEntity memberEntity =
                   entityOptional.get();
            memberEntityRepository.delete(memberEntity);
            return true;
        }
        return false;
    }


}
