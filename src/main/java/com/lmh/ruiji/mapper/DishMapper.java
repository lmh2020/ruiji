package com.lmh.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmh.ruiji.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品管理
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
