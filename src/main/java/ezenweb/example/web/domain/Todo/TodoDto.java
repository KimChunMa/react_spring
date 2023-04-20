package ezenweb.example.web.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private int todoId;
    private String iid; //식별번호
    private String title; //제목
    private boolean done; //  체크여부

    public TodoEntity toEntity(){
        return TodoEntity.builder()
                .todoId(this.todoId)
                .iid(this.iid)
                .title(this.title)
                .done(this.done)
                .build();

    }
}
