package demo.serivce.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.dto.DishDto;
import demo.entity.Dish;
import demo.entity.DishFlavor;
import demo.mapper.DishMapper;
import demo.serivce.DishFlavorService;
import demo.serivce.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;


    @Override
    @Transactional //涉及到对多张表的数据进行操作,需要加事务，需要事务生效,需要在启动类加上事务注解生效
    public void saveWithFlavor(DishDto dishDto) {
        //保存到dish表中
        this.save(dishDto);
        Long dishId = dishDto.getId();
        //为了把dishId  set进flavors表中
        //拿到菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        //这里对集合进行赋值 可以使用循环或者是stream流
        flavors = flavors.stream().map((item) ->{
            //拿到的这个item就是这个DishFlavor集合
            item.setDishId(dishId);
            return item; //记得把数据返回去
        }).collect(Collectors.toList()); //把返回的集合搜集起来,用来被接收

        //把菜品口味的数据到口味表 dish_flavor  注意dish_flavor只是封装了name value 并没有封装dishId(从前端传过来的数据发现的,然而数据库又需要这个数据)
        dishFlavorService.saveBatch(dishDto.getFlavors()); //这个方法是批量保存

    }
}
