package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.SauapAdminUser;
import kz.sdu.project.sauapbackend.entity.AdminUsers;
import kz.sdu.project.sauapbackend.repository.AdminUsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    private final AdminUsersRepository adminUsersRepository;

    public Optional<AdminUsers> getAdminUserInfo(String username) {
        return adminUsersRepository.findByUsername(username);
    }

    public SauapAdminUser getCurrentUser() {
        return (SauapAdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
