package com.chen.campus.dao;

import com.chen.campus.entity.Stu;
import lombok.Data;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface StuDao {

    @Select("SELECT " +
            "au.id, au.account, au.name, au.mobile, " +
            "cs.school, cs.number, cs.id_card_number, cs.dormitory_id " +
            "FROM auth_user au " +
            "LEFT JOIN campus_stu cs ON au.id = cs.id " +
            "WHERE au.id = #{id}")
    public Stu selectUserAndStuById(@Param("id") Long id);


    @Select("SELECT " +
            "au.id, au.account, au.name, au.mobile, " +
            "cs.school, cs.number, cs.id_card_number, cs.dormitory_id " +
            "FROM auth_user au " +
            "LEFT JOIN campus_stu cs ON au.id = cs.id ")
    public List<Stu> selectAllUsersAndStu();

    @Update({
            "UPDATE auth_user " +
                    "SET " +
                    "name = #{name}, " +
                    "mobile = #{mobile} " +
                    "WHERE id = #{id};",

            "UPDATE campus_stu " +
                    "SET " +
                    "school = #{school}, " +
                    "number = #{number}, " +
                    "id_card_number = #{idCardNumber} " +
                    "WHERE id = #{id};"
    })
    public Integer updateUserAndStu(Stu stu);

    @Update("UPDATE campus_stu SET balance = balance + #{money} WHERE id = #{id}")
    public Integer campusCardRecharge(@Param("id") Long id, @Param("money") double money);






}
