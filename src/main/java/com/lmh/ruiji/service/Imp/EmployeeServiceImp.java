package com.lmh.ruiji.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.ruiji.entity.Employee;
import com.lmh.ruiji.mapper.EmployeeMapper;
import com.lmh.ruiji.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImp extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


}
