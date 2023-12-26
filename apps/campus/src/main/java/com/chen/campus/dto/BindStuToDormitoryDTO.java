package com.chen.campus.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "BindStuToDormitoryDTO", description = "学生绑定宿舍DTO")
public class BindStuToDormitoryDTO {

    @Min(value = 1L, message = "id无效")
    String stuNum;

    @Min(value = 1L, message = "id无效")
    long dormitoryId;

}
