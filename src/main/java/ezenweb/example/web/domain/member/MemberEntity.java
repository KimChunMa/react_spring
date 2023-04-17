package ezenweb.example.web.domain.member;

import ezenweb.example.web.domain.BaseTime;
import ezenweb.example.web.domain.border.BoardEntity;
import ezenweb.example.web.domain.border.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity@Table(name="member")
public class MemberEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;

    @Column(nullable = false)
    private String memail;
    @Column
    private String mpw;
    @Column
    private String mname;
    @Column
    private String mphone;
    @Column
    private String mrole;

    // 게시물 목록 = 내가 쓴 게시물
    @OneToMany(mappedBy = "memberEntity")
    @Builder.Default
    private List<BoardEntity> memberEntityList = new ArrayList<>();

    // 댓글 목록 = 내가 쓴 댓글
    @OneToMany(mappedBy = "memberEntity")
    @Builder.Default
    private List<ReplyEntity> replyEntityList = new ArrayList<>();


    public MemberDto toDto(){
        return MemberDto.builder()
                .mno(this.mno) .memail(this.memail)
                .mpw(this.mpw).mname(this.mname)
                .mphone(this.mphone).mrole(this.mrole)
                .cdate(this.cdate).udate(this.udate)
                .build();
    }
}
