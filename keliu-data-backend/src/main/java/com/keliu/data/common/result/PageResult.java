package com.keliu.data.common.result;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * <h3>分页结果封装</h3>
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 当前页码 */
    private Long current;

    /** 每页条数 */
    private Long size;

    /** 总条数 */
    private Long total;

    /** 总页数 */
    private Long pages;

    /** 数据列表 */
    private List<T> records;

    public PageResult() {}

    public PageResult(Long current, Long size, Long total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.pages = (total + size - 1) / size;
        this.records = records;
    }

    /** 转为统一 Result 返回 */
    public Result<PageResult<T>> toResult() {
        return Result.success(this);
    }
}
