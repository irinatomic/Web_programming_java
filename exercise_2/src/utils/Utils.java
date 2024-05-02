package utils;

import java.util.Optional;

public class Utils {

    public static <T extends AutoCloseable> void closeResource(T resource) {
        Optional.ofNullable(resource).ifPresent(r -> {
            try {
                r.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
