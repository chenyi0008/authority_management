package com.chen.campus.controller;


import com.chen.base.BaseController;
import com.chen.base.R;
import com.chen.campus.dto.CampusCardRechargeDTO;
import com.chen.campus.dto.StuDTO;
import com.chen.campus.dto.StuDormitoryInfoDTO;
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

@Slf4j
@RestController
@RequestMapping("/stu")
public class StuController extends BaseController {

    @Autowired
    private IStuService stuService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private IDormitoryService dormitoryService;

    @GetMapping
    public R<List<Stu>> getStuAll(){
        List<Stu> list = stuService.getAllStu();
        return R.success(list);
    }

    @GetMapping("/{id}")
    public R<StuDormitoryInfoDTO> getStuById(@PathVariable Long id){
        Stu stu = stuService.getStuById(id);
        StuDormitoryInfoDTO dto = dozerUtils.map(stu, StuDormitoryInfoDTO.class);
        System.out.println(stu.toString());

        if(stu.getDormitoryId() != null){
            Dormitory dormitory = dormitoryService.getById(stu.getDormitoryId());
            dto.setDormitory(dormitory);
        }

        return R.success(dto);
    }

    @PutMapping
    public R updateStu(@RequestBody @Validated StuDTO dto){
        Stu stu = dozerUtils.map(dto, Stu.class);
        Integer integer = stuService.updateStu(stu);
        if (integer>0) return R.success();
        else return R.fail("更新失败");
    }


    @PutMapping("/charge")
    public R campusCardRecharge(@RequestBody @Validated CampusCardRechargeDTO dto){
        log.warn(dto.toString());
        Integer integer = stuService.campusCardRecharge(dto.getUserId(), dto.getMoney());
        if (integer > 0){
            return R.success();
        }else{
            return R.fail("充值失败");
        }
    }


}
