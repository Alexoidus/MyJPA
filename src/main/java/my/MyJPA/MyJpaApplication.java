package my.MyJPA;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import my.MyJPA.logging.Mdc;
import my.MyJPA.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@SpringBootApplication
@RestController
@Slf4j
public class MyJpaApplication {

    @Autowired
    private ArtistService artistService;

    public static void main(String[] args) {
        SpringApplication.run(MyJpaApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        artistService.test();
        return String.format("Hello %s!", name);
    }

    @Component
    public static class UniqueTrackingNumberFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            @Cleanup
            val mdc = Mdc.put(Map.of(
                    "http.request.remote-ip", request.getRemoteAddr(),
                    "http.request.user-agent", request.getHeader("User-Agent"),
                    "http.request.method", request.getMethod(),
                    "http.request.url", request.getRequestURL().toString(),
                    "http.request.query", request.getQueryString()
            ));

            filterChain.doFilter(request, response);
        }
    }

}
