package ezenweb.example.web.service;

import ezenweb.example.web.domain.MemberEntityRepository;
import ezenweb.example.web.domain.member.MemberDto;
import ezenweb.example.web.domain.member.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class MemberService implements UserDetailsService {

    @Autowired //저장소
    private MemberEntityRepository memberEntityRepository;




    //1. 회원가입
    @Transactional
    public boolean write( MemberDto memberDto){
        //시큐리티에서 제공하는 암호화(사람이해x 컴퓨터만 이해o) 사용하기
        // db에 패스워드 감추기, 정보 이동시 노출방지
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("암호화 : " + passwordEncoder.encode("1234"));

        //인코더 : 암호화 , 디코더:원본으로
        memberDto.setMpw(passwordEncoder.encode((memberDto.getMpw() ) ) );

        MemberEntity entity =
               memberEntityRepository.save(memberDto.toEntity());
        if(entity.getMno() > 0){ return true;}
        return false;
    }

    //스프링 시큐리티 적용할때 사용되는 로그인 메소드
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        //1. UserDetailsService 인터페이스 구현
        //2. loadUserByUserame() 메소드 : 아이디 검증
            //시큐리티가 패스워드 검증
        MemberEntity entity = memberEntityRepository.findByMemail(memail);
        if(entity == null){return null;}

        // 3. 검증후 세션에 저장할 dto 반환
        MemberDto dto = entity.toDto();
        log.info("dto : "+dto);
        return dto;
    }


    
    //2.[세션에 존재하는 회원] 호출
    @Transactional
    public MemberDto info( ){
       //1. 시큐리티 인증 된 userDetails 객체로 관리
        // SecurityContextHolder : 시큐리티 정보 저장소
        // SecurityContextHolder.getContext() : 시큐리티 저장된 정보 호출
        // SecurityContextHolder.getContext().getAuthentication(); : 인증정보 호출
        log.info("Auto : "+ SecurityContextHolder.getContext().getAuthentication() );

        //인증된 회원의 정보 호출
        log.info("Auto : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o == null) {return null;}

        //2. 인증된 객체내 회원정보 [Principal] 타입 변환
        return (MemberDto)o;

/* //2. 일반 세션으로 로그인 관리 했을 때
        String memail = (String) request.getSession().getAttribute("login");

        if(memail != null){
            MemberEntity entity = memberEntityRepository.findByMemail(memail);
            return entity.toDto();
        }
        return null;*/
    }



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

    // ------------------ 과제 -----------------------

    /*
 //2.[세션에 존재하는정보 제거] 로그아웃
    @Transactional
    public boolean logout(){
        request.getSession().setAttribute("login",null);
        return true;
    }
*/

/*    @Transactional //2. 회원정보 호출
    public MemberDto info( int mno){
        Optional<MemberEntity> entityOptional =
                memberEntityRepository.findById(mno);

        if(entityOptional.isPresent()) {
            MemberEntity entity = entityOptional.get();
            return entity.toDto();
        }
        return null;
    }*/

    /*
    //2 로그인
    @Transactional
    public boolean login(MemberDto memberDto){
        //입력받은 이메일로 엔티티 찾기
        MemberEntity entity = memberEntityRepository.findByMemail(memberDto.getMemail());
        //암호화 되기전 데이터 , 암호화된 데이터 비교
        if(new BCryptPasswordEncoder().matches( memberDto.getMpw() , entity.getMpw()  )){
            request.getSession().setAttribute("login",entity.getMemail());
            return true;
        }




        //이메일로 엔티티 찾기
        MemberEntity entity =
                memberEntityRepository.findByMemail(memberDto.getMemail());

        //패스워드 검증
        if(entity.getMpw().equals(memberDto.getMpw() ) ){
            request.getSession().setAttribute("login",entity.getMno());
        }


        //한번에 검증
        Optional<MemberEntity> result =
                memberEntityRepository.findByMemailAndMpw(memberDto.getMemail() , memberDto.getMpw());
        log.info("result : " + result);
        if(result.isPresent()){
            request.getSession().setAttribute("login",result.get().getMno());
            return true;
        }
        //한번에찾기?
        boolean result = memberEntityRepository.existsByMemailAndMpw(memberDto.getMemail() , memberDto.getMpw() );
        if(result==true){
            request.getSession().setAttribute("login", memberDto.getMemail());
            return true;
        }


        return false;
    }*/


}
