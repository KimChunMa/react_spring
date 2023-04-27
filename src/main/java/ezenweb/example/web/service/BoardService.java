package ezenweb.example.web.service;


import ezenweb.example.web.domain.MemberEntityRepository;
import ezenweb.example.web.domain.border.*;
import ezenweb.example.web.domain.member.MemberDto;
import ezenweb.example.web.domain.member.MemberEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
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
    @Autowired
    private ReplyRepository replyRepository;

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
    public List<CategoryDto>  categoryList(){
        List<CategoryEntity> categoryEntityList
                = categoryEntityRepository.findAll();
        // 형변환 List <엔티티> ---> MAP

        List<CategoryDto> list = new ArrayList<>();

        categoryEntityList.forEach( (e) -> {
           list.add( new CategoryDto(e.getCno() , e.getCname()) ) ;
        });
        return list;
    }



    //3. 게시물 쓰기
    @Transactional
    @PostMapping("/write")
    public byte write(BoardDto boardDto){
        System.out.println("--------------------------------");
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
    public PageDto list(PageDto pageDto){ log.info( "pageDto : " + pageDto );
        Pageable pageable = PageRequest.of(
                pageDto.getPage()-1,3, Sort.by(Sort.Direction.DESC, "bno") ) ;
        //PageRequest.of(페이지번호, 페이지당 표시 개수 Sort.by (Sort.Direction.ASC/DESC, "정렬기준 필드명"))
        Page<BoardEntity> entityPage = boardEntityRepository.findBySearch(
                pageDto.getCno(), pageDto.getKey() , pageDto.getKeyword() , pageable);

        List<BoardDto> list = new ArrayList<>();
        entityPage.forEach( (b)-> {
            list.add(b.toDto());
        });
        log.info("총게시물 수 : " + entityPage.getTotalElements()); log.info("총게시물 수 : " + entityPage.getTotalPages());
        pageDto.setBoardDtoList(list);
        pageDto.setTotalPage(entityPage.getTotalPages());
        pageDto.setTotalCount(entityPage.getTotalElements());
        return pageDto;
    }

    //5. 내가 쓴 게시물 출력
    @Transactional
    public List<BoardDto> myboards(){
        //1. 로그인 인증 세션 [object] => dto 형변환
       MemberDto memberDto = (MemberDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       // 일반회원dto : 모든 정보, oauth2dto : memail, mname, mrol
       //2. 회원 엔티티 찾기
        MemberEntity entity =  memberEntityRepository.findByMemail(memberDto.getMemail());
        //3. 회원 엔티티 내 게시물리스트를 반복문 돌려서 dto 리스트로 변환
        List<BoardDto> list = new ArrayList<>();
        entity.getBoardEntityList().forEach((e)->{
            list.add(e.toDto());
        });
        return list;
    }

    /*------------------------- 과제 ---------------------------------*/
    //6. 게시물 상세 출력
    @Transactional
    public BoardDto s_board(int bno){

        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById( bno );
        if( optionalBoardEntity.isPresent() ){  // 게시물 출력시 현재 게시물의 댓글도 같이~~ 출력
            BoardEntity boardEntity = optionalBoardEntity.get();
            List<ReplyDto> list = new ArrayList<>();
            boardEntity.getReplyEntities().forEach( ( r)->{  // 댓글 같이~~ 형변환 [ toDto vs 서비스 ]
                list.add( r.toDto() );
            });
            BoardDto boardDto = boardEntity.toDto();
            boardDto.setReplyDtoList( list );
            return boardDto;
        }
        return null;
    }

    //7. 게시물 삭제하기
    @Transactional
    public boolean b_del(int bno){
        //시큐리티에서 인증된 회원 정보 가져오기
        MemberDto memberDto =
                (MemberDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BoardEntity boardEntity = boardEntityRepository.findById(bno).get();

        if(boardEntity.getMemberEntity().getMno() == memberDto.getMno()){
            boardEntityRepository.delete(boardEntity);
            return true;
        }
        return false;
    }

    //8. 게시물 수정하기
    @Transactional
    public boolean b_modify(BoardDto boardDto){
       BoardEntity boardEntity =  boardEntityRepository.findById(boardDto.getBno()).get();
       boardEntity.setBtitle(boardDto.getBtitle());
       boardEntity.setBcontent(boardDto.getBcontent());
       return true;
    }

    //9. 댓글 입력하기
    @Transactional
    public boolean post_reply(ReplyDto replyDto) {
        System.out.println("-------------- 입력 --------");
        System.out.println(replyDto);

        //0. 로그인 했는지
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){return false;}
        MemberDto memberDto = (MemberDto)o;
        MemberEntity memberEntity = memberEntityRepository.findById(memberDto.getMno()).get();

        // 0. 댓글작성할 게시물 호출
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById( replyDto.getBno() );
        if( !optionalBoardEntity.isPresent() ){ return false; }
        BoardEntity boardEntity = optionalBoardEntity.get();

        // 1. 댓글 작성한다.
        ReplyEntity replyEntity = replyRepository.save( replyDto.toEntity() );
        if( replyEntity.getRno() < 1 ) { return  false; }

        // 2. 댓글과 회원의 양방향 관계[ 댓글->회원 / 회원 -> 댓글 == 양방향  ,  댓글->회원 == 단방향  ]
        replyEntity.setMemberEntity(  memberEntity );
        memberEntity.getReplyEntityList().add( replyEntity );
        // 3. 댓글과 게시물의 양방향 관계 [ 댓글->게시물 / 게시물->댓글 == 양방향 , 댓글->게시물 == 단방향 ]
        replyEntity.setBoardEntity( boardEntity );
        boardEntity.getReplyEntities().add(replyEntity);

        return true;
    }

    //10 댓글 출력하기
    @Transactional
    public  List<ReplyDto> get_reply(int bno){

        return null;
    }

    //11 댓글 삭제하기
    @Transactional
    public  boolean del_reply(int rno){

        //0. 로그인 했는지
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){return false;}
        MemberDto memberDto = (MemberDto)o;
        MemberEntity memberEntity = memberEntityRepository.findById(memberDto.getMno()).get();

        ReplyEntity replyEntity
                = replyRepository.findById(rno).get();

        if(memberEntity.getMno() == replyEntity.getMemberEntity().getMno() ) {
            replyRepository.delete(replyEntity);
            return true;
        }

        replyRepository.delete(replyEntity);
        return false;
    }

    //12 댓글 수정
    @Transactional
    public boolean putReply(ReplyDto replyDto){

        //0. 로그인 했는지
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){return false;}
        MemberDto memberDto = (MemberDto)o;
        MemberEntity memberEntity = memberEntityRepository.findById(memberDto.getMno()).get();

        Optional<ReplyEntity> optionalReplyEntity
                =replyRepository.findById(replyDto.getRno());

        //게시판이있다면
        if(optionalReplyEntity.isPresent()) {

            ReplyEntity replyEntity = optionalReplyEntity.get();

            //작성가자 맞는지
            if(memberEntity.getMno() == ( replyEntity.getMemberEntity().getMno() ) ){
                replyEntity.setRcontent(replyDto.getRcontent());
                return true;
            }

            return true;
        }








        return false;
    }

}
