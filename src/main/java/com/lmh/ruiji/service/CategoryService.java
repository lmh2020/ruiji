package com.lmh.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.ruiji.entity.Category;


public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
