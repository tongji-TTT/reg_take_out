package demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.common.R;
import demo.entity.Category;
import demo.serivce.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping()
    public R<String> delete(@RequestParam("ids") Long id){
        categoryService.remove(id);
        return R.success("分类信息删除成功");
    }

    @PutMapping()
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list (Category category){
        log.info("{}",category);
        //条件构造器
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查询条件
        lambdaQueryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件  使用两个排序条件,如果sort相同的情况下就使用更新时间进行排序
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return R.success(list);
    }

}
