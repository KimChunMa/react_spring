package ezenweb.example.Day03;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service // MVC 서비스
@Slf4j
public class NoteService {

    @Autowired
    NoteEntityRepository noteEntityRepository;

    //1. 쓰기
    public boolean write(NoteDto noteDto){
        log.info(" service write in " + noteDto);
        // save 는 엔티티 객체만 받음
        NoteEntity entity = noteEntityRepository.save(noteDto.toEntity()) ;
        if(entity.getNno()>= 0){
            //레코드가 생성되었으면
            return true;
        }
        return false;
    }
    //2. 출력
    public ArrayList<NoteDto> get(){
        log.info(" service get in");

        //모든 엔티티 호출
        List< NoteEntity> entityList = noteEntityRepository.findAll();

        //모든 에닡티 형변환 [엔티티 -> dto]
        ArrayList<NoteDto> list = new ArrayList<>();

        entityList.forEach(e -> {
            list.add( e.toDto() );
        });
        return list;
    };



    //3. 삭제
    public boolean delete(int nno){
        log.info(" service delete in : " + nno);

        //1. 삭제할 식별번호[pk]를 이용한 엔티티 검색
        Optional<NoteEntity> optionalNoteEntity= noteEntityRepository.findById(nno);

        //2. 포장클래스 <엔티티>
        //null이면 flase, 객체가 있으면 true
        if( optionalNoteEntity.isPresent() ){ // 포장 클래스내 엔티티가 들어있으면
            NoteEntity noteEntity =  optionalNoteEntity.get(); //포장클래스내 엔티티 객체를 꺼내기/호출
            noteEntityRepository.delete(noteEntity); // 찾은 Entity 삭제
            return true;
        }
        return false;
    }

    //4. 수정
    @Transactional // 해당 메소드내 엔티티 객체 필드의 변화가 있을경우 실시간으로 commit 처리
    public boolean update(NoteDto dto){ log.info(" service update in : " + dto );
        //1. 해당 pk번호를 이용한 엔티티 검색
        Optional<NoteEntity> optionalNoteEntity = noteEntityRepository.findById(dto.getNno());

        //2. 포장 클래스 <엔티티>
        if(optionalNoteEntity.isPresent()){
            NoteEntity noteEntity = optionalNoteEntity.get(); //엔티티 객체 꺼내기

            noteEntity.setNcontent(dto.getNcontent()); //변경하기

            return true;
        }
        return false;
    }
}
