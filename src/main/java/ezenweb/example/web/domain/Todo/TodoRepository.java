package ezenweb.example.web.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TodoRepository   extends JpaRepository<TodoEntity, String> {
    @Query(value =  " select * from todo ; "
            , nativeQuery = true)
    Page<TodoEntity> findBySearch(Pageable pageable);
}
