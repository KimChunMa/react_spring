package ezenweb.example.web.domain.border;

import ezenweb.example.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ReplyDto {
    private int rno;
    private String rcontent;
    private String rdate;
    private int bno;

    private  int rindex;

    // 저장용
    public ReplyEntity toEntity(){
        return ReplyEntity.builder()
                .rcontent( this.rcontent )
                .build();
    }
}
