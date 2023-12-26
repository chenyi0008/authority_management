package com.chen.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.campus.dao.StuDao;
import com.chen.campus.entity.Stu;
import com.chen.campus.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuServiceImpl implements IStuService  {

    @Autowired
    private StuDao stuDao;

    @Override
    public Stu getStuById(Long id) {
        return stuDao.selectUserAndStuById(id);
    }

    @Override
    public List<Stu> getAllStu() {
        return stuDao.selectAllUsersAndStu();
    }

    @Override
    public Integer updateStu(Stu stu) {
        return stuDao.updateUserAndStu(stu);
    }

    @Override
    public Integer campusCardRecharge(Long id, double money) {
        return stuDao.campusCardRecharge(id, money);
    }

    @Override
    public Stu getStuByStuNum(String stuNum) {
        return stuDao.getStuByNumber(stuNum);
    }


}
