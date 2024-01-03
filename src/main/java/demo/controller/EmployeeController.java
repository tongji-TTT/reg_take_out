package demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.common.*;
import demo.entity.Employee;
import demo.serivce.EmployeeService;
import demo.serivce.Impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/test")
    public int test(){return 1;}

//1、将页面提交的密码password进行md5加密处理
//2、根据页面提交的用户名username查询数据库
//3、如果没有查询到则返回登录失败结果
//4、密码比对，如果不一致则返回登录失败结果
//5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
//6、登录成功，将员工id存入Session并返回登录成功结果
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){//接收前端的json数据,这个json数据是在请求体中的
        //这里为什么还有接收一个request对象的数据?
        //登陆成功后，我们需要从请求中获取员工的id，并且把这个id存到session中，这样我们想要获取登陆对象的时候就可以随时获取

        //1.md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3.4.5.判断该用户是否存在,密码是否正确，用户状态是否正确
        if(emp==null)
        {
            return R.error("该用户不存在");
        }
        else if(!emp.getPassword().equals(password))
        {
            return R.error("密码不正确");
        }
        else if(emp.getStatus()==0)
        {
            return R.error("该用户已被禁用");
        }
        //6.登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);

    }

    /*
    退出账号
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request)
    {
        request.getSession().removeAttribute("employee");

        return R.success("退出成功");
    }

    /*
    新增员工
     */
    @PostMapping()
    public R<String> add(HttpServletRequest request,@RequestBody Employee employee){

        //一开始给新增的员工设置初始化密码123456，并用md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //mybatis 添加方法
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
    /*
     *员工信息分页
     *page 当前页数
     *page Size 当前页可以存放的数据
     *name 根据name查询员工的信息
     */
     @GetMapping("/page")
    public R<Page> page(int page,int pageSize, String name){
         //log.info("page={},pageSize={},name={}",page,pageSize,name);
         //构造分页器
         Page pageInfo = new Page(page,pageSize);
         //构造条件构造器
         //这段代码的作用是根据name字段进行模糊查询，如果name不为空，则添加模糊查询条件；如果name为空，则不添加模糊查询条件。
         LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
         queryWrapper.like(StringUtils.isNotBlank(name),Employee::getName,name);
         //添加一个排序
         queryWrapper.orderByDesc(Employee::getUpdateTime);
         //执行查询
         employeeService.page(pageInfo,queryWrapper);

         return R.success(pageInfo);

     }
     /*
     根据id修改员工信息
      */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee)
    {
        log.info(employee.toString());
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }
    /*
    根据id返回employee信息
     */

    @GetMapping("/{id}")//路径变量
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到该员工信息");
    }





}
