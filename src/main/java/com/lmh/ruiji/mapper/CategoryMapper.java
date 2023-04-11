package com.lmh.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmh.ruiji.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品分类
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
