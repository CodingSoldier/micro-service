package com.github.codingsoldier.common.util.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 树数据实体类接口
 *
 * @author chenpiqian
 * @date 2019-09-16
 */
public interface TreeEntity<E> {

    /**
     * 获取ID
     *
     * @return
     */
    Serializable getId();

    /**
     * 获取父级ID
     *
     * @return
     */
    Serializable getParentId();

    /**
     * 赋值子类
     *
     * @param children
     * @return
     */
    void setChildren(List<E> children);

}
