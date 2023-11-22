package com.chen.campus.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ApplyDTO", description = "申请DTO")
public class ApplyDTO {

    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    @Min(value = 1L, message = "id无效")
    private Long stuId;

    /**
     * 申请类型
     */
    @ApiModelProperty(value = "申请类型")
    @NotEmpty(message = "申请类型不能为空")
    @Length(max = 30, message = "申请类型长度不能超过30")
    private String type;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @NotEmpty(message = "内容不能为空")
    @Length(max = 255, message = "内容长度不能超过255")
    private String content;


}
