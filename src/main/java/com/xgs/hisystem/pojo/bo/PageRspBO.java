package com.xgs.hisystem.pojo.bo;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/2
 * @description:
 */
public class PageRspBO<T> {

    private int total;

    private List<T> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
