package com.keliu.data.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h3>自定义状态码枚举</h3>
 * <p>
 * 规范业务异常状态码，便于前后端统一处理。
 * 状态码区间规划：
 *   200  - 成功
 *   400  - 参数校验失败
 *   401  - 未登录 / Token 失效
 *   403  - 无权限
 *   404  - 资源不存在
 *   500  - 系统内部错误
 *   1xxx - 业务异常
 * </p>
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),

    // ===== HTTP 标准码 =====
    BAD_REQUEST(400, "参数校验失败"),
    UNAUTHORIZED(401, "未登录或Token已过期"),
    FORBIDDEN(403, "无操作权限"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),

    // ===== 业务通用码 1xxx =====
    PARAM_ERROR(1001, "参数错误"),
    DATA_NOT_EXIST(1002, "数据不存在"),
    DATA_ALREADY_EXIST(1003, "数据已存在"),
    DATA_DELETED(1004, "数据已被删除"),

    // ===== 登录/鉴权 2xxx =====
    LOGIN_FAIL(2001, "用户名或密码错误"),
    ACCOUNT_DISABLED(2002, "账号已被禁用"),
    TOKEN_EXPIRED(2003, "登录已过期，请重新登录"),
    TOKEN_INVALID(2004, "无效的Token"),
    SMS_CODE_ERROR(2005, "短信验证码错误或已过期"),
    WECHAT_LOGIN_FAIL(2006, "微信登录失败"),

    // ===== 台账 3xxx =====
    LEDGER_NOT_FOUND(3001, "经营主体台账不存在"),
    LEDGER_ALREADY_EXIST(3002, "统一社会信用代码已存在"),

    // ===== 从业人员 4xxx =====
    STAFF_NOT_FOUND(4001, "从业人员不存在"),
    STAFF_FROZEN(4002, "账号已冻结"),
    STAFF_ID_CARD_EXIST(4003, "身份证号已被使用"),

    // ===== 客流填报 5xxx =====
    FILL_WINDOW_CLOSED(5001, "不在填报窗口期内"),
    FILL_ALREADY_SUBMITTED(5002, "今日已填报，请通过修改功能调整"),
    SUPPLEMENT_REQUIRE_AUDIT(5003, "超期补报需管理员审核"),

    // ===== 导出 6xxx =====
    EXPORT_PERMISSION_DENIED(6001, "无数据导出权限");

    private final Integer code;
    private final String msg;
}
