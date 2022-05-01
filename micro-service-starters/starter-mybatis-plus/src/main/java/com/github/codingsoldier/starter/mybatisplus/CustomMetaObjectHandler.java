package com.github.codingsoldier.starter.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.codingsoldier.common.token.TokenInterface;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;

/**
 * 自动填充
 * https://baomidou.com/pages/4c6bcf/
 */
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Lazy
    @Autowired
    private TokenInterface tokenInterface;

    private static final String CREATE_TIME = "createTime";
    private static final String CREATE_ID = "createId";
    private static final String UPDATE_TIME = "updateTime";
    private static final String UPDATE_ID = "updateId";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (isFieldValueNull(metaObject, CREATE_TIME)) {
            // 填充创建时间
            this.strictInsertFill(metaObject, CREATE_TIME, () -> LocalDateTime.now(), LocalDateTime.class);
        }
        if (tokenInterface != null && isFieldValueNull(metaObject, CREATE_ID)) {
            // 填充创建者id
            this.fillStrategy(metaObject, CREATE_ID, tokenInterface.getUserId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (isFieldValueNull(metaObject, UPDATE_TIME)) {
            // 填充更新时间
            this.strictInsertFill(metaObject, UPDATE_TIME, () -> LocalDateTime.now(), LocalDateTime.class);
        }
        if (tokenInterface != null && isFieldValueNull(metaObject, UPDATE_ID)) {
            // 填充更新者id
            this.fillStrategy(metaObject, UPDATE_ID, tokenInterface.getUserId());
        }
    }

    private boolean isFieldValueNull(MetaObject metaObject, String field){
        return metaObject.hasSetter(field)  && this.getFieldValByName(field, metaObject) == null;
    }

}