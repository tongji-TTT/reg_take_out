package demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        //log.info("{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

//    @GetMapping("/page")
//    public R<Page> page(int page ,int pageSize){
//        //分页器
//        Page<Category> categoryPage = new Page<>(page,pageSize);
//        //条件构造器
//        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByAsc(Category::getSort);
//        categoryService.page(categoryPage,queryWrapper);
//        return R.success(categoryPage);
//
//    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){

        //创建一个分页构造器
        Page categoryPage = new Page<>(page,pageSize);
        //创建一个条件构造器  用来排序用的  注意这个条件构造器一定要使用泛型，否则使用条件查询这个方法的时候会报错
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        //添加排序条件 ，根据sort字段进行排序
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(categoryPage,queryWrapper);
        return R.success(categoryPage);
    }

}
