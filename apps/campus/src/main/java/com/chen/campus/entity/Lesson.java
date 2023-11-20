package com.chen.campus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chen.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("campus_lesson")
@ApiModel(value = "Lesson", description = "课程")
public class Lesson extends Entity<Long> {

    private String day;

    private String lessonName;

    private String address;

    private String idx;

}
