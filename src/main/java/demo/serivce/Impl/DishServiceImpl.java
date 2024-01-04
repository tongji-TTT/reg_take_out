package demo.serivce.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.entity.Dish;
import demo.mapper.DishMapper;
import demo.serivce.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
