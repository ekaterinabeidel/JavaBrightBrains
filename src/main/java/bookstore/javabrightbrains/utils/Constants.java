package bookstore.javabrightbrains.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String ADMIN_BASE_URL = "/api/admin";
    public static final String USER_BASE_URL = "/api/user";
    public static final String PUBLIC_BASE_URL = "/api/public";
    public static List<String> getGroupByProfit() {
        List<String> groupByList = new ArrayList<>();
        groupByList.add("hour");
        groupByList.add("day");
        groupByList.add("week");
        groupByList.add("month");
        groupByList.add("year");

        return groupByList;
    }
}

