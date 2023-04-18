package ezenweb.example.web.domain.border;

import ezenweb.example.web.domain.BaseTime;
import ezenweb.example.web.domain.member.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="border")
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BoardEntity extends BaseTime {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno ;

    @Column
    private String btitle;

    @Column(columnDefinition = "longtext") //mysql text자료형 선택
    private String bcontent;

    @Column
    @ColumnDefault("0") //필드 초기값
    private int bview; // 조회수

    //작성일, 수정일


    //FK = 외래키
    //카테고리 번호
    @ManyToOne //pk  다수가 하나에게 [fk --> pk]
    @JoinColumn(name="cno") // pk필드명
    @ToString.Exclude // 해당필드는  @ToString을 안쓰겠다. [양뱡향]
    private CategoryEntity categoryEntity;

    //회원번호 [작성자]
    @ManyToOne
    @JoinColumn(name="mno")
    @ToString.Exclude //tostring
    private MemberEntity memberEntity;


    // pk =양방향 [해당 댓글 엔티티의 fk 필드와 매핑]
    //댓글 목록
    @OneToMany(mappedBy = "boardEntity")
    @Builder.Default
    private List<ReplyEntity> replyEntities = new ArrayList<>();

    public BoardDto toDto(){
        return BoardDto.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .cno(this.getCategoryEntity().getCno())
                .cname(this.getCategoryEntity().getCname())
                .mno(this.getMemberEntity().getMno())
                .memail((this.getMemberEntity().getMemail()))
                .bview(this.bview)
                //날짜 형변환
                .bdate(
                        //만약 작성 날짜/시간중 날짜가 현재 날짜와 동일하다면
                        this.cdate.toLocalDate().toString().equals(LocalDateTime.now().toLocalDate().toString()) ?
                        this.cdate.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) :
                        this.cdate.toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-DD") )


                )


                .build();
    }


}
