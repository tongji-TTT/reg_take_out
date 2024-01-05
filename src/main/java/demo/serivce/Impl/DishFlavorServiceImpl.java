package demo.serivce.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.entity.DishFlavor;
import demo.mapper.DishFlavorMapper;
import demo.serivce.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
