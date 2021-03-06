package cn.com.sina.alan.autoconfig.utils;

import cn.com.sina.alan.autoconfig.AlanDateProperties;
import cn.com.sina.alan.autoconfig.annotation.AlanDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 17/11/2016.
 */
@Component
public class ObjectUtils {
    private static final Logger log = LoggerFactory.getLogger(ObjectUtils.class);

    @Autowired
    private AlanDateProperties dateProperties;

    public ObjectUtils() {}

    /**
     * 仅单元测试用
     * @param properties
     */
    public ObjectUtils(AlanDateProperties properties) {
        this.dateProperties = properties;
    }


    /**
     * 将java bean转换成键值对;
     * 支持无限层嵌套;
     *
     * @param obj
     * @return
     */
    public Map<String, String> convertToMap(Object obj) {
        if (null == obj) {
            throw new IllegalArgumentException("参数obj不能为null");
        }

        Map<String, String> map = new HashMap();

        doParseParameterMap("", obj, map);

        return map;
    }

    /**
     * 将对象转换成HTTP表单键值对参数
     * @param obj
     * @return
     */
    public String convertToHttpFormParameter(Object obj) {
        Map<String, String> map = convertToMap(obj);

        return convertToHttpFormParameter(map);
    }

    /**
     * 将Map中的键值对转换成HTTP表单参数
     * @param paramMap
     * @return
     */
    public String convertToHttpFormParameter(Map<String, String> paramMap, String encoderCharset) throws UnsupportedEncodingException {
        StringBuilder formString = new StringBuilder(paramMap.size() * 8);
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String encodedKey = URLEncoder.encode(key, encoderCharset);


            formString.append(encodedKey);
            formString.append("=");

            String val = entry.getValue();
            String encodedVal = URLEncoder.encode(val, encoderCharset);
            formString.append(encodedVal);
            formString.append("&");
        }

        return formString.toString();

    }

    /**
     * 遍历对象的所有属性, 如果是String或者数字类型, 则直接转换成键值对;
     * 如果为POJO, 则进行递归, 将POJO的属性转换成键值对;
     * 如果POJO的属性中还有POJO, 继续递归
     *
     * @param prefix 上次递归时的变量名, 第一次调用时请传递空串""
     * @param obj POJO本身
     * @param parameterMap 保存属性键值对的Map对象
     */
    private void doParseParameterMap(final String prefix, Object obj, Map<String, String> parameterMap) {

        // 遍历POJO的Fields
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);

            // 得到属性名
            String fieldName = field.getName();
            // 忽略序列化属性
            if (fieldName.equals("serialVersionUID")) {
                return;
            }

            // 得到属性值
            Object fieldValue = ReflectionUtils.getField(field, obj);

            // 忽略为null的属性
            if (null == fieldValue) {
                return;
            }

            // 判断是否为自定义的POJO对象
            if (isPOJO(fieldValue)) {
                doParseParameterMap(combinePrefix(prefix, fieldName), fieldValue, parameterMap);

            } else {

                String paramValue = null;

                if (isBasicObjectTypes(fieldValue)) {
                    // 是基本数据类型
                    paramValue = fieldValue.toString();

                } else if (isDateType(fieldValue)) {
                    // 是日期, 进行格式化
                    paramValue = processDate( (Date) fieldValue, field );

                } else if (isCollectionTypes(fieldValue)) {
                    // 是集合类
                    processCollection(combinePrefix(prefix, fieldName), fieldValue, parameterMap);

                    return;

                } else if (fieldValue.getClass().isArray()) {
                    // 是数组
                    processArray(combinePrefix(prefix, fieldName), fieldValue, parameterMap);

                    return;

                } else {
                    // 一般情况下不会执行到这里, 除非Enum类型
                    paramValue = fieldValue.toString();
                }

                parameterMap.put(combinePrefix(prefix, fieldName), paramValue);
            }

        });

    }

    private void processCollection(String prefix, Object obj, Map<String, String> parameterMap) {
        if (obj instanceof Iterable) {
            // 可迭代
            processIterable(prefix, obj, parameterMap);

        } else if (obj instanceof Map) {
            // 是Map类型
            processMap(prefix, obj, parameterMap);
        }
    }

    /**
     * 遍历可迭代的集合, 转换成键值对扔到map中
     * @param prefix
     * @param obj
     * @param parameterMap
     */
    protected void processIterable(String prefix, Object obj, Map<String, String> parameterMap) {
        Iterator it = ((Iterable) obj).iterator();

        int ix = 0;
        while (it.hasNext()) {
            String key = String.format("%s[%d]", prefix, ix);
            parameterMap.put(key, it.next().toString());
            ++ix;
        }
    }

    /**
     * 遍历Map并转换成键值对
     * @param prefix
     * @param obj
     * @param parameterMap
     */
    protected void processMap(String prefix, Object obj, Map<String, String> parameterMap) {
        Map map = (Map) obj;

        Set<Map.Entry> entrySet = map.entrySet();
        for (Map.Entry entry : entrySet) {
            String key = String.format("%s['%s']", prefix, entry.getKey());
            parameterMap.put(key, entry.getValue().toString());
        }
    }

    /**
     * 使用反射的方式遍历数组
     * @param prefix
     * @param obj
     * @param parameterMap
     */
    protected void processArray(String prefix, Object obj, Map<String, String> parameterMap) {
        int size = Array.getLength(obj);

        for (int ix = 0 ; ix < size ; ++ix) {
            Object elem = Array.get(obj, ix);
            String key = String.format("%s[%d]", prefix, ix);

            parameterMap.put(key, elem.toString());
        }
    }

    /**
     * 判断该对象是否可以直接调用其toString()方法取字面值当HTTP请求参数
     * @param obj
     * @return
     */
    protected boolean isPOJO(Object obj) {
        return !isBasicObjectTypes(obj)
                && !isDateType(obj)
                && !isCollectionTypes(obj)
                && !obj.getClass().isArray()
                && !isEnumType(obj)
                && !isBooleanType(obj);
    }

    /**
     * 是否为枚举类型
     * @param obj
     * @return
     */
    protected boolean isEnumType(Object obj) {
        return obj instanceof Enum;
    }

    /**
     * 是否为bool类型
     * @param obj
     * @return
     */
    protected boolean isBooleanType(Object obj) {
        return obj instanceof Boolean;
    }

    /**
     * 是否为可遍历的集合类型
     * @param obj
     * @return
     */
    protected boolean isCollectionTypes(Object obj) {
        return obj instanceof Iterable
                || obj instanceof Map;
    }

    /**
     * 判断对象类型是否为基本类型、数字类型封装类或String类型
     * @param obj
     * @return
     */
    private boolean isBasicObjectTypes(Object obj) {
        return obj.getClass().isPrimitive()
                || obj instanceof Number
                || obj instanceof String;
    }

    /**
     * 是否为日期类型
     * @param obj
     * @return
     */
    private boolean isDateType(Object obj) {
        return obj instanceof Date;
    }

    /**
     * 将日期转换为字符串
     * @param date
     * @param field
     * @return
     */
    protected String processDate(Date date, Field field) {
        // 判断是否带有@AlanDateFormat注解
        AlanDateFormat alanDateFormat = field.getAnnotation(AlanDateFormat.class);

        String dateString = null;
        if (null != alanDateFormat) {
            // 标有注解
            // 注解优先
            String pattern = alanDateFormat.pattern();
            log.debug("@AlanDateFormat注解优先");

            dateString = date2String(date, pattern);
            log.trace("日期{}使用{}格式的编码结果为{}", date, pattern, dateString);

        } else {
            // 没有注解
            // 使用properties中配置的参数
            String pattern = dateProperties.getPattern();
            log.debug("使用properties指定的{}格式编码Date: {}", pattern, date);

            dateString = date2String(date, pattern);
            log.trace("日期{}编码结果为{}", date, dateString);

        }


        return dateString == null ? date.toString() : dateString;
    }

    private String date2String(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    private String combinePrefix(String prefix, String newPrefix) {
        if (StringUtils.isEmpty(prefix)) {
            return newPrefix;
        }

        int len = prefix.length() + newPrefix.length() + 1;
        return AlanStringUtils.concat(len, prefix, ".", newPrefix);
    }
}
