package demo.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.common.R;
import demo.dto.DishDto;
import demo.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品,同时插入菜品对应的口味数据,需要同时操作两张表:dish  dish_flavor
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    R<String> changeStatusById(Integer status, List<Long> ids);
}
