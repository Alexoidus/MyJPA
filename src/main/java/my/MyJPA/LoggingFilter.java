package my.MyJPA;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;
import my.MyJPA.logging.Mdc;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        @Cleanup final var mdc = Mdc.put(Map.of(
                "http.request.remote-ip", request.getRemoteAddr(),
                "http.request.user-agent", Objects.toString(request.getHeader("User-Agent"), ""),
                "http.request.method", request.getMethod(),
                "http.request.url", request.getRequestURL().toString(),
                "http.request.query", Objects.toString(request.getQueryString(), "")
        ));

        filterChain.doFilter(request, response);
    }
}
