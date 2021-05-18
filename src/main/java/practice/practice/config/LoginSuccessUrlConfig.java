package practice.practice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import practice.practice.objects.User;
import practice.practice.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class LoginSuccessUrlConfig implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private final UserRepository userRepository;

    @Autowired
    public LoginSuccessUrlConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String redirectUrl = createUrl(userDetails);
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }

    private String createUrl(UserDetails userDetails) {
        Optional<User> user = userRepository.findUserByEmail(userDetails.getUsername());
        return user.map(value -> "personalPage/" + value.getId()).orElse("error");
    }
}
