package ezenweb.example.web.domain.border;

import ezenweb.example.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private int mno;
    private String mname;

    @Builder.Default // 빌더 이용한 객체 생성시 혀냊 필드 정보 기본값으로 사용
    private List<ReplyDto> rereplyDtolist = new ArrayList<>();

    // 저장용
    public ReplyEntity toEntity(){
        return ReplyEntity.builder()
                .rcontent( this.rcontent )
                .rindex(this.rindex)
                .build();
    }
}
