package ezenweb.example.web.domain.product;

import ezenweb.example.web.domain.file.FileDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="productImg")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImgEntity {
    @Id private String uuidFile; //1. 이미지 식별 번호 [오토키]
    @Column private String originalFilename;     //2. 이미지 이름

    //3. 제품 객체[fk]
    @ManyToOne // fk 필드 선언시
    @JoinColumn(name="id") // DB 테이블에 표시될 FK 필드명
    @ToString.Exclude // 순환참조 방지 [양방향일때 필수]
    private ProductEntity productEntity;


    public FileDto toFileDto(){
        return FileDto.builder()
                .uuidFile(this.uuidFile)
                .originalFilename(this.originalFilename)
                .build();
    }

}
