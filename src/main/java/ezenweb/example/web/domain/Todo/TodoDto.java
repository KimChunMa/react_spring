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
    private String id; //식별번호
    private String title; //제목
    private boolean done; //  체크여부

    public TodoEntity toEntity(){
        return TodoEntity.builder()
                .id(this.id)
                .title(this.title)
                .done(this.done)
                .build();

    }
}
