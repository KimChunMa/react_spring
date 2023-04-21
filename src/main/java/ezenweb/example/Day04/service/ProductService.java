package ezenweb.example.Day04.service;

import ezenweb.example.Day04.domain.dto.ProductDto;
import ezenweb.example.Day04.domain.entity.product.ProductEntity;
import ezenweb.example.Day04.domain.entity.product.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    // 컨테이너에 등록된 리포지토리 빈 자동 주입
    @Autowired
    private ProductEntityRepository productEntityRepository;

    //1. 저장
    public boolean write(ProductDto dto){
        ProductEntity entity = productEntityRepository.save(dto.toEntity());
        
        if(entity.getPno() >0 ){
            return true;
        }
        return false;
    }

    //2. 수정
    @Transactional
    public boolean update(ProductDto dto){
        Optional<ProductEntity> optionalProductEntity =
                productEntityRepository.findById(dto.getPno());

        if(optionalProductEntity.isPresent()){
            ProductEntity entity = optionalProductEntity.get();
            entity.setPcontent(dto.getPcontent() );
            return true;
        }
        return false;
    }

    //3. 삭제
    public boolean delete(int pno){
        Optional<ProductEntity> optionalProductEntity =
                productEntityRepository.findById(pno);

        if(optionalProductEntity.isPresent()){
            ProductEntity productentity = optionalProductEntity.get();
            productEntityRepository.delete(productentity);
            return true;
        }
        return false;
    }

    //4. 출력
    public ArrayList<ProductDto> get(){
        List<ProductEntity> plist = productEntityRepository.findAll();

        ArrayList<ProductDto> list = new ArrayList<>();

        plist.forEach( e -> {
            list.add(e.toDto());
        });
        return list;
    }
}
