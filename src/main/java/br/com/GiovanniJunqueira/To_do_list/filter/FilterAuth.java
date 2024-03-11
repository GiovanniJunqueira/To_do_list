package br.com.GiovanniJunqueira.To_do_list.filter;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterAuth extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorization = request.getHeader("Authorization");
        var clean = authorization.substring("Basic".length()).trim();
        byte[] descript = Base64.getDecoder().decode(clean);
        var descriptString = new String(descript);
        String[]credentials = descriptString.split(":");
        String username = credentials[0];
        String password = credentials[1];
    }
}
