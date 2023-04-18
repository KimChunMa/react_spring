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
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.*;

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
    public boolean categoryWrite(BoardDto boardDto){
        CategoryEntity entity =
                categoryEntityRepository.save(boardDto.toCategoryEntity());
        if(entity.getCno() >= 1 ) {return true;}
        return false;
    }

    //2. 카테고리 출력
    @Transactional
    public Map<Integer, String > categoryList(){
        List<CategoryEntity> categoryEntityList
                = categoryEntityRepository.findAll();
        // 형변환 List <엔티티> ---> MAP

        Map<Integer,String> map = new HashMap<>();

        categoryEntityList.forEach( (e) -> {
            map.put( e.getCno() , e.getCname()) ;
        });
        return map;
    }



    //3. 게시물 쓰기
    @Transactional
    @PostMapping("/write")
    public byte write(BoardDto boardDto){
        System.out.println("-----------------------------------");
        log.info("s board dto : " + boardDto );

        // 1. 선택된 카테고리 번호를 이용한 카테고리 엔티티 찾기
        Optional<CategoryEntity> entity =
                categoryEntityRepository.findById(boardDto.getCno());
        //2. 만약 선택된 카테고리가 존재하지 않으면 리턴
        if(!entity.isPresent()){return 1;}
        //3. 카테고리 엔티티 추출
        CategoryEntity categoryEntity = entity.get();

        //로그인된 회원 엔티티 찾기
        //1. 인증된 인증 정보 찾기
        Object o =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){return 2;}
        //2. 형변환
        MemberDto loginDto = (MemberDto)o;
        //3. 회원 엔티티 찾기
        MemberEntity memberEntity = memberEntityRepository.findByMemail(loginDto.getMemail());


        //4. 게시물 쓰기
        BoardEntity boardEntity =
                boardEntityRepository.save(boardDto.toBoardEntity());
        if(boardEntity.getBno() <1){return 3;}

        //5. 양방향
            //1. 카테고리 엔티티에 생성된 게시물 등록
        categoryEntity.getBorderEntityList().add(boardEntity);
            //2. 생성된 게시물에 카테고리 엔티티 등록
        boardEntity.setCategoryEntity(categoryEntity);

        //1. 생성된 게시물 엔티티에 로그인된 엔티티
        boardEntity.setMemberEntity(memberEntity);
        //2. 로그인된 회원 엔티티에 게시물 넣기
        memberEntity.getBoardEntityList().add(boardEntity);

        // 공지사항 게시물 정보 확인
        /*
        Optional<CategoryEntity> optionalCategory =  categoryEntityRepository.findById( 2 );
        log.info( "공지사항 엔티티 확인 :" + optionalCategory.get()  );
        */
        return 4;
    }

    //4. 카테고리별  게시물 출력
    @Transactional
    public List<BoardDto> list(int cno){
        log.info("list cno : "+cno);
        List<BoardDto> list = new ArrayList<>();
        if(cno == 0){ // 카테고리 정보 전체 출력


            List<BoardEntity>  boardEntityList = boardEntityRepository.findAll();
            boardEntityList.forEach((e)->{
                list.add(e.toDto());
            });

        }else{ // 해당 cno의 카테고리 정보 전체 출력
            Optional<CategoryEntity> categoryEntityOptional = categoryEntityRepository.findById(cno);
            if(categoryEntityOptional.isPresent()){
                categoryEntityOptional.get().getBorderEntityList().forEach((e) ->{
                    list.add(e.toDto());
                });
            }
        }

        return list;
    }

    //3. 내가 쓴 게시물 출력
    public List<BoardDto> myboards(){

        return null;
    }
}
