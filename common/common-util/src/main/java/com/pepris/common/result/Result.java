package com.pepris.common.result;

import lombok.Data;

@Data
public class Result<T> {
    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;

    public Result(){}

    // 返回数据
    /*
    * 基本语法：[修饰符] 返回值类型 方法名 ([参数列表]) {// 方法体}
    * TODO 这是一个基础构建放方法
    * protected代表修饰符，权限：同类，同包（路径），子类
    * static声明这是一个静态方法：静态方法和对象无关，只和类有关
    * <T>声明这是一个泛型方法
    * Result<T>代表返回值类型是Result
    * build是方法名
    * 参数是data
    * 作用：创建Result对象并设置data。
    * 设计意图：作为其他构建方法的基础。
     * */
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    /*
    * TODO 这是一个通用构建方法
    * */
    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    /*
     * TODO 这是一个枚举构建方法
     * */
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }
    /*
     * TODO 这是一个快捷方法
     *  返回成功
     * */
    public static<T> Result<T> ok(){
        return Result.ok(null);
    }

    /**
     * 操作成功
     * @param data  baseCategory1List
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }
    /*
     * TODO 这是一个快捷方法
     *  返回失败
     * */

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }


}
