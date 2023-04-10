package ezenweb.example.web.domain;

import ezenweb.example.web.domain.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberEntityRepository
        extends JpaRepository<MemberEntity,Integer> {
}
