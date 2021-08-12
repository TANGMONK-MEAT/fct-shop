package io.github.tangmonkmeat.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/23 下午3:52
 */
public class BeanListUtil {

    /**
     * BeanUtils.copyProperties()的List增强版
     * 对List的每个元素进行拷贝,生成target类型的List
     *
     * @param sourceList
     * @param targetClazz 要生成的target List<T>的Class对象
     * @param <T>
     * @return
     */
    public static <T, R> List<R> copyListProperties(List<T> sourceList, Class<R> targetClazz) {
        List<R> result = new ArrayList<>();
        sourceList.forEach(source -> {
            try {
                R target = targetClazz.newInstance();
                BeanUtils.copyProperties(source, target);
                result.add(target);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return result;
    }

}
