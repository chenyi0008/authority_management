package com.chen.campus.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ChargeFeeDTO", description = "充值电费DTO")
public class ChargeFeeDTO {

    /**
     * 宿舍id
     */
    @ApiModelProperty(value = "宿舍id")
    @Min(value = 1L, message = "宿舍id不能为0")
    private long id;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    @DecimalMin(value = "0.0", inclusive = true, message = "充值金额不能小于0.0")
    @DecimalMax(value = "1000.0", inclusive = true, message = "充值金额不能大于1000.0")
    private double money;


}
