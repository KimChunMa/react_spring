package ezenweb.example.web.domain.border;

import ezenweb.example.web.domain.BaseTime;
import ezenweb.example.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Builder@Data
@NoArgsConstructor@AllArgsConstructor
@Entity@Table(name="Reply")
public class ReplyEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @Column
    private String rcontent;

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

    public ReplyDto toDto(){
        return ReplyDto.builder()
                .bno(this.boardEntity.getBno())
                .rno( this.rno ).rcontent( this.rcontent )
                .rindex(this.rindex)
                .rdate( this.cdate.toLocalDate().toString() )
                .mno(this.memberEntity.getMno())
                .mname(this.memberEntity.getMname())
                // cdate[ LocalDateTime ] <--> rdate[ String ]
                .build();
    }

}
