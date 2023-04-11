package com.lmh.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.ruiji.common.R;
import com.lmh.ruiji.entity.Employee;
import com.lmh.ruiji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> logon(HttpServletRequest request, @RequestBody Employee employee) {
        String password = employee.getPassword();
        //对密码进行Md5加密
        String password5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        //根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());

        Employee emp = employeeService.getOne(queryWrapper);
        //账号比对

        if (emp == null) {
            return R.error("账号不存在");
        }
        //密码比对
        if (!emp.getPassword().equals(password5)) {

            return R.error("密码错误");
        }
        //查看账号状态
        if (emp.getStatus() == 0) {
            return R.error("账号被禁用");
        }
        //登录成功，将员工的id存入session并返回登陆成功结果
        request.getSession().setAttribute("employee", emp.getId());
        Long employee1 = (Long) request.getSession().getAttribute("employee");
        log.info("employee", employee1);
        return R.success(emp);

    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中当前员工的id
        request.getSession().removeAttribute("employee");

        return R.success("退出成功·");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("员工信息 {}", employee);
        //设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));

//        LocalDateTime now = LocalDateTime.now();
//        employee.setCreateTime(now);
//        employee.setUpdateTime(now);
        Long empID = (Long) request.getSession().getAttribute("employee");

//        employee.setCreateUser(empID);
//        employee.setUpdateUser(empID);
        employeeService.save(employee);


        return R.success("添加成功");
    }

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
//        log.info("page={};pageSize={};name={}", page, pageSize, name);
        Page pageIn = new Page(page, pageSize);
        //查询所有数据
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //排序
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageIn, lambdaQueryWrapper);
        return R.success(pageIn);
    }

    /**
     * 根据id修改状态
     *
     * @param employee
     * @return
     */

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
//        log.info(employee.toString());
        Long id = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(id);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */

    @GetMapping("/{id}")
    public R<Employee> getByid(@PathVariable Long id) {
        Employee byId = employeeService.getById(id);

        return R.success(byId);


    }
}
