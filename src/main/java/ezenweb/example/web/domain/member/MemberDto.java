package ezenweb.example.web.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data@Builder
@AllArgsConstructor@NoArgsConstructor
public class MemberDto {
    //회원번호, 아이디[메일], 비번, 이름, 전화번호 , 등급
    private int mno;
    private String memail;
    private String mpw;
    private String mname;
    private String mphone;
    private String mrole;

    private LocalDateTime cdate;
    private LocalDateTime udate;

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno) .memail(this.memail)
                .mpw(this.mpw).mname(this.mname)
                .mphone(this.mphone).mrole(this.mrole)
                .build();
    }

}
