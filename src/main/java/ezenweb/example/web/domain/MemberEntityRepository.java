package ezenweb.example.web.domain;

import ezenweb.example.web.domain.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberEntityRepository
        extends JpaRepository<MemberEntity,Integer> {

    //1. 해당 이메일로 엔티티 찾기
        //sql : select * from member where memail
    MemberEntity findByMemail(String memail);

    //2. 해당 이메일과 비밀번호가 일치한 엔티티 여부
    //sql : select * from member where memail =? and Mpw =?
    Optional<MemberEntity> findByMemailAndMpw( String memail , String mpw);

    //3. 동일한 이메일 존재시 true
    boolean existsByMemail(String memail);

    //4 동일한 이메일, 비번 존재시 true
    boolean existsByMemailAndMpw(String memail, String mpw);

    //5. 아이디 찾기 [이름 , 전화번호]
    Optional<MemberEntity> findByMnameAndMphone(String mname, String mphone);
    //6. 비밀번호 찾기 [아이디, 전화번호]
    boolean existsByMemailAndMphone(String memail, String mphone);

    Optional<MemberEntity> findByMpw(String mpw);
}

/*
    Optional : null값이 나와도 오류가안나오게 해줌

    .findById(pk 값) : 해당하는 pk값이 검색후 존재하면 레코드 (엔티티) 반환
    .findAll() : 모든 레코드[엔티티] 반환
    .save(엔티티) : 해당 엔티티를  DB레코드 insert
    .delete(엔티티) : 해당 엔티티를 DB레코드에서 delete
    @Transactional --> setter 메소드 이용 : 수정
    --------------> 그외 추가 메소드 사용 만들기
    검색

        findBy필드명(인수)  select * from member where memail = ? ;
        findBy필드명And필드명() select * from member where memail = ? and mpw = ? ;
        findBy필드명or필드명() select * from member where memail = ? or mpw = ? ;

      검색여부 [true, false]
        existsBy필드(And필드)(String 필드);

        Optional<클래스>
*/
