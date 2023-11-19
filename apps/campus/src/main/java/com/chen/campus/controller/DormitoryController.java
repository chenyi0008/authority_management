package com.chen.campus.controller;


import com.chen.base.BaseController;
import com.chen.base.R;
import com.chen.campus.entity.Dormitory;
import com.chen.campus.service.IDormitoryService;
import com.chen.campus.service.impl.DormitoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public R<List<Dormitory>> findAllDormitory(){
        List<Dormitory> list = dormitoryService.list();
        return R.success(list);
    }


}

