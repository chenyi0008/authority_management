package com.chen.campus.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ExamineAndApproveDTO", description = "审查DTO")
public class ExamineAndApproveDTO {

    @ApiModelProperty(value = "审核id")
    private Long applyId;

    @Pattern(regexp = "^(通过|拒绝)$", message = "审核状态只能传 “通过” 或 “拒绝” ")
    @ApiModelProperty(value = "状态")
    private String status;



}
