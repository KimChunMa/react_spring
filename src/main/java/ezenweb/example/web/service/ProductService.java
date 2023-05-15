package ezenweb.example.web.service;

import ezenweb.example.web.domain.file.FileDto;
import ezenweb.example.web.domain.product.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    @Autowired private ProductEntityRepository productEntityRepository;

    @Autowired FileService fileService;
    @Autowired
    ProductImgEntityRepository productImgEntityRepository;

    @Transactional //메인페이지 사진출력용
    public List<ProductDto> mainGet(){
        List<ProductEntity> productEntityList = productEntityRepository.findAllState();
        List<ProductDto> productDtoList = productEntityList.stream().map(
                o->o.toMainDto()).collect(Collectors.toList());
        return productDtoList;
    }

    //1.
    @Transactional
    public List<ProductDto> get() { log.info("get: ");
        //모든 엔티티 호출
        List<ProductEntity> productEntityList = productEntityRepository.findAll();
        //모든 엔티티를 dto로 변환
        List<ProductDto> productDtoList =
                productEntityList.stream().map( o-> o.toAdminDto()).collect( Collectors.toList() );
        return productDtoList;
    }
    // 2.
    @Transactional
    public boolean post(ProductDto productDto){ log.info("post : " + productDto);
        // 1. id 생성 [ 등록 오늘날짜 + 밀리초 + 난수]
        String number = ""; for( int i = 0 ; i<3; i++ ){ number+= new Random().nextInt(10); } // 737 , 831 , 001
        String pid = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddSSS")) + number;
        // 2. dto에 id 넣기
        productDto.setId( pid );
        // 3. db 저장
        ProductEntity productEntity = productEntityRepository.save( productDto.toSaveEntity() );
        // 4. 첨부파일 업로드
        // 만약에 첨부파일 1개 이상이면
        if( productDto.getPimgs().size() != 0 ){
            // 하나씩 업로드
            productDto.getPimgs().forEach( (img)->{
                // 업로드된 파일 결과[리턴]
                FileDto fileDto = fileService.fileupload( img );
                // DB 저장
                ProductImgEntity productImgEntity = productImgEntityRepository.save(
                        ProductImgEntity.builder()
                                .originalFilename( fileDto.getOriginalFilename() )
                                .uuidFile( fileDto.getUuidFile())
                                .build()
                );
                // 단방향 : 이미지객체에 제품객체 등록
                productImgEntity.setProductEntity( productEntity );
                // 양방향 : 제품객체에 이미지객체 등록
                productEntity.getProductImgEntityList().add( productImgEntity );
            });
        }
        return true;
    } // 2 end

    //3.
    @Transactional
    public boolean put(ProductDto productDto) {log.info("put: "+productDto);
        Optional<ProductEntity> entityOptional =
                productEntityRepository.findById(productDto.getId());

        if(entityOptional.isPresent()){
            ProductEntity entity = entityOptional.get();
            entity.setPcomment(productDto.getPcomment());
            entity.setPcategory(productDto.getPcategory());
            entity.setPprice(productDto.getPprice());
            entity.setPname(productDto.getPname());
            entity.setPstate(productDto.getPstate());
            entity.setPmanufacturer(productDto.getPmanufacturer());
            entity.setPstock(productDto.getPstock());
            return true;
        }
        return false;
    }

    // 4. 파일 삭제
    @Transactional
    public boolean delete( String id ){ log.info("delete : " + id);
        // 1. 삭제 할 엔티티 찾기
        Optional<ProductEntity> entityOptional  = productEntityRepository.findById( id );
        // 2. 해당 엔티티가 존재하면
        entityOptional.ifPresent( o -> {
            // 3. 파일도 같이 삭제
            if( o.getProductImgEntityList().size() != 0 ) {
                o.getProductImgEntityList().forEach( (img) -> {
                    // 1. 해당 이미지 경로를 찾아서 파일 객체화
                    File file = new File( fileService.path + img.getUuidFile() );
                    // 2. 만약에 해당 경로의 파일이 존재하면 삭제
                    if( file.exists() ) { file.delete();}
                });
            }
            // 4. DB/엔티티 삭제
            productEntityRepository.delete( o );
        } );
        /* vs
            if( entityOptional.isPresent() ){ productEntityRepository.delete( entityOptional.get() ); } );
         */
        return true;
    }
}