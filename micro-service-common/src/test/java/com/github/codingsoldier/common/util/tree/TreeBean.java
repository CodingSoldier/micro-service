package com.github.codingsoldier.common.util.tree;

import java.util.List;

/**
 * @author chenpq05
 * @since 2022/8/15 17:37
 */
public class TreeBean implements TreeEntity<TreeBean>{

    private Long id;

    private Long parentId;

    private List<TreeBean> children;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<TreeBean> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<TreeBean> children) {
        this.children = children;
    }
}