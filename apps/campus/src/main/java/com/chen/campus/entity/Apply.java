package com.chen.campus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.chen.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chen
 * @since 2023-11-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("campus_apply")
@ApiModel(value = "Apply", description = "申请")
public class Apply extends Entity<Long> {

    private Long stuId;


    private String type;


    private String content;


    private String status;


}
