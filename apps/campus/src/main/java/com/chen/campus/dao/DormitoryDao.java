package com.chen.campus.dao;

import com.chen.campus.entity.Dormitory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2023-11-19
 */
@Mapper
public interface DormitoryDao extends BaseMapper<Dormitory> {


    @Update("UPDATE campus_dormitory SET electricity_cost = electricity_cost + #{money} WHERE id = #{id}")
    public Integer chargeFee(Long id, double money);

    @Update("UPDATE campus_stu SET dormitory_id = #{dormitoryId} WHERE id = #{stuId}")
    public Integer bingStuToDormitory(Long stuId, Long dormitoryId);



}
