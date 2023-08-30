package pl.foodflow.infrastructure.security.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    SimpleUrlAuthenticationSuccessHandler ownerSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/owner");
    SimpleUrlAuthenticationSuccessHandler customerSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/customer");

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("OWNER")) {
                this.ownerSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            } else if (authorityName.equals("CUSTOMER")) {
                this.customerSuccessHandler.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }
}
