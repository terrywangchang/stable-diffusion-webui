package com.keliu.data.common.constant;

/**
 * <h3>系统常量</h3>
 */
public final class Constants {

    private Constants() {}

    // ===== 状态 =====
    /** 启用 */
    public static final Integer STATUS_ENABLE = 1;
    /** 停用 */
    public static final Integer STATUS_DISABLE = 0;

    // ===== 逻辑删除 =====
    public static final Integer DELETED_NO = 0;
    public static final Integer DELETED_YES = 1;

    // ===== 用户类型 =====
    /** 管理员 */
    public static final String USER_TYPE_ADMIN = "admin";
    /** 分管领导 */
    public static final String USER_TYPE_LEADER = "leader";
    /** 从业人员 */
    public static final String USER_TYPE_STAFF = "staff";

    // ===== 经营主体类型 =====
    public static final String LEDGER_TYPE_SCENIC = "scenic";   // 景区
    public static final String LEDGER_TYPE_HOTEL = "hotel";     // 酒店
    public static final String LEDGER_TYPE_BNB = "bnb";         // 民宿
    public static final String LEDGER_TYPE_OTHER = "other";     // 其他

    // ===== 填报状态 =====
    public static final String FILL_STATUS_NORMAL = "normal";     // 正常填报
    public static final String FILL_STATUS_SUPPLEMENT = "supplement"; // 补报
    public static final String FILL_STATUS_MODIFIED = "modified"; // 修改

    // ===== 审核状态 =====
    public static final String AUDIT_PENDING = "pending";    // 待审核
    public static final String AUDIT_APPROVED = "approved";  // 审核通过
    public static final String AUDIT_REJECTED = "rejected";  // 审核驳回
}
