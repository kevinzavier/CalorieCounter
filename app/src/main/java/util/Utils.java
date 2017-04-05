package util;

import java.text.DecimalFormat;

/**
 * Created by kevinzavier on 3/30/17.
 */

public class Utils {
    public static String formatNumber(int value){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatted = formatter.format(value);

        return formatted;
    }
}
