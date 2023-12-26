package com.chen.campus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.chen.base.BaseController;
import com.chen.base.R;
import com.chen.campus.dto.BindStuToDormitoryDTO;
import com.chen.campus.dto.ChargeFeeDTO;
import com.chen.campus.dto.DormitoryDTO;
import com.chen.campus.dto.DormitoryStuInfoDTO;
import com.chen.campus.entity.Dormitory;
import com.chen.campus.entity.Stu;
import com.chen.campus.service.IDormitoryService;
import com.chen.campus.service.IStuService;
import com.chen.dozer.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chen
 * @since 2023-11-19
 */
@Slf4j
@RestController
@RequestMapping("/dormitory")
public class DormitoryController extends BaseController {

    @Autowired
    IDormitoryService dormitoryService;
    @Autowired
    DozerUtils dozerUtils;
    @Autowired
    IStuService stuService;


    /**
     * 获取所有宿舍信息
     * @return
     */
    @GetMapping
    public R<List<Dormitory>> findAllDormitory(){
        List<Dormitory> list = dormitoryService.list();
        return R.success(list);
    }

    /**
     * 根据id获取宿舍信息（含本宿舍的学生名单）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable Long id){
        Dormitory dormitory = dormitoryService.getById(id);
        DormitoryStuInfoDTO dto = dozerUtils.map(dormitory, DormitoryStuInfoDTO.class);
        List<Stu> list = stuService.getAllStu().stream().
                filter(stu -> stu.getDormitoryId() != null).
                filter(stu -> stu.getDormitoryId().equals(id)).collect(Collectors.toList());
        dto.setStuList(list);
        return R.success(dto);
    }

    /**
     * 更新宿舍信息
     * @param dto
     * @return
     */
    @PutMapping
    public R update(@RequestBody @Validated DormitoryDTO dto){

        Boolean flag = dormitoryService.updateDormitory(dto);
        if(flag){
            return R.success();
        }else {
            return R.fail("更新失败");
        }
    }

    /**
     * 宿舍电费充值
     * @param chargeFeeDto
     * @return
     */
    @PutMapping("/charge")
    public R chargeFee(@RequestBody @Validated ChargeFeeDTO chargeFeeDto){
        Integer integer = dormitoryService.chargeFee(chargeFeeDto.getId(), chargeFeeDto.getMoney());
        if(integer.equals(0))return R.fail("充值失败");
        return R.success();
    }

    /**
     * 学生绑定宿舍
     * @param dto
     * @return
     */
    @PutMapping("/bind")
    public R bindStuToDormitory(@RequestBody @Validated BindStuToDormitoryDTO dto){
        Stu stu = stuService.getStuByStuNum(dto.getStuNum());
        if(stu == null){
            return R.fail("不存在此学生");
        }
        Integer integer = dormitoryService.bindStuToDormitory(stu.getId(), dto.getDormitoryId());
        if(integer > 0){
            return R.success();

        } else {

            return R.fail("绑定失败");

        }
    }

    /**
     * 学生解绑宿舍
     * @param stuId
     * @return
     */
    @PutMapping("/unbind")
    public R unbindStuToDormitory(Long stuId){
//        Stu stu = stuService.getStuByStuNum(stuNum);
//        if(stu == null){
//            return R.fail("不存在此学生");
//        }
        Integer integer = dormitoryService.unbindStuToDormitory(stuId);
        if(integer > 0){
            log.info("解绑结果：{}", integer);
            return R.success();
        } else {
            return R.fail("解绑失败");

        }
    }




}

