package demo.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.entity.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
