package com.chen.campus.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.chen.campus.dao.StuDao;
import com.chen.campus.dto.ApplyStuDTO;
import com.chen.campus.entity.Apply;
import com.chen.campus.dao.ApplyDao;
import com.chen.campus.entity.Stu;
import com.chen.campus.service.IApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2023-11-22
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyDao, Apply> implements IApplyService {

    @Autowired
    private IApplyService applyService;

    @Autowired
    private ApplyDao applyDao;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private StuDao studao;
    @Override
    public List<ApplyStuDTO> getApplyAndStu() {
        //获取申请列表
        List<Apply> applyList = applyService.list();

        //申请一个学生id列表，用来存储需要查询的学生id
        List<Long> stuIdList = new ArrayList<>();

        //将申请列表里的学生id存入stuIdList，且要保证不能重复
        for (Apply apply : applyList) {
            if(!stuIdList.contains(apply.getStuId())){
                stuIdList.add(apply.getStuId());
            };
        }

        //查询数据库对应学生id的学生信息
//        List<Stu> res = studao.selectUserAndStuByIdList(stuIdList);
        List<Stu> res = studao.selectAllUsersAndStu();

        //将学生列表转化成map，方便接下来根据学生id获取信息
        Map<Long, Stu> map = new HashMap<>();
        for (int i = 0; i < res.size(); i++) {
            map.put(res.get(i).getId(), res.get(i));
            System.out.println("info:" + res.get(i).toString());
        }



        List<ApplyStuDTO> dtoList = new ArrayList<>();
        for (Apply apply : applyList) {
            ApplyStuDTO dto = dozerUtils.map(apply, ApplyStuDTO.class);
            dto.setStu(map.get(dto.getStuId()));
            System.out.println("map:" + map.get(dto.getStuId()));
            dtoList.add(dto);
            System.out.println(dto.toString());
        }

        for (ApplyStuDTO applyStuDTO : dtoList) {
            System.out.println(applyStuDTO.toString());
        }
        return dtoList;
    }

    @Override
    public Boolean examineAndApprove(Long id, String status) {
        LambdaUpdateChainWrapper<Apply> wrapper = new LambdaUpdateChainWrapper<>(applyDao);
        boolean update = wrapper.eq(Apply::getId, id).set(Apply::getStatus, status).update();
        return update;
    }

    @Override
    public List<Apply> getByStuId(Long stuId){
        LambdaQueryChainWrapper<Apply> wrapper = new LambdaQueryChainWrapper<>(applyDao);
        List<Apply> list = wrapper.eq(Apply::getStuId, stuId).list();
        return list;
    }

}
