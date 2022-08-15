package com.github.codingsoldier.common.util.tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenpq05
 * @since 2022/8/15 17:37
 */
class TreeUtilTest {

    @Test
    void treeWithDel() {
        TreeBean tb1 = new TreeBean();
        tb1.setId(1L);
        tb1.setParentId(0L);
        TreeBean tb2 = new TreeBean();
        tb2.setId(10L);
        tb2.setParentId(1L);
        List<TreeBean> list = new ArrayList<>();
        list.add(tb1);
        list.add(tb2);

        List<TreeBean> tree1 = TreeUtil.tree(0L, list);
        assertEquals(10L, tree1.get(0).getChildren().get(0).getId());

        List<TreeBean> tree2 = TreeUtil.treeWithDel(0L, list);
        assertEquals(10L, tree2.get(0).getChildren().get(0).getId());
    }

    @Test
    void tree() {
    }
}