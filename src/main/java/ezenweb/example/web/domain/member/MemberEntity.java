package ezenweb.example.web.domain.member;

import ezenweb.example.web.domain.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public MemberDto toDto(){
        return MemberDto.builder()
                .mno(this.mno) .memail(this.memail)
                .mpw(this.mpw).mname(this.mname)
                .mphone(this.mphone).mrole(this.mrole)
                .cdate(this.cdate).udate(this.udate)
                .build();
    }
}
