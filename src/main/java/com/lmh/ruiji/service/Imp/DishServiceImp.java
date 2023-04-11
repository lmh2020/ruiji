package com.lmh.ruiji.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.ruiji.entity.Dish;
import com.lmh.ruiji.mapper.DishMapper;
import com.lmh.ruiji.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImp extends ServiceImpl<DishMapper,Dish> implements DishService {
}
