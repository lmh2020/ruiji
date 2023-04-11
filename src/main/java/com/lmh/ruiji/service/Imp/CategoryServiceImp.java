package com.lmh.ruiji.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.ruiji.common.CustomException;
import com.lmh.ruiji.entity.Category;
import com.lmh.ruiji.entity.Dish;
import com.lmh.ruiji.entity.Setmeal;
import com.lmh.ruiji.mapper.CategoryMapper;
import com.lmh.ruiji.service.CategoryService;
import com.lmh.ruiji.service.DishService;
import com.lmh.ruiji.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /**
     * 根据id进行删除
     *
     * @param id
     */
    @Autowired
    private SetMealService setMealServic;
    @Autowired
    private DishService dishService;

    @Override
    public void remove(Long id) {
        //添加查询条件，根据分类的id进行查询
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联菜品，如果已经关联，抛出异常
        if (count > 0) {
            //抛出业务异常
            throw new CustomException("当前分类下关联菜品，不能删除");
        }

        //查询当前分类是否关联套菜，如果已经关联，抛出异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setMealServic.count(setmealLambdaQueryWrapper);
        if (count1 > 0) {
            //抛出业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除
         super.removeById(id);
    }
}
