package br.com.GiovanniJunqueira.To_do_list.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.GiovanniJunqueira.To_do_list.user.UserRepository;
import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterAuth extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if(servletPath.contains("/task")){
            var authorization = request.getHeader("Authorization");
            var clean = authorization.substring("Basic".length()).trim();
            byte[] descript = Base64.getDecoder().decode(clean);
            var descriptString = new String(descript);
            String[]credentials = descriptString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            var user = this.userRepository.findByUsername(username);
            if(user==null){
                response.sendError(401);
            }
            else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordVerify.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                }
                else {response.sendError(401);}

            }
        }
        else{filterChain.doFilter(request, response);}

    }
}
