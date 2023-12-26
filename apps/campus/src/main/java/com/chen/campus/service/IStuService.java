package com.chen.campus.service;

import com.chen.campus.entity.Stu;

import java.util.List;

public interface IStuService {

    public Stu getStuById(Long id);

    public List<Stu> getAllStu();

    public Integer updateStu(Stu stu);

    public Integer campusCardRecharge(Long id, double balance);

    public Stu getStuByStuNum(String stuNum);

}
