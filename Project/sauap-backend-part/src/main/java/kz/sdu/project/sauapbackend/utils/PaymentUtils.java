package kz.sdu.project.sauapbackend.utils;

import java.time.LocalDateTime;

public class PaymentUtils {

    private PaymentUtils() {}

    public static String getDateString(LocalDateTime today) {
        return String.format("%s %d %d %d:%d:%d", today.getMonth(), today.getDayOfMonth(), today.getYear(),
                today.getHour(), today.getMinute(), today.getSecond());
    }
}
