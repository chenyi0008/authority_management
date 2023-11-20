package com.chen.campus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.chen.base.BaseController;
import com.chen.base.R;
import com.chen.campus.dto.ChargeFeeDto;
import com.chen.campus.dto.DormitoryDto;
import com.chen.campus.entity.Dormitory;
import com.chen.campus.service.IDormitoryService;
import com.chen.campus.service.impl.DormitoryServiceImpl;
import com.chen.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chen
 * @since 2023-11-19
 */
@RestController
@RequestMapping("/dormitory")
public class DormitoryController extends BaseController {

    @Autowired
    IDormitoryService dormitoryService;
    @Autowired
    DozerUtils dozerUtils;

    @GetMapping
    public R<List<Dormitory>> findAllDormitory(){
        List<Dormitory> list = dormitoryService.list();
        return R.success(list);
    }

    @PutMapping
    public R update(@RequestBody @Validated DormitoryDto dto){

        Boolean flag = dormitoryService.updateDormitory(dto);
        if(flag){
            return R.success();
        }else {
            return R.fail("更新失败");
        }
    }

    @PutMapping("/charge")
    public R chargeFee(@RequestBody @Validated ChargeFeeDto chargeFeeDto){
        Integer integer = dormitoryService.chargeFee(chargeFeeDto.getId(), chargeFeeDto.getMoney());
        if(integer.equals(0))return R.fail("充值失败");
        return R.success();
    }




}

