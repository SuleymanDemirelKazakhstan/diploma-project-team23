package kz.sdu.project.sauapbackend;

import kz.sdu.project.sauapbackend.entity.AdminUsers;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;


public class SauapAdminUser implements UserDetails {
    @Getter
    private Long userId;
    private String username;
    private String password;
    @Getter
    private Boolean isActive;
    private String[] roles;

    public SauapAdminUser(AdminUsers adminUsers) {
        Objects.requireNonNull(adminUsers, "empty admin user");
        this.userId = adminUsers.getAdminUserId();
        this.username = adminUsers.getUsername();
        this.password = adminUsers.getPassword();
        this.isActive = adminUsers.getIsActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ADMIN");
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return this.password;
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
        return isActive;
    }
}
