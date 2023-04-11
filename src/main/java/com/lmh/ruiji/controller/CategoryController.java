package com.lmh.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.ruiji.common.R;
import com.lmh.ruiji.entity.Category;
import com.lmh.ruiji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */

    @GetMapping("/page")
    public R<Page> select(int page, int pageSize) {

        Page pageIn = new Page(page, pageSize);

        //封装查询信息,条件构造器

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        queryWrapper.orderByAsc(Category::getUpdateTime);
        //执行查询
        categoryService.page(pageIn, queryWrapper);


        return R.success(pageIn);
    }

    /**
     * 添加分类
     * @param category
     * @return
     */

    @PostMapping
    public R<String> categoryInsert(@RequestBody Category category) {
        boolean save = categoryService.save(category);
        if(save==true){
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }

    /**
     * 根据id删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String>delete( Long ids){
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    /**
     * 根据id修改分类信息
      * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){

        categoryService.updateById(category);
        return R.success("保存成功");
    }



}
