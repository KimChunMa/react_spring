package ezenweb.example.web.domain.product;

import ezenweb.example.web.domain.BaseTime;
import ezenweb.example.web.domain.file.FileDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity@Table(name="product")
@Data@AllArgsConstructor@NoArgsConstructor @Builder
public class ProductEntity extends BaseTime {
    @Id     private String id; //제품번호 [JPA는 1개 이상 ID=PK 필요]
    @Column(nullable = false) private String pname;//제품명 [JPA로 DB 필드 선언시 _ 제외]
    @Column(nullable = false) private int pprice;//제품가격
    @Column(nullable = false) private String pcategory;//제품카테고리
    @Column(nullable = false, columnDefinition = "TEXT") private String pcomment;//제품설명
    @Column(nullable = false, length = 100) private String pmanufacturer;//제조사
    @ColumnDefault("0")@Column(nullable = false)private byte pstate;// 제품상태 [0:판매중, 1:판매중지, 2:재고없음]
    @ColumnDefault("0")@Column(nullable = false)private int pstock;//제품 재고/수량
    // 제품이미지 [1:다] 연관관계 [추후]
    //pk 필드 선언시 mappedBy = "참조할필드명" // 제약조건 : PK객체가 삭제되면 fk객체 제약조건
    // ALL: pk 객체 삭제시 fk 객체 모두 삭제/수정 ,
    @OneToMany( mappedBy = "productEntity", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @Builder.Default
    private List<ProductImgEntity> productImgEntityList = new ArrayList<>();
    // 구매내역 [1:다] 연관관계 [추후]

    //1. 관리자페이지
    public ProductDto toAdminDto() {
        return ProductDto.builder()
                .id(this.id)
                .pname(this.pname)
                .pprice(this.pprice)
                .pcategory(this.pcategory)
                .pcomment(this.pcomment)
                .pmanufacturer(this.pmanufacturer)
                .pstate(this.pstate)
                .pstock(this.pstock)

                .cdate(this.cdate.toString())
                .udate(this.udate.toString())
                .build();
    }

    //2.사용자 페이지
    public ProductDto toMainDto(){
        List<FileDto> list =
            this.getProductImgEntityList().stream().map(
                        imgEntity->imgEntity.toFileDto()
                    )
                    .collect(Collectors.toList());


        return ProductDto.builder()
                .id(this.id).pname(this.pname)
                .pprice(this.pprice) .pcategory(this.pcategory)
                .pmanufacturer(this.pmanufacturer)
                .files(list)
                .build();
    }

}
