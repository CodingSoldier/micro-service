package com.github.codingsoldier.common.token;

/**
 * token接口
 * 1、com.github.codingsoldier.starter.mybatisplus.CustomMetaObjectHandler 使用 getUserId() 填充
 * CustomMetaObjectHandler#CREATE_BY、CustomMetaObjectHandler#UPDATE_BY
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public interface TokenInterface<T> {

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    T getUserId();

}
