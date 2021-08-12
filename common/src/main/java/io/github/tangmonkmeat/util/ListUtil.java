package io.github.tangmonkmeat.util;

import java.util.List;

/**
 * Description:
 * 
 * @author zwl 
 * @date 2021/7/27 下午11:32 
 * @version 1.0
 */
public class ListUtil {

    public static <T> T getFirst(List<T> list){
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    public static <T> T getLast(List<T> list){
        return list != null && !list.isEmpty() ? list.get(list.size() - 1) : null;
    }

}
