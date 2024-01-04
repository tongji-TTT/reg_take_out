package demo.serivce.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.common.CustomException;
import demo.entity.Category;
import demo.entity.Dish;
import demo.entity.Setmeal;
import demo.mapper.CategoryMapper;
import demo.serivce.CategoryService;
import demo.serivce.DishService;
import demo.serivce.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //按id查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        //注意:这里使用count方法的时候一定要传入条件查询的对象，否则计数会出现问题，计算出来的是全部的数据的条数
        int count = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果已经管理，直接抛出一个业务异常
        if (count > 0){
            //已经关联了菜品，抛出一个业务异常
            throw new CustomException("当前分类项关联了菜品,不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int number = setmealService.count(setmealLambdaQueryWrapper);
        if(number >0) {
            throw new CustomException("当前分类项关联了套餐,不能删除");
        }

        super.removeById(id);
    }
}
