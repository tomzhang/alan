package cn.com.sina.alan.autoconfig.utils;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 18/11/2016.
 */
public class AlanStringUtils {
    private AlanStringUtils() {}

    /**
     * 字符串拼接;
     *
     * @param len 预计拼接后的字符串长度, 填写该参数以减少不必须的内存拷贝
     * @param strs
     * @return
     */
    public static String concat(int len, String... strs) {
        if (null == strs) {
            throw new IllegalArgumentException("参数strs不能为null");
        }


        StringBuilder sb = new StringBuilder(len > 0 ? len : 20);
        for (String str : strs) {
            sb.append(str);
        }

        return sb.toString();
    }
}
