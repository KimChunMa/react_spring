package ezenweb.example.web.domain.border;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository  extends JpaRepository<ReplyEntity, Integer> {
    @Query(value =  " select *  from reply where bno= :bno "
            , nativeQuery = true)
    List<ReplyEntity> findAllBno(int bno);
}
