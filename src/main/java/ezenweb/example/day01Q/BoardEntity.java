package ezenweb.example.day01Q;

import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "testboard") // DB 테이블과 연결된 클래스 매핑[연결]
@Setter@Getter@ToString //롬북
@AllArgsConstructor@NoArgsConstructor // 롬북
@Builder // 롬북
public class BoardEntity {
    @Id // pk [jpa 엔티티/테이블 당 PK 하나 이상 무조건 선언]
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto key
    private int bno;

    @Column
    private String btitle;

    @Column
    private String bcontent;

}
