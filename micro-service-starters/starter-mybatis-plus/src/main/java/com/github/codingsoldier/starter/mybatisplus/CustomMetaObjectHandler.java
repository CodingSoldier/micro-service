package com.github.codingsoldier.starter.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.time.LocalDateTime;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 自动填充
 * <a href="https://baomidou.com/pages/4c6bcf/">...</a>
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class CustomMetaObjectHandler implements MetaObjectHandler {

  public static final String CREATE_TIME = "createdTime";
  public static final String UPDATE_TIME = "updatedTime";

  @Override
  public void insertFill(MetaObject metaObject) {
    if (isFieldValueNull(metaObject, CREATE_TIME)) {
      // 填充创建时间
      this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    if (isFieldValueNull(metaObject, UPDATE_TIME)) {
      // 填充更新时间
      this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
    }
  }

  private boolean isFieldValueNull(MetaObject metaObject, String field) {
    return metaObject.hasSetter(field) && this.getFieldValByName(field, metaObject) == null;
  }

}