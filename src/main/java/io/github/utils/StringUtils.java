package io.github.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 字符串工具类
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/31 10:59
 */
public class StringUtils {

    /**
     * toJSONString
     *
     * @param o
     * @return
     */
    public static String toJSONString(Object o) {
        return ToStringBuilder.reflectionToString(o, ToStringStyle.JSON_STYLE);
    }

    /**
     * toShortPrefixString
     *
     * @param o
     * @return
     */
    public static String toShortPrefixString(Object o) {
        return ToStringBuilder.reflectionToString(o, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
