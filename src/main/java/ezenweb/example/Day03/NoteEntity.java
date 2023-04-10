package ezenweb.example.Day03;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//객체 = 레코드 , 테이블 = 클래스
@Entity // 테이블과 해당 클래스 객체간 매핑 [연결]
@Table(name = "notes")
@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteEntity {
    @Id // primary key // JAP 사용시 1개이상 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto key
    private int nno;
    @Column
    private String ncontents;

    //entity --> dto [서비스에서 사용]
    public NoteDto toDto(  ){
        return NoteDto.builder()
                .nno(this.nno)
                .ncontents(this.ncontents)
                .build();
    }
}
