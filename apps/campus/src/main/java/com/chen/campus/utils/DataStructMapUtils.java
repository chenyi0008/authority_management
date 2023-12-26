package com.chen.campus.utils;

import com.chen.campus.entity.Lesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStructMapUtils {

    public static List<Map<String, Object>> convertToDesiredStructure(List<Lesson> lessons) {
        Map<String, Map<String, Map<String, Object>>> convertedData = new HashMap<>();

        for (Lesson lesson : lessons) {
            String day = lesson.getDay();

            // Create a new lesson map
            Map<String, Object> lessonInfo = new HashMap<>();
            lessonInfo.put("name", lesson.getLessonName());
            lessonInfo.put("position", lesson.getAddress());

            // Create a new day map if it doesn't exist
            convertedData.computeIfAbsent(day, k -> new HashMap<>());

            // Add the lesson to the corresponding day and lesson position
            convertedData.get(day).put(lesson.getIdx(), lessonInfo);
        }

        // Convert the intermediate map to the final list structure
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Map<String, Map<String, Object>>> entry : convertedData.entrySet()) {

            Map<String, Object> dayMap = new HashMap<>();
            dayMap.put("day", entry.getKey());
//            dayMap.put("todayLessons", entry.getValue());
            Map<String, Map<String, Object>> map = entry.getValue();
            for (String s : map.keySet()) {
                dayMap.put(s, map.get(s));
            }

            result.add(dayMap);
        }

        return result;
    }



}
