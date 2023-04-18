package ezenweb.example.web.domain.border;

import ezenweb.example.web.domain.BaseTime;
import ezenweb.example.web.domain.member.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
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



}
