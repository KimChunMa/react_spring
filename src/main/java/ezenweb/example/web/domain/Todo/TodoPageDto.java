package ezenweb.example.web.domain.Todo;

import ezenweb.example.web.domain.border.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoPageDto {
    //전체 개시물수
    private long totalCount;
    //전체 페이지 수
    private int totalPage;
    //현재 페이지번호
    private int page;

    private List<TodoDto> tododtoList;
}
