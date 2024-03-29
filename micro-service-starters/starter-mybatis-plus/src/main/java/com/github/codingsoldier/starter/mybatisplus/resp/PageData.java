package com.github.codingsoldier.starter.mybatisplus.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class PageData<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 8545996863226528111L;

    @Schema(description = "当前页", example = "1")
    private long current = 1L;

    @Schema(description = "每页显示条数", example = "10")
    private long size = 10L;

    @Schema(description = "总数", example = "10")
    private long total = 0L;

    @Schema(description = "当前分页总页数", example = "1")
    private long pages = 0L;

    @Schema(description = "数据列表")
    @SuppressWarnings("squid:S1948")
    private List<T> records = Collections.emptyList();

    public PageData() {
    }

    public PageData(long current, long size, long total, long pages, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.pages = pages;
        this.records = records;
    }

    public static <T> PageData<T> create(IPage<?> page, List<T> records) {
        return new PageData<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), records);
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

    @Override
    public String toString() {
        return "PageData{" +
                "current=" + current +
                ", size=" + size +
                ", total=" + total +
                ", pages=" + pages +
                ", records=" + records +
                '}';
    }
}
