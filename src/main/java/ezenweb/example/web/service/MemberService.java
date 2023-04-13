package ezenweb.example.web.service;

import ezenweb.example.web.domain.MemberEntityRepository;
import ezenweb.example.web.domain.member.MemberDto;
import ezenweb.example.web.domain.member.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

// UserDetailsService : 일반유저 서비스
// OAuth2UserService : oauth2 유저 서비스 구현
// OAuth2UserService<OAuth2UserRequest , OAuth2User> : oauth2 유저 서비스 구현
@Service
@Slf4j
public class MemberService implements UserDetailsService , OAuth2UserService<OAuth2UserRequest , OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            log.info("서비스 매개변수 : "+ userRequest.toString());

        //1. 인증[로그인] 결과 토큰 확인
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        log.info("서비스 정보 : "+ oAuth2UserService.loadUser(userRequest));

        //2. 전달받은 정보객체
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        log.info("회원정보 : " + oAuth2User.getAuthorities());

        //3. 클라이언트id 요청 [구글, 네이버 , 카카오 ]
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("클라이언트 id : " + registrationId);

        MemberDto memberDto = new MemberDto();

        //4
        //구글 이메일, 이름 호출
       String email = (String)oAuth2User.getAttributes().get("email");
        log.info("google name" +email );

       String name = (String)oAuth2User.getAttributes().get("name");
        log.info("google name" +name );
        
        memberDto.setMemail(email);
        memberDto.setMname(name);
            Set<GrantedAuthority> 권한목록 = new HashSet<>();
            SimpleGrantedAuthority 권한 = new SimpleGrantedAuthority("ROLE_oauthuser");
            권한목록.add(권한);
            memberDto.set권한목록(권한목록);

        return memberDto;
    }

    @Autowired //저장소
    private MemberEntityRepository memberEntityRepository;

    //1. 일반 회원가입 [본 어플리케이션 에서 가입한 사람]
    @Transactional
    public boolean write( MemberDto memberDto){
        //시큐리티에서 제공하는 암호화(사람이해x 컴퓨터만 이해o) 사용하기
        // db에 패스워드 감추기, 정보 이동시 노출방지
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("암호화 : " + passwordEncoder.encode("1234"));

        //인코더 : 암호화 , 디코더:원본으로
        memberDto.setMpw(passwordEncoder.encode((memberDto.getMpw() ) ) );

        //등급부여
        memberDto.setMrole("user");

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
        
        //1. 권한목록 만들기
        Set<GrantedAuthority> 권한목록 = new HashSet<>();
       //2. 권한 객체 만들기 [DB에 존재하는 권한명(ROLE_!!!!)으로]
            //권한 없을경우 : ROLE_ANONYMOUS / 권한 있을경우 ROLE_권한명
        SimpleGrantedAuthority 권한명 = new SimpleGrantedAuthority("ROLE_"+entity.getMrole());
        //3. 권한객체를 권한목록[컬렉션]에 추가
        권한목록.add(권한명);
        //4. UserDetails에 권한목록 대입
        dto.set권한목록(권한목록);
        log.info("로그인 dto : "+dto);
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
        if( o.equals("anonymousUser") ){ return null; }
        // [ Principal ]  // 인증  실패시 : anonymousUser   // 인증  성공시 : 회원정보[Dto]

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
