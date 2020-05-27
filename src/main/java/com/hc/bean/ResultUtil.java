package com.hc.bean;

public class ResultUtil {
    /**
     * 操作成功，只返回结果码和提示信息
     * 用于删除、修改、新增接口
     * @return
     */
    public static Result success() {
        return new Result(ResultCode.OK);
    }

    /**
     * 操作成功,只返回结果码和具体的数据，但不返回提示信息
     * 用于删除、修改、新增接口
     * @return
     */
    public static Result success(int code, String msg) {
        return new Result(code, msg);
    }

    /**
     * 操作成功,返回具体的数据、结果码和提示信息
     * 用于数据查询接口
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result<Object> result = new Result(ResultCode.OK);
        result.setData(data);
        return result;
    }

    /**
     * 操作成功,返回具体的数据、结果码和提示信息
     * 用于数据查询接口
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result success(Integer code, String msg, Object data) {
        return new Result<>(code,msg,data);
    }


    /**
     * 操作失败，只返回结果码和提示信息
     *
     * @param resultCode
     * @return
     */
    public static Result fail(ResultCode resultCode) {
        return new Result(resultCode);
    }

    /**
     * 操作失败,只返回指定的结果码和具体的数据，但不返回提示信息
     *
     * @return
     */
    public static Result fail(int code, String msg) {
        return new Result(code, msg);
    }

    /**
     * 操作失败,返回具体的数据、结果码和提示信息
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result fail(Integer code, String msg, Object data) {
        return new Result<>(code,msg,data);
    }
}
