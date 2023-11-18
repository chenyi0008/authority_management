package com.chen.authority.entity.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chen.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("campus_stu")
@ApiModel(value = "CampusStu", description = "智慧校园学生类")
public class CampusStu extends SuperEntity<Long> {

    /**
     * 学校
     */
    private String school;

    /**
     * 编号
     */
    private String number;

    /**
     * 身份证
     */
    private String idCardNumber;

}
