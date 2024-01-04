package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
