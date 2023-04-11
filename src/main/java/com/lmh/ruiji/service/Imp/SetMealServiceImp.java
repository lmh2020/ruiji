package com.lmh.ruiji.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.ruiji.entity.Setmeal;
import com.lmh.ruiji.mapper.SetmealMapper;
import com.lmh.ruiji.service.SetMealService;
import org.springframework.stereotype.Service;

@Service
public class SetMealServiceImp extends ServiceImpl<SetmealMapper, Setmeal> implements SetMealService {
}
