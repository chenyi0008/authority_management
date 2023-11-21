package com.chen.campus.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CampusCardRechargeDTO", description = "校园卡充值DTO")
public class CampusCardRechargeDTO {

    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    @Min(value = 1L, message = "学生id不能为0")
    private Long userId;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    @DecimalMin(value = "0.0", message = "充值金额不能小于0.0")
    @DecimalMax(value = "1000.0", message = "充值金额不能大于1000.0")
    private double money;
}
