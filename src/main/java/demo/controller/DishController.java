package demo.controller;


import demo.common.R;
import demo.dto.DishDto;
import demo.serivce.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    DishService dishService;

    @PostMapping()
    public R<String> save(@RequestBody DishDto dishDto){ //前端提交的是json数据的话，我们在后端就要使用这个注解来接收参数，否则接收到的数据全是null
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

}
