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
@ApiModel(value = "DormitoryDTO", description = "学生宿舍DTO")
public class DormitoryDTO {

    @ApiModelProperty(value = "id")
    @Min(value = 1L, message = "id无效")
    private long id;

    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号")
    @NotEmpty(message = "房间号不能为空")
    @Length(max = 30, message = "房间号长度不能超过30")
    private String roomNumber;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    @NotEmpty(message = "地址不能为空")
    @Length(max = 30, message = "地址长度不能超过30")
    private String address;


}
