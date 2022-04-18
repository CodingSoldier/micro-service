package com.github.codingsoldier.starterweb.util;

import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.enums.RespCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author chenpq05
 * @since 2022/3/14 14:01
 */
@Slf4j
public class CopyUtils {

  /**
   * 集合拷贝
   * @param sources 原集合
   * @param targetClazz 目标集合元素类型
   * @param <T>
   * @param <E>
   * @return 目标集合
   * @throws AppException
   */
  public static <T, E> List<T> listCopy(Collection<E> sources, Class<T> targetClazz) throws AppException {
    ArrayList<T> result = new ArrayList<>();
    for (E source : sources) {
      try {
        T t = targetClazz.newInstance();
        BeanUtils.copyProperties(source, t);
        result.add(t);
      } catch (InstantiationException | IllegalAccessException e){
        log.error("异常", e);
        throw new AppException(RespCodeEnum.ERROR.getCode(), "集合转换异常。");
      }
    }
    return result;
  }

}
