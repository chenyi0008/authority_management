package com.chen.campus.dto;

import com.chen.campus.entity.Stu;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "DormitoryStuInfoDTO", description = "宿舍信息DTO")
public class DormitoryStuInfoDTO {

    private String roomNumber;

    private double electricityCost;

    private String address;

    private List<Stu> stuList;
}
