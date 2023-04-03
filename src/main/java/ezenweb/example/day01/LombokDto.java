package ezenweb.example.day01;

import lombok.*;

@Setter@Getter
@AllArgsConstructor //전체 생성자
@NoArgsConstructor //빈생성자
@ToString
@Builder
public class LombokDto {
    private int mno;
    private String mid;
    private String mpassword;
    private long mpoint;
    private String phone;

    public Testmember2 toEntity(){
        return Testmember2.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpassword(this.mpassword)
                .mpoint(this.mpoint)
                .mphone(this.phone)
                .build();
    }
}
