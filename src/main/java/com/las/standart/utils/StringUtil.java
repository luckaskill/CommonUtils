package com.las.standart.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static Integer extractNumber(String str) {
        Pattern compile = Pattern.compile("[0-9]+");
        Matcher matcher = compile.matcher(str);
        if (matcher.find()) {
            String group = matcher.group();
            int i = Integer.parseInt(group);
            return i;
        } else {
            return null;
        }
    }

    public static List<Integer> extractNumbers(String str) {
        Pattern compile = Pattern.compile("[0-9]+");
        Matcher matcher = compile.matcher(str);

        List<Integer> result = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            int num = Integer.parseInt(group);
            result.add(num);
        }
        return result;
    }



}
