package ezenweb.example.web.domain.border;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

 
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity@Table(name="bcategory")
@Data@NoArgsConstructor@AllArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno ;

    @Column
    private String cname;

    //양뱡향
    //카테고리 [pk] <----> 게시물 [fk]
    //pk는 fk흔적 남기지 않는다 [필드 존재 X  객체존재 o]
    @OneToMany(mappedBy = "categoryEntity") // 하나가 다수에게 [pk --> fk]
    @Builder.Default
    private List<BoardEntity> borderEntityList = new ArrayList<>();


}
