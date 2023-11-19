package com.chen.campus.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.chen.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.text.DecimalFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("campus_dormitory")
@ApiModel(value = "Dormitory", description = "学生宿舍")
public class Dormitory extends Entity<Long> {

    private String roomNumber;

    private double electricityCost;

    private String address;
}
