package ezenweb.example.web.service;

import ezenweb.example.web.domain.MemberEntityRepository;
import ezenweb.example.web.domain.member.MemberDto;
import ezenweb.example.web.domain.member.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberEntityRepository memberEntityRepository;


    //1. 회원가입
    @Transactional
    public boolean write( MemberDto memberDto){
        MemberEntity entity =
               memberEntityRepository.save(memberDto.toEntity());
        if(entity.getMno() > 0){return true;}
        return false;
    }

    //2 정보 호출
    @Transactional
    public MemberDto info( int mno){
        Optional<MemberEntity> entityOptional =
                memberEntityRepository.findById(mno);

        if(entityOptional.isPresent()) {
            MemberEntity entity = entityOptional.get();
            return entity.toDto();
        }
        return null;
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
}
