package com.lmh.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmh.ruiji.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工管理
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
