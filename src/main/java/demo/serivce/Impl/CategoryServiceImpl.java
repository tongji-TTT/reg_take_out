package demo.serivce.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.entity.Category;
import demo.mapper.CategoryMapper;
import demo.serivce.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
