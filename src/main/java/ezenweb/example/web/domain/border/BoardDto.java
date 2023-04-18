package ezenweb.example.web.domain.border;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data@Builder
@NoArgsConstructor @AllArgsConstructor

public class BoardDto {
        private int bno;
        private String btitle;
        private String bcontent;
        private int cno;
        private String cname;

        //추가
        private int mno;
        private String memail;
        private int bview;
        private String bdate;

        //Entity 변환 메소드
        // 1. toSaveCategoryEntity
        public CategoryEntity toCategoryEntity(){
                return new CategoryEntity().builder()
                        .cname(this.cname).build();
        }

        public BoardEntity toBoardEntity(){
                return BoardEntity.builder().btitle(this.btitle)
                        .bcontent(this.bcontent)
                        .build();
        }
}
