package ezenweb.example.Day04.domain.entity.product;

import ezenweb.example.Day04.domain.dto.ProductDto;
import ezenweb.example.Day04.domain.entity.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Entity
@Table(name="item")
@Data@Builder@NoArgsConstructor@AllArgsConstructor

public class ProductEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;
    @Column
    private String pname;
    @Column
    private String pcontent;

    public ProductDto toDto(){
        return ProductDto.builder()
                .pno(this.pno) .pcontent(this.pcontent).pname(this.pname)

                .build();
    }
}
