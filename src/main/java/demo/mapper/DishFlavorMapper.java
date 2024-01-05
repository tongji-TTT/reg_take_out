package demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
