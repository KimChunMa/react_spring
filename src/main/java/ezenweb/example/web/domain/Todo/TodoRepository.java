package ezenweb.example.web.domain.Todo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository   extends JpaRepository<TodoDto, Integer> {
}
