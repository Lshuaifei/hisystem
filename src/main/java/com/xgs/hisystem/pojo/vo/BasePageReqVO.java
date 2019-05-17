package com.xgs.hisystem.pojo.vo;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
public class BasePageReqVO {

    private int pageSize;

    private int PageNumber;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(int pageNumber) {
        PageNumber = pageNumber;
    }
}
