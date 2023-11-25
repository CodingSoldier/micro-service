package com.github.codingsoldier.common.util.tree;

import com.github.codingsoldier.common.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 树工具类
 *
 * @author chenpiqian
 * @since 2022-02-09
 */
public class TreeUtil {

    private TreeUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    /**
     * 获取树结构集合，会删除entityList的元素
     *
     * @param parentId   最外层级的parentId
     * @param list list
     * @return List
     */
    public static <E extends Tree<E>> List<E> treeWithDel(Object parentId, List<E> list) {
        List<E> treeList = new ArrayList<>();
        Iterator<E> it = list.iterator();
        while (it.hasNext()) {
            E elem = it.next();
            if (CommonUtil.objToStr(parentId)
                    .equals(CommonUtil.objToStr(elem.getParentId()))) {
                treeList.add(elem);
                // 使用Iterator，以便在迭代时把entityList中已经添加到treeList的数据删除，减少迭代次数
                it.remove();
            }
        }
        for (E elem : treeList) {
            elem.setChildren(treeWithDel(elem.getId(), list));
        }
        return treeList;
    }

    /**
     * 获取树结构集合
     *
     * @param parentId   最外层级的parentId
     * @param list list
     * @return List
     */
    public static <E extends Tree<E>> List<E> tree(Object parentId, List<E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<E> treeList = new ArrayList<>();
        for (E elem : list) {
            if (CommonUtil.objToStr(parentId).equals(CommonUtil.objToStr(elem.getParentId()))) {
                treeList.add(elem);
            }
        }
        for (E elem : treeList) {
            elem.setChildren(tree(elem.getId(), list));
        }
        return treeList;
    }

}