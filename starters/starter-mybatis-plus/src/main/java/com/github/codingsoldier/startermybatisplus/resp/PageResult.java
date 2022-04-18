package com.github.codingsoldier.startermybatisplus.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@ApiModel(value = "分页结果")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 8545996863226528111L;

    @ApiModelProperty(value = "当前页")
    protected long current = 1;

    @ApiModelProperty(value = "每页显示条数")
    protected long size = 10;

    @ApiModelProperty(value = "总数")
    protected long total = 0;

    @ApiModelProperty(value = "当前分页总页数")
    protected long pages = 0;

    @ApiModelProperty(value = "数据列表")
    protected List<T> records = Collections.emptyList();

    public PageResult(long current, long size, long total, long pages, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.pages = pages;
        this.records = records;
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

    public static <T> PageResult<T> create(IPage page, List<T> records){
        return new PageResult(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), records);
    }

}
