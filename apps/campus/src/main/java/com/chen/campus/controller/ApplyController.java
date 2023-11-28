package com.chen.campus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.chen.base.R;
import com.chen.campus.dto.ApplyDTO;
import com.chen.campus.dto.ApplyStuDTO;
import com.chen.campus.dto.ExamineAndApproveDTO;
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


    /**
     * 学生创建申请
     * @param dto
     * @return
     */
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

    /**
     * 根据学生id获取此学生的所有请求
     * @return
     */
    @GetMapping
    public R getAll(){
        List<ApplyStuDTO> dto = applyService.getApplyAndStu();
        return R.success(dto);
    }

    /**
     * 获取所有学生信息
     * @param stuId
     * @return
     */
    @GetMapping("/stu")
    public R getByStuId(@RequestParam Long stuId){
        List<Apply> list = applyService.getByStuId(stuId);
        return R.success(list);
    }

    /**
     * 管理员审核学生申请
     * @param dto
     * @return
     */
    @PutMapping
    public R ExamineAndApprove(@RequestBody @Validated ExamineAndApproveDTO dto){
        Boolean flag = applyService.examineAndApprove(dto.getApplyId(), dto.getStatus());
        if(flag){
            return R.success();
        }else {
            return R.fail("更新失败");
        }
    }





}

