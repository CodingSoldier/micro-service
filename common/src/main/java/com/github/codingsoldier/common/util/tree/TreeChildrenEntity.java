package com.github.codingsoldier.common.util.tree;

import java.util.List;

/**
 * 数据实体类接口
 *
 * @author chenpiqian
 * @date 2019-09-16
 */
public interface TreeChildrenEntity<E> {

    /**
     * 获取ID
     *
     * @return
     */
    Long getId();

    /**
     * 获取父级ID
     *
     * @return
     */
    Long getParentId();

    /**
     * 赋值子类
     *
     * @param children
     * @return
     */
    void setChildren(List<E> children);

}
