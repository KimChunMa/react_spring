package ezenweb.example.web.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductEntityRepository extends JpaRepository<ProductEntity,String> {


    @Query(value = "select * from product where pstate = 0", nativeQuery=true)
    List<ProductEntity> findAllState();

}
