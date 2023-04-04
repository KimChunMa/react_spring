package ezenweb.example.Day02.controller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/gethtml")
@RestController
public class ResourceMappingController {
    @GetMapping("/test1")
    public Resource gettest(){
        return new ClassPathResource("templates/test1.html");

    }
}