package com.chen.campus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.chen.base.R;
import com.chen.campus.dto.ApplyDTO;
import com.chen.campus.dto.ApplyStuDTO;
import com.chen.campus.entity.Apply;
import com.chen.campus.entity.Stu;
import com.chen.campus.service.IApplyService;
import com.chen.campus.service.IStuService;
import com.chen.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chen
 * @since 2023-11-22
 */
@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private IApplyService applyService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private IStuService stuService;


    @PostMapping
    public R createApply(@RequestBody @Validated ApplyDTO dto) {
        Apply apply = dozerUtils.map(dto, Apply.class);
        boolean save = applyService.save(apply);
        if (save) {
            return R.success();
        } else {
            return R.fail("保存失败");
        }
    }

    @GetMapping
    public R getAll(){
        List<ApplyStuDTO> dto = applyService.getApplyAndStu();
        return R.success(dto);
    }

    @PutMapping
    public R ExamineAndApprove(){
        return null;
    }





}

