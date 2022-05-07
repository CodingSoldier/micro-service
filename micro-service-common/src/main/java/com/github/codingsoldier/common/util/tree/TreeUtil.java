package com.github.codingsoldier.common.util.tree;

import com.github.codingsoldier.common.util.CommonUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 树工具类
 *
 * @author chenpiqian
 * @date 2019-09-16
 */
public class TreeUtil {

    /**
     * 获取树结构集合
     *
     * @param parentId   最外层级的parentId
     * @param entityList
     * @return
     */
    public static <E extends TreeChildrenEntity<E>> List<E> treeWithDel(Object parentId,
                                                                        List<E> entityList) {
        List<E> treeList = new ArrayList<>();
        Iterator it = entityList.iterator();
        while (it.hasNext()) {
            E elem = (E) it.next();
            if (CommonUtil.objToStr(parentId)
                    .equals(CommonUtil.objToStr(elem.getParentId()))) {
                treeList.add(elem);
                /**
                 * 使用Iterator，以便在迭代时把entityList中已经添加到treeList的数据删除，减少迭代次数
                 */
                it.remove();
            }
        }
        for (E elem : treeList) {
            elem.setChildren(treeWithDel(elem.getId(), entityList));
        }
        return treeList;
    }

    /**
     * 获取树结构集合
     *
     * @param parentId   最外层级的parentId
     * @param entityList
     * @return
     */
    public static <E extends TreeChildrenEntity<E>> List<E> tree(Object parentId,
                                                                 List<E> entityList) {
        List<E> treeList = new ArrayList<>();
        Iterator it = entityList.iterator();
        while (it.hasNext()) {
            E elem = (E) it.next();
            if (CommonUtil.objToStr(parentId)
                    .equals(CommonUtil.objToStr(elem.getParentId()))) {
                treeList.add(elem);
            }
        }
        for (E elem : treeList) {
            elem.setChildren(tree(elem.getId(), entityList));
        }
        return treeList;
    }

}