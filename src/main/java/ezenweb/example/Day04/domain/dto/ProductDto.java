package ezenweb.example.Day04.domain.dto;

import ezenweb.example.Day04.domain.entity.product.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // set, get , toString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDto {
        private int pno;
        private String pname;
        private String pcontent;


        public ProductEntity toEntity(){
                return ProductEntity.builder()
                        .pno(this.pno).pcontent(this.pcontent)
                        .pname(this.pname)
                        .build();
        }

}
