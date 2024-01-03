package demo.controller;

import demo.common.R;
import demo.entity.Category;
import demo.serivce.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/test")
    public int test(){
        return 1;
    }

    @PostMapping()
    public R<String> save(@RequestBody Category category){
        log.info("{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }
}
