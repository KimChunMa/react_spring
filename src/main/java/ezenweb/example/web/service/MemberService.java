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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

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

        String email = null;
        String name = null;

        if(registrationId.equals("kakao")) { // 만약 카카오 회원이면
            Map<String,Object> kakao_account =(Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            Map<String,Object> profile =(Map<String, Object>)kakao_account.get("profile");

            System.out.println(kakao_account);
            System.out.println(profile);

            email = (String)kakao_account.get("email");
            name = (String)profile.get("nickname");

        }else if(registrationId.equals("naver")) { // 카카오 회원
            Map<String,Object> response =(Map<String, Object>) oAuth2User.getAttributes().get("response");

            email = (String)response.get("email");
            name = (String)response.get("nickname");


        }else if(registrationId.equals("google")) { // 구글 회원
            email = (String)oAuth2User.getAttributes().get("email");
            log.info("google name:" +email );

            name = (String)oAuth2User.getAttributes().get("name");
            log.info("google name:" +name );
        }


        // 인가 객체 [0Auth2User ---->  통합 Dto(일반 dto + oauth)]
        MemberDto memberDto = new MemberDto();
        memberDto.set소셜회원정보(oAuth2User.getAttributes());
        memberDto.setMemail(email);
        memberDto.setMname(name);

            Set<GrantedAuthority> 권한목록 = new HashSet<>();
            SimpleGrantedAuthority 권한 = new SimpleGrantedAuthority("ROLE_user");
            권한목록.add(권한);
            memberDto.set권한목록(권한목록);

            //1. DB 저장하기전에 해당 이메일로 된 이메일 존재하는지 검사
            //DB처리 [DB에 회원가입, 두번쨰 방문시 db수정]
            MemberEntity entity =  memberEntityRepository.findByMemail(email);
            if(entity == null){ // 첫방문
                memberDto.setMrole("oauthuser"); // DB에 저장할 권한명
                memberEntityRepository.save(memberDto.toEntity());
            }else{//두번쨰 이상 수정처리
                entity.setMname(name);
            }
            memberDto.setMno(entity.getMno()); // 생성된, 검색된 엔티티 회원번호
            return memberDto;
    }

    @Autowired //저장소
    private MemberEntityRepository memberEntityRepository;

    //1. 일반 회원가입 [본 어플리케이션 에서 가입한 사람]
    @Transactional
    public boolean write( MemberDto memberDto){
        MemberEntity entityOptional =
                memberEntityRepository.findByMemail(memberDto.getMemail());

        if(entityOptional!=null){
            return false;
        }

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
        if(entity == null){throw new UsernameNotFoundException("해당 계정의 회원이 없습니다.");}

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
    public MemberDto info(){
       //1. 시큐리티 인증 된 userDetails 객체로 관리
        // SecurityContextHolder : 시큐리티 정보 저장소
        // SecurityContextHolder.getContext() : 시큐리티 저장된 정보 호출
        // SecurityContextHolder.getContext().getAuthentication(); : 인증정보 호출
        log.info("Auto : "+ SecurityContextHolder.getContext().getAuthentication() );

        //인증된 회원의 정보 호출
        System.out.println("-----------------------------------");
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

    //5. 아이디 중복 체크
    public boolean idcheck( String memail){
        return memberEntityRepository.existsByMemail(memail);
    }

    //6. 전화번호 체크
    public boolean phoneCheck(@RequestParam String mphone){
        return memberEntityRepository.existsByMphone(mphone);
    }



    // ------------------ 과제 -----------------------

    //5.이름+전화번호 일치시 이메일 알려주기
    public String findId(String mname, String mphone){
        System.out.println("--------------------------------");
        System.out.println("매개변수 : " + mname +" , "+ mphone);
        System.out.println("-------------------------------");
        System.out.println(memberEntityRepository.findByMnameAndMphone(mname,mphone));
        Optional<MemberEntity> entityOptional =
                memberEntityRepository.findByMnameAndMphone(mname,mphone);
        if(entityOptional.isPresent()){
            System.out.println("-------------");
            System.out.println( entityOptional.get().getMemail());
            return entityOptional.get().getMemail();
        }
        return null;
    }

    //6. 이메일+전화번호 일치시 임시비밀번호 6자리
    @Transactional
    public String findPw(String memail, String mphone){
        //<null값 대비> 이메일+전화번호 일치시
        Optional<MemberEntity> entityOptional =
                memberEntityRepository.findByMnameAndMphone(memail,mphone);
        if(entityOptional.isPresent()){ //존재시 6자리 난수
            MemberEntity entity = entityOptional.get();
            String pw = "";

            for(int i = 0 ; i < 6 ; i++) { //
                pw +=  (int)(Math.random() * 10); }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            //6가지 난수+암호화 엔티티 대입
            entity.setMpw(passwordEncoder.encode(pw)); 
            System.out.println("수정된 entity : "+ entity );
            return pw;
        }
        return null;
    }

    //7. 회원탈퇴하기
    @Transactional
    public boolean mdelete(String memail,String mpw){
        BCryptPasswordEncoder pwe = new BCryptPasswordEncoder();

        //입력받은 비번 암호화후 DB에 찾기
        MemberEntity me =
                memberEntityRepository.findByMemail(memail);

        if(me!=null){ // 찾으면 삭제
            if(pwe.matches( mpw, me.getMpw()) ) { //끝을 복호화됨
                memberEntityRepository.delete(me);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean mUpdate(String mphone,String mname){
        //세션에 등록된 객체 저장
        MemberDto mdto  = (MemberDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberEntity me = mdto.toEntity();

       if(memberEntityRepository.findByMemail(me.getMemail() ) != null ){
            //세션에 등록된 객체를 다시 DB객체에 넣기
            me = memberEntityRepository.findByMemail(me.getMemail());
            me.setMphone(mphone); me.setMname(mname);
            return true;
        };
        return false;
    }


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
