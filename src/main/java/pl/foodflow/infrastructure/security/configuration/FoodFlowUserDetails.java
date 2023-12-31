package pl.foodflow.infrastructure.security.configuration;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.foodflow.infrastructure.security.user.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FoodFlowUserDetails implements UserDetails {
    private String userName;
    private String password;
    private Boolean active;
    private List<GrantedAuthority> authorities;

    public FoodFlowUserDetails(UserEntity user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.active = user.getActive();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return active;
    }
}
