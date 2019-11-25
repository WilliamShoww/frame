package com.xcf.util.lang;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CollectionUtil {

    /**
     * 判断是否为空
     *
     * @return false 非空 true 为空
     */
    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断是否为空
     *
     * @return false 非空 true 为空
     */
    public static boolean isEmpty(Map collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断是否为空
     *
     * @return false 非空 true 为空
     */
    public static boolean isEmpty(List collection) {
        return null == collection || collection.isEmpty();
    }
}
