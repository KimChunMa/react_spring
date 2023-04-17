package ezenweb.example.web.domain.border;

import ezenweb.example.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="border")
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BoardEntity {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno ;

    @Column
    private String btitle;

    @Column
    private String bcontent;

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

    //댓글 목록
    @OneToMany(mappedBy = "boardEntity")
    @Builder.Default
    private List<ReplyEntity> replyEntities = new ArrayList<>();



}
