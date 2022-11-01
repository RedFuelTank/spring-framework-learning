package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServlet {
    @GetMapping("/hello")
    public String sendHello() {
        return "Hello";
    }
}
