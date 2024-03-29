package com.github.codingsoldier.starter.web.util;


import com.github.codingsoldier.common.enums.ResultCodeEnum;
import com.github.codingsoldier.common.exception.HttpStatus4xxException;
import com.github.codingsoldier.common.util.StringUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 参数校验工具类
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class ValidationUtils {

    private static final Validator validator;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            validator = factory.getValidator();
        }
    }

    private ValidationUtils() {
        throw new IllegalStateException("不允许创建ValidatorUtils实例");
    }

    /**
     * 校验Bean，拼接提示信息
     *
     * @param object object to validate
     * @param groups the group or list of groups targeted for validation (defaults to Default)
     */
    public static void validateEntity(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                String message = constraint.getMessage();
                boolean isMatch = StringUtil.isEndWith(message, StringUtil.END_CHAR);
                // 没有结尾符号，添加句号
                message = isMatch ? message : String.format("%s。", message);
                sb.append(message);
            }
            throw new HttpStatus4xxException(ResultCodeEnum.PRECONDITION_FAILED.getCode(), sb.toString());
        }
    }

    /**
     * 校验Bean，拼接提示信息
     *
     * @param object object to validate
     * @param excludeFieldList 忽略字段
     * @param groups the group or list of groups targeted for validation (defaults to Default)
     */
    public static void validateEntity(Object object, final List<String> excludeFieldList, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                // 排除属性
                if (CollectionUtils.isNotEmpty(excludeFieldList)
                        && excludeFieldList.contains(constraint.getPropertyPath().toString())) {
                    continue;
                }
                String message = constraint.getMessage();
                boolean isMatch = StringUtil.isEndWith(message, StringUtil.END_CHAR);
                // 没有结尾符号，添加句号
                message = isMatch ? message : String.format("%s。", message);
                sb.append(message);
            }
            //有message抛出异常
            if (!sb.isEmpty()) {
                throw new HttpStatus4xxException(ResultCodeEnum.PRECONDITION_FAILED.getCode(), sb.toString());
            }
        }
    }

}