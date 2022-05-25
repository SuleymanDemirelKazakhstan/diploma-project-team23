package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.SauapAdminUser;
import kz.sdu.project.sauapbackend.entity.AdminUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SauapUserDetailsService implements UserDetailsService {

    private SecurityService securityService;

    @Autowired
    public SauapUserDetailsService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AdminUsers> adminUserOptional = securityService.getAdminUserInfo(username);
        if (adminUserOptional.isPresent()) {
            return new SauapAdminUser(adminUserOptional.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
