package ezenweb.example.web.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity@Table(name="todo")
public class TodoEntity {

    @Id
    private String id; //식별번호
    @Column
    private String title; //제목
    @Column
    private boolean done; //  체크여부

    public TodoDto toDto(){
        return TodoDto.builder()
                .id(this.id)
                .title(this.title)
                .done(this.done)
                .build();

    }


}
