package ezenweb.example.web.domain.border;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardEntityRepository extends JpaRepository<BoardEntity, Integer> {
    // JPA 형식이아닌 순수 SQL 적용하는 함수 정의
        //동일한 cno 찾기
        // select * from board where cno = ?
            //ps.setInt(1,cno);
        // [JPA] select * from board where cno = :cno
            //:매개변수명 (해당 함수의 매개변수 이름)
    @Query(value =  " select * " +
                    " from border " +
                    " where if( :cno = 0 , cno like '%%' , cno = :cno ) "
                    , nativeQuery = true)
    Page<BoardEntity> findBySearch(int cno, Pageable pageable);

}
