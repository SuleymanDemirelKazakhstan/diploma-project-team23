package kz.sdu.project.sauapbackend.utils;

public class CardUtils {

    private CardUtils() {}

    public static String maskCardNumber(String cardNumber) {
        return "*" + cardNumber.substring(12);
    }
}
