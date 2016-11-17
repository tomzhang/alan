package cn.com.sina.alan.autoconfig.utils;

import cn.com.sina.alan.autoconfig.annotation.AlanDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 17/11/2016.
 */
public class ObjectUtils {
    private static final Logger log = LoggerFactory.getLogger(ObjectUtils.class);

    private ObjectUtils() {}

    public static Map<String, Object> convertToMap(Object obj) {
        Map<String, Object> map = new HashMap();

        doParseParameterMap("", obj, map);

        return map;
    }

    private static void doParseParameterMap(final String prefix, Object obj, Map<String, Object> parameterMap) {

        // 遍历POJO的Fields
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);

            String fieldName = field.getName();
            Object fieldValue = ReflectionUtils.getField(field, obj);

            if (null == fieldValue) {
                return;
            }

            if ( !isBasicObjectTypes(fieldValue)
                    && !isDateType(fieldValue)
                    && !isCollectionTypes(fieldValue)) {

                doParseParameterMap(combinePrefix(prefix, fieldName), fieldValue, parameterMap);
            } else {

                String paramValue = null;

                if (isBasicObjectTypes(fieldValue)) {
                    // 是基本数据类型
                    paramValue = fieldValue.toString();

                } else if (isDateType(fieldValue)) {
                    // 是日期
                    paramValue = processDate( (Date) fieldValue, field );

                } else if (isCollectionTypes(fieldValue)) {
                    // 是集合类
                    processCollection(combinePrefix(prefix, fieldName), fieldValue, parameterMap);

                    return;
                } else {
                    paramValue = fieldValue.toString();
                }

                parameterMap.put(combinePrefix(prefix, fieldName), paramValue);
            }

        });

    }

    private static void processCollection(String prefix, Object obj, Map<String, Object> parameterMap) {
        if (obj instanceof Iterable) {
            // 可迭代
            processIterable(prefix, obj, parameterMap);

        } else if (obj instanceof Map) {
            // 是Map类型
            processMap(prefix, obj, parameterMap);
        }
    }

    protected static void processIterable(String prefix, Object obj, Map<String, Object> parameterMap) {
        Iterator it = ((Iterable) obj).iterator();

        int ix = 0;
        while (it.hasNext()) {
            String key = String.format("%s[%d]", prefix, ix);
            parameterMap.put(key, it.next());
            ++ix;
        }
    }

    protected static void processMap(String prefix, Object obj, Map<String, Object> parameterMap) {
        Map map = (Map) obj;

        Set<Map.Entry> entrySet = map.entrySet();
        for (Map.Entry entry : entrySet) {
            String key = String.format("%s.%s", prefix, entry.getKey());
            parameterMap.put(key, obj);
        }
    }


    /**
     * 是否为可遍历的集合类型
     * @param obj
     * @return
     */
    protected static boolean isCollectionTypes(Object obj) {
        return obj instanceof Iterable
                || obj instanceof Map;
    }

    /**
     * 判断对象类型是否为基本类型、数字类型封装类或String类型
     * @param obj
     * @return
     */
    private static boolean isBasicObjectTypes(Object obj) {
        return obj.getClass().isPrimitive()
                || obj instanceof Number
                || obj instanceof String;
    }

    /**
     * 是否为日期类型
     * @param obj
     * @return
     */
    private static boolean isDateType(Object obj) {
        return obj instanceof Date;
    }

    /**
     * 将日期转换为字符串
     * @param date
     * @param field
     * @return
     */
    protected static String processDate(Date date, Field field) {
        // 判断是否带有@AlanDateFormat注解
        AlanDateFormat alanDateFormat = field.getAnnotation(AlanDateFormat.class);

        String dateString = null;
        if (null != alanDateFormat) {
            // 标有注解
            // 注解优先
            String pattern = alanDateFormat.pattern();
            log.trace("@AlanDateFormat注解优先");

            dateString = date2String(date, pattern);
            log.debug("日期{}使用{}格式的编码结果为{}", date, pattern, dateString);

        } else {
            // 没有注解
            // 使用properties中配置的参数
            //String pattern = alanDateProperties.getPattern();
            String pattern = "yyyy";
            // todo
            log.trace("使用properties指定的{}格式编码Date: {}", pattern, date);

            dateString = date2String(date, pattern);
            log.debug("日期{}编码结果为{}", date, dateString);

        }


        return dateString == null ? date.toString() : dateString;
    }

    private static String date2String(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    private static String combinePrefix(String prefix, String newPrefix) {
        if (StringUtils.isEmpty(prefix)) {
            return newPrefix;
        }

        return prefix + "." + newPrefix;
    }
}
