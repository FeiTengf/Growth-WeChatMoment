package feiteng.test.wechatmoment.utils;

/**
 * Helper class for String
 */
public class StringUtl {
    public static final String NAME_COLOR = "#5E6182";

    public static String getColoredName(String text) {
        return "<font color='" + NAME_COLOR + "'>" + text + ":" + "</font>";
    }
}
