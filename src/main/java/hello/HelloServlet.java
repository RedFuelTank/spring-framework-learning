package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloServlet {
    @GetMapping("/hello")
    public String sendHello() {
        int i = 1 / 0;
        return "Hello";
    }
}
