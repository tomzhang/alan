package cn.com.sina.alan.common.model;

import cn.com.sina.alan.common.config.Const;

/**
 * Created by whf on 8/6/16.
 */
public abstract class PageableModel {
    /**
     * 页数, 从1开始
     */
    private int page;
    /**
     * 每页的条数
     */
    private int size;

    /**
     * 对应mysql中limit语句的第一个参数
     */
    private int skip;

    protected PageableModel() {
    }

    protected PageableModel(int page, int size) {
        if (page < 1) {
            this.page = 1;
        }

        if (size < 1) {
            this.size = Const.DEFAULT_PAGE_SIZE;
        }

        this.skip = this.size * (this.page - 1);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSkip() {
        return skip;
    }
}
