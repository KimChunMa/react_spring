package ezenweb.example.Day04.domain.entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEntityRepository
    extends JpaRepository<ProductEntity , Integer> {

}
