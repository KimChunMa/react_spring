package ezenweb.example.web.service;


import ezenweb.example.web.domain.MemberEntityRepository;
import ezenweb.example.web.domain.border.*;
import ezenweb.example.web.domain.member.MemberDto;
import ezenweb.example.web.domain.member.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardService {
    @Autowired
    private CategoryEntityRepository categoryEntityRepository;
    @Autowired
    private BoardEntityRepository boardEntityRepository;
    @Autowired
    private MemberEntityRepository memberEntityRepository;

    //1. 카테고리 등록
    @Transactional
    @PostMapping("/category/write")
    public boolean categoryWrite(BoardDto boardDto){
        CategoryEntity entity =
                categoryEntityRepository.save(boardDto.toCategoryEntity());
        if(entity.getCno() >= 1 ) {return true;}
        return false;
    }

    //2. 게시물 쓰기
    @Transactional
    @PostMapping("/write")
    public Boolean write(BoardDto boardDto){
        // 1. 선택된 카테고리 번호를 이용한 카테고리 엔티티 찾기
        Optional<CategoryEntity> entity =
                categoryEntityRepository.findById(boardDto.getCno());
        //2. 만약 선택된 카테고리가 존재하지 않으면 리턴
        if(!entity.isPresent()){return false;}
        //3. 카테고리 엔티티 추출
        CategoryEntity categoryEntity = entity.get();

        //로그인된 회원 엔티티 찾기
        //1. 인증된 인증 정보 찾기
        Object o =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){return false;}
        //2. 형변환
        MemberDto loginDto = (MemberDto)o;
        //3. 회원 엔티티 찾기
        MemberEntity memberEntity = memberEntityRepository.findByMemail(loginDto.getMemail());


        //4. 게시물 쓰기
        BoardEntity boardEntity =
                boardEntityRepository.save(boardDto.toBoardEntity());
        if(boardEntity.getBno() <1){return false;}

        //5. 양방향
            //1. 카테고리 엔티티에 생성된 게시물 등록
        categoryEntity.getBorderEntityList().add(boardEntity);
            //2. 생성된 게시물에 카테고리 엔티티 등록
        boardEntity.setCategoryEntity(categoryEntity);

        //1. 생성된 게시물 엔티티에 로그인된 엔티티
        boardEntity.setMemberEntity(memberEntity);
        //2. 로그인된 회원 엔티티에 게시물 넣기
        memberEntity.getBoardEntityList().add(boardEntity);

        // ----- 내가쓴글
        log.info(boardEntity.toString());
        return false;
    }

    //3. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards(){

        return null;
    }
}
