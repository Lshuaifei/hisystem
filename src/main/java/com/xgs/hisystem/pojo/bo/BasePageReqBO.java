package com.xgs.hisystem.pojo.bo;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
public class BasePageReqBO {

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
