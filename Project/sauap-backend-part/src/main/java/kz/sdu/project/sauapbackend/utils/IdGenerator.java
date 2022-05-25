package kz.sdu.project.sauapbackend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class IdGenerator {

    private IdGenerator() {}

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHH");

    public static String generateUniqueId() {
        return DATE_FORMAT.format(new Date())
                + UUID.randomUUID().toString().replaceAll("-", "");
    }

}
