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

    private int rindex;

    private int bno;
    private int mno;
}
