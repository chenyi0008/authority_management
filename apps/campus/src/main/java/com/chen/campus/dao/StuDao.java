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
            "cs.school, cs.number, cs.id_card_number " +
            "FROM auth_user au " +
            "LEFT JOIN campus_stu cs ON au.id = cs.id " +
            "WHERE au.id = #{id}")
    public Stu selectUserAndStuById(@Param("id") Long id);


    @Select("SELECT " +
            "au.id, au.account, au.name, au.mobile, " +
            "cs.school, cs.number, cs.id_card_number " +
            "FROM auth_user au " +
            "LEFT JOIN campus_stu cs ON au.id = cs.id ")
    public List<Stu> selectAllUsersAndStu();

    @Update({
            "UPDATE auth_user " +
                    "SET " +
                    "account = #{account}, " +
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






}
