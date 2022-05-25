package kz.sdu.project.sauapbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.sdu.project.sauapbackend.dto.ErrorDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private ObjectMapper objectMapper;

    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] AUTH_WHITELIST = {
                "/actuator/health",
        };
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper));
    }
}

class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String errorJson;

    CustomAuthenticationEntryPoint(ObjectMapper objectMapper) throws IOException {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorCode("-1001");
        errorDto.setMessage("Unauthorized");
        errorJson = objectMapper.writeValueAsString(errorDto);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"Realm\"");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter writer = response.getWriter();
        writer.print(errorJson);
        writer.flush();
    }
}
