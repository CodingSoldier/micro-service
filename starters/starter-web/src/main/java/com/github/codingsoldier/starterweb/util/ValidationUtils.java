package com.github.codingsoldier.starterweb.util;


import com.github.codingsoldier.common.enums.RespCodeEnum;
import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;


/**
 * @author chenpiqian
 * @date: 2019-03-04
 */
public class ValidationUtils {

    private ValidationUtils(){
        throw new IllegalStateException("不允许创建ValidatorUtils实例");
    }

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();;


    /**
     * 校验Bean，拼接提示信息
     *
     * @param object
     * @param groups
     */
    public static void validateEntity(Object object, Class<?>... groups){
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                String message = constraint.getMessage();
                boolean isMatch = StringUtils.isEndWith(message, com.github.codingsoldier.common.util.StringUtils.END_CHAR);
                // 没有结尾符号，添加句号
                message = isMatch ? message : String.format("%s。", message);
                sb.append(message);
            }
            throw new AppException(RespCodeEnum.PRECONDITION_FAILED.getCode(), sb.toString());
        }
    }

    /**
     * 校验Bean，拼接提示信息
     *
     * @param object
     * @param excludeFieldList 忽略字段
     * @param groups
     */
    public static void validateEntity(Object object, final List<String> excludeFieldList, Class<?>... groups){
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                // 排除属性
                if (excludeFieldList != null && excludeFieldList.size() > 0
                        && excludeFieldList.contains(constraint.getPropertyPath().toString())){
                   continue;
                }
                String message = constraint.getMessage();
                boolean isMatch = StringUtils.isEndWith(message, com.github.codingsoldier.common.util.StringUtils.END_CHAR);
                // 没有结尾符号，添加句号
                message = isMatch ? message : String.format("%s。", message);
                sb.append(message);
            }
            //有message抛出异常
            if (sb.length() != 0){
                throw new AppException(RespCodeEnum.PRECONDITION_FAILED.getCode(), sb.toString());
            }
        }
    }

    public static void main(String[] args) {

    }

}