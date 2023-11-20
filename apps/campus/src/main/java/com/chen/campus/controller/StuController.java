package com.chen.campus.controller;


import com.chen.base.BaseController;
import com.chen.base.R;
import com.chen.campus.entity.Stu;
import com.chen.campus.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stu")
public class StuController extends BaseController {

    @Autowired
    private IStuService stuService;

    @GetMapping
    public R<List<Stu>> getStuAll(){
        List<Stu> list = stuService.getAllStu();
        return R.success(list);
    }

    @GetMapping("/{id}")
    public R<Stu> getStuById(@PathVariable Long id){
        Stu stu = stuService.getStuById(id);
        return R.success(stu);
    }

    @PutMapping
    public R updateStu(@RequestBody Stu stu){
        stuService.updateStu(stu);
        return R.success();
    }


}
