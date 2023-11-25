package com.github.codingsoldier.common.util.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 树数据实体类接口
 *
 * @author chenpiqian
 * @since 2022-02-09
 */
public interface Tree<E> {

    /**
     * 获取ID
     *
     * @return id
     */
    Serializable getId();

    /**
     * 获取父级ID
     *
     * @return parentId
     */
    Serializable getParentId();

    /**
     * 赋值子类
     *
     * @param children children
     */
    void setChildren(List<E> children);

}
