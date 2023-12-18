package demo.serivce.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.entity.Employee;
import demo.mapper.EmployeeMapper;
import demo.serivce.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee>
        implements EmployeeService {

}
