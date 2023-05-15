package ezenweb.example.web.domain.product;

import ezenweb.example.web.domain.file.FileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductDto {
    private String id; //제품번호 [JPA는 1개 이상 ID=PK 필요]
    private String pname;//제품명 [JPA로 DB 필드 선언시 _ 제외]
    private int pprice;//제품가격
    private String pcategory;//제품카테고리
    private String pcomment;//제품설명
    private String pmanufacturer;//제조사
    private byte pstate;// 제품상태 [0:판매중, 1:판매중지, 2:재고없음]
    private int pstock;//제품 재고/수량

    //관리자용
    private String cdate;
    private String udate;
    //첨부파일 입력용
    private List<MultipartFile> pimgs;

    //첨부파일 출력용
    private List<FileDto> files = new ArrayList<>();

    public ProductEntity toSaveEntity() {
        return ProductEntity.builder()
                .id(this.id)
                .pname(this.pname)
                .pprice(this.pprice)
                .pcategory(this.pcategory)
                .pcomment(this.pcomment)
                .pmanufacturer(this.pmanufacturer)
                .pstate(this.pstate)
                .pstock(this.pstock)
                .build();
    }
}
