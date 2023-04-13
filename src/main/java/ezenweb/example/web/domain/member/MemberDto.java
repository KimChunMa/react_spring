package ezenweb.example.web.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

//시큐리티 + 일반 DTO
@Data@Builder
@AllArgsConstructor@NoArgsConstructor
public class MemberDto implements UserDetails {
    //회원번호, 아이디[메일], 비번, 이름, 전화번호 , 등급
    private int mno;
    private String memail;
    private String mpw;
    private String mname;
    private String mphone;
    private String mrole;
    private Set<GrantedAuthority> 권한목록;

    private LocalDateTime cdate;
    private LocalDateTime udate;


    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno) .memail(this.memail)
                .mpw(this.mpw).mname(this.mname)
                .mphone(this.mphone).mrole(this.getMrole())
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.권한목록;
    }

    @Override
    public String getPassword() {
        return this.mpw;
    }

    @Override
    public String getUsername() {
        return this.memail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
