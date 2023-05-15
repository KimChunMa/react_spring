package ezenweb.example.web.controller;

import ezenweb.example.web.domain.product.ProductDto;
import ezenweb.example.web.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/main") private List<ProductDto> mainGet() {return productService.mainGet();}
    @GetMapping("") private List<ProductDto> get() {return productService.get();}
    @PostMapping("") private boolean post( ProductDto productDto) {
        return  productService.post(productDto);
    }

    @PutMapping("") private boolean put(@RequestBody ProductDto productDto) {
        return productService.put(productDto);
    }
    @DeleteMapping("") private boolean delete(@RequestParam String id) {
        return productService.delete(id);
    }
}

/*
    1. 객체 전송 [post,put]
        axios.post('url',object) @RequestBody

    2. 폼 전송 [post 필수]
        axios.post('url',object) Dto 받을떄는 어노테이션 생략,
                             @RequestParam("form 필드이름") : 폼 내 필드 하나의 데이터 매핑

    3. 쿼리스트링 [get,post,put,delete]
        axios.post('url',{params : {필드명 : 데이터, 필드명 : 데이터} } )
        axios.post('url',{params : object } )
            @RequestParam

    4. 매개변수  [get,post,put,delete]
        axios.post('url/데이터1/데이터')
        @PathVariable

*/