package com.example.samsungproject2.utils;

import java.util.HashMap;
import java.util.Map;

public class MonthUtils {
    public static HashMap<String, String> months;
    static {
        months = new HashMap<>();
        months.put("01", "Января");
        months.put("02", "Февраля");
        months.put("03", "Марта");
        months.put("04", "Апреля");
        months.put("05", "Мая");
        months.put("06", "Июня");
        months.put("07", "Июля");
        months.put("08", "Августа");
        months.put("09", "Сентября");
        months.put("10", "Октября");
        months.put("11", "Ноября");
        months.put("12", "Декабря");
    }
    public static HashMap<String, String> getMonths(){
        return months;
    }


}
