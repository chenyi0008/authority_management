package com.chen.campus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.campus.entity.Lesson;
import com.chen.campus.entity.Stu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StuDao {

    @Select("SELECT " +
            "au.id, au.account, au.name, au.mobile, " +
            "cs.school, cs.number, cs.id_card_number, cs.dormitory_id, cs.cet4, cs.cet6, cs.check_out, cs.credit, cs.balance " +
            "FROM auth_user au " +
            "LEFT JOIN campus_stu cs ON au.id = cs.id " +
            "WHERE au.id = #{id}")
    public Stu selectUserAndStuById(@Param("id") Long id);


    @Select("SELECT " +
            "au.id, au.account, au.name, au.mobile," +
            "cs.school, cs.number, cs.id_card_number, cs.dormitory_id, cs.cet4, cs.cet6, cs.check_out, cs.credit, cs.balance " +
            "FROM campus_stu cs " +
            "LEFT JOIN auth_user au ON au.id = cs.id ")
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

    @Select("SELECT * FROM campus_stu cs LEFT JOIN auth_user au ON au.id = cs.id WHERE number = #{number}")
    public Stu getStuByNumber(@Param("number") String number);


//    @Select("SELECT " +
//            "au.id, au.account, au.name, au.mobile, " +
//            "cs.school, cs.number, cs.id_card_number, cs.dormitory_id, cs.cet4, cs.cet6, cs.check_out, cs.credit " +
//            "FROM auth_user au " +
//            "LEFT JOIN campus_stu cs ON au.id = cs.id " +
//            "WHERE au.id IN (#{list})")
//    public List<Stu> selectUserAndStuByIdList(@Param("list") List<Long> idList);




}
