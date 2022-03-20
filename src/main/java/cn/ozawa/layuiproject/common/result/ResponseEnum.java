package cn.ozawa.layuiproject.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/02/25
 * @description : cn.ozawa.layuiproject.common.result
 */
@Getter
@AllArgsConstructor
@ToString
public enum ResponseEnum {
    SUCCESS(0, "成功"),
    ERROR(400, "失败"),

    LOGIN_MOBILE_ERROR(201, "用户不存在"),
    LOGIN_PASSWORD_ERROR(202, "密码错误"),
    PASSWORD_NULL_ERROR(203, "密码不能为空"),
    CODE_NULL_ERROR(204, "验证码不能为空"),
    CODE_ERROR(205, "验证码错误"),
    TIME_OVERLAPPING_ERROR(206, "该二维码在此时间段有其他活动，请重新选择开始时间和结束时间");

    private final Integer code;
    private final String message;
}
