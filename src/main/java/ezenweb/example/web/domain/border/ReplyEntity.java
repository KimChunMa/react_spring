package ezenweb.example.web.domain.border;

import ezenweb.example.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Builder@Data
@NoArgsConstructor@AllArgsConstructor
@Entity@Table(name="reply")
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @Column
    private String rcontent;

    @Column
    private String rdate;

    @Column
    private int rindex;

    //FK = 외래키
    //카테고리 번호
    @ManyToOne //pk  다수가 하나에게 [fk --> pk]
    @JoinColumn(name="mno") // pk필드명
    @ToString.Exclude // 해당필드는  @ToString을 안쓰겠다. [양뱡향]
    private MemberEntity memberEntity;

    @ManyToOne //pk  다수가 하나에게 [fk --> pk]
    @JoinColumn(name="bno") // pk필드명
    @ToString.Exclude // 해당필드는  @ToString을 안쓰겠다. [양뱡향]
    private BoardEntity boardEntity;

}
