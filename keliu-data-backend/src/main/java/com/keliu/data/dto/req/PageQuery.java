package com.keliu.data.dto.req;

import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * <h3>分页查询基础参数</h3>
 * <p>
 * 所有需要分页的请求 DTO 都可继承此类。
 * </p>
 */
@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 当前页码，默认 1 */
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum = 1;

    /** 每页条数，默认 10，最大 100 */
    @Min(value = 1, message = "每页条数最小为1")
    @Max(value = 100, message = "每页条数最大为100")
    private Integer pageSize = 10;

    /** 模糊搜索关键字 */
    private String keyword;

    /** 排序字段 */
    private String orderBy;

    /** 排序方向：asc / desc */
    private String orderDirection = "desc";

    /**
     * 便捷方法：获取 offset
     */
    public Long getOffset() {
        return (long) (pageNum - 1) * pageSize;
    }
}
