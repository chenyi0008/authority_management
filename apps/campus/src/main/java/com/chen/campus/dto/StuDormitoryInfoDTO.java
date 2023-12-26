package com.chen.campus.dto;

import com.chen.campus.entity.Dormitory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "StuDormitoryInfoDTO", description = "学生信息DTO")
public class StuDormitoryInfoDTO {

    private Long id;

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
    @ApiModelProperty(value = "编号")
    @NotEmpty(message = "学生编号不能为空")
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
     * 手机
     */
    @ApiModelProperty(value = "手机")
    @Length(max = 20, message = "手机长度不能超过20")
    private String mobile;

    private Dormitory dormitory;


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

    /**
     * 是否退宿
     */
    @ApiModelProperty(value = "余额")
    private Double balance;


}
