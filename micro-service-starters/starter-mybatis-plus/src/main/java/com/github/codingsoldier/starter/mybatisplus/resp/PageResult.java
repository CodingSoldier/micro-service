package com.github.codingsoldier.starter.mybatisplus.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Schema(name = "分页结果")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 8545996863226528111L;

    @Schema(description = "当前页")
    private long current = 1L;

    @Schema(description = "每页显示条数")
    private long size = 10L;

    @Schema(description = "总数")
    private long total = 0L;

    @Schema(description = "当前分页总页数")
    private long pages = 0L;

    @Schema(description = "数据列表")
    @SuppressWarnings("squid:S1948")
    private List<T> records = Collections.emptyList();

    public PageResult() {
    }

    public PageResult(long current, long size, long total, long pages, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.pages = pages;
        this.records = records;
    }

    public static <T> PageResult<T> create(IPage<?> page, List<T> records) {
        return new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), records);
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

}
