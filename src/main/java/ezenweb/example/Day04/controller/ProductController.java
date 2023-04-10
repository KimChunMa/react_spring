package ezenweb.example.Day04.controller;

import ezenweb.example.Day04.domain.dto.ProductDto;
import ezenweb.example.Day04.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController //컨트롤러
@RequestMapping("/item")
public class ProductController {

    //컨테이너에 등록된 빈 자동 주입
    @Autowired
    private ProductService service;

    //-------------- 1. [React.Js] 사용전 HTML 반환 ----------------
    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/item.html");
    }

    // ------------2.    Restful API---------------

    // 1. 저장
    @PostMapping("/write")
    public boolean write(@RequestBody ProductDto dto ) {
        return service.write(dto);
    }

    //2. 수정
    @PutMapping("/update")
    public boolean update(@RequestBody ProductDto dto ) {
        return service.update(dto);
    }

    //3. 삭제
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam int pno){
        return service.delete(pno);
    }

    //4. 출력
    @GetMapping("/get")
    public ArrayList<ProductDto> get(){
        ArrayList<ProductDto> result = service.get();
        System.out.println(result);
        return result;
    }

    // 3. 유효성 검사
}
