package ezenweb.example.web.domain.border;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    //전체 개시물수
    private long totalCount;
    //전체 페이지 수
    private int totalPage;
    //현재 페이지의 게시물 dto들
    private List<BoardDto> boardDtoList;
    //현재 페이지번호
    private int page;
    //현재 카테고리 번호
    private int cno;

}
