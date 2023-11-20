package com.chen.campus.controller;


import com.chen.base.R;
import com.chen.campus.entity.Lesson;
import com.chen.campus.service.ILessonService;
import com.chen.campus.utils.DataStructMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chen
 * @since 2023-11-20
 */
@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    ILessonService lessonService;

    @GetMapping
    public R getAllLesson(){
        List<Lesson> list = lessonService.list();
//        DataStructMapUtils.transformLessonData()
        List<Map<String, Object>> maps = DataStructMapUtils.convertToDesiredStructure(list);
        return R.success(maps);
    }

}

