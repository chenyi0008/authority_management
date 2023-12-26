package com.chen.campus.dto;


import com.chen.campus.entity.Stu;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ApplyStuDTO", description = "申请查询DTO")
public class ApplyStuDTO {

    private Long id;

    private Long stuId;

    private String type;

    private String content;

    private String status;

    private Stu stu;
}
