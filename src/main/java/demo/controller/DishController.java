package demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.common.R;
import demo.dto.DishDto;
import demo.entity.Category;
import demo.entity.Dish;
import demo.entity.Employee;
import demo.serivce.CategoryService;
import demo.serivce.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.beans.Beans;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    DishService dishService;

    @Autowired
    CategoryService categoryService;

    @PostMapping()
    public R<String> save(@RequestBody DishDto dishDto){ //前端提交的是json数据的话，我们在后端就要使用这个注解来接收参数，否则接收到的数据全是null
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page ,int pageSize ,String name)
    {
        //构造分页器
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page,pageSize);
        //构造条件构造器

        //这段代码的作用是根据name字段进行模糊查询，如果name不为空，则添加模糊查询条件；如果name为空，则不添加模糊查询条件。
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(name !=null, Dish::getName,name);
        //添加排序条件  根据更新时间降序排
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //去数据库处理分页 和 查询
        dishService.page(pageInfo,queryWrapper);

        //将dish page 复制到 dishDto page
        List<Dish> dishList = pageInfo.getRecords();

        List<DishDto> dishDtoList = dishList.stream().map((item)->{

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            //获取分类的id
            Long categoryId = item.getCategoryId();
            //通过分类id获取分类对象
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        //对象拷贝  使用框架自带的工具类，第三个参数是不拷贝record属性而拷贝总数等其他数据
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        //放入record属性
        dishDtoPage.setRecords(dishDtoList);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto =new DishDto();
        dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping()
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status,@RequestParam List<Long> ids){

//        log.info("{}",status);
//        log.info("{}",ids);
        return dishService.changeStatusById(status,ids);
    }

}
