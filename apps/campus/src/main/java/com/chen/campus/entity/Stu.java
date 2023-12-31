package com.chen.campus.entity;

import com.chen.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "Stu", description = "学生类")
public class Stu extends Entity<Long> {

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @NotEmpty(message = "姓名不能为空")
    @Length(max = 30, message = "姓名长度不能超过30")
    private String name;

    /**
     * 学校
     */
    @ApiModelProperty(value = "学校")
    @NotEmpty(message = "学校不能为空")
    @Length(max = 30, message = "学校名称长度不能超过30")
    private String school;

    /**
     * 编号
     */
    @ApiModelProperty(value = "学号")
    @NotEmpty(message = "学生学号不能为空")
    @Length(max = 10, min = 6,  message = "账号长度范围在6-10之间")
    private String number;

    /**
     * 身份证
     */
    @ApiModelProperty(value = "身份证")
    @NotEmpty(message = "身份证")
    @Length(min = 18, max = 18, message = "身份证号码长度必须为18位")
    private String idCardNumber;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    @Length(max = 64, message = "密码长度不能超过64")
    private String password;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @NotEmpty(message = "账号不能为空")
    @Length(max = 30, message = "账号长度不能超过30")
    private String account;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Length(max = 20, message = "手机长度不能超过20")
    private String mobile;

    /**
     * 宿舍id
     */
    @ApiModelProperty(value = "宿舍id")
    private Long dormitoryId;

    /**
     * 校园卡余额
     */
    @ApiModelProperty(value = "校园卡余额")
    private double balance;

    /**
     * 是否通过四级
     */
    @ApiModelProperty(value = "是否通过四级")
    private Boolean cet4;

    /**
     * 是否通过六级
     */
    @ApiModelProperty(value = "是否通过六级")
    private Boolean cet6;

    /**
     * 学分是否符合毕业要求
     */
    @ApiModelProperty(value = "学分是否符合毕业要求")
    private Boolean credit;

    /**
     * 是否退宿
     */
    @ApiModelProperty(value = "是否退宿")
    private Boolean checkOut;

}