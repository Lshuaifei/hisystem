$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#LoginInfor').bootstrapTable({
            url: '/user/getLoginfor',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            // sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            queryParamsType: "",
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: false,                //是否启用点击选中行
            // height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "loginIp",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                field: 'no',
                title: '序号',
                sortable: true,
                align: "center",
                // width: 40,
                formatter: function (value, row, index) {
                    //获取每页显示的数量
                    var pageSize = $('#LoginInfor').bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber = $('#LoginInfor').bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1
                }
            }, {
                field: 'loginIp',
                title: 'IP地址',
                align: 'center',
                valign: 'middle'
            }, {

                field: 'loginBroswer',
                title: '浏览器',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'loginAddress',
                title: '位置',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'createDatetime',
                title: '时间',
                align: 'center',
                valign: 'middle'
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.pageSize,   //页面大小
            pageNumber: params.pageNumber - 1 //页码
        };
        return temp;
    };
    return oTableInit;
};


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};


function changePassword() {
    var ChangePasswordReqVO = {
        oldPassword: $("#oldPassword").val(),
        newPassword: $("#newPassword").val(),
        okPassword: $("#okPassword").val()
    };

    $.ajax({
        async: false,
        url: "/user/changePassword",
        type: "post",
        contentType: 'application/json',

        data: JSON.stringify(ChangePasswordReqVO),

        success: function (data) {

            $('.modalxa').html(data);
            $('.modalx').fadeIn();
            setTimeout(function () {
                $('.modalx').fadeOut();
            }, 3000);

            if (data == "修改成功") {
                /*防止重复提交*/
                $("#changePassword").attr("disabled", true);

                setTimeout("$('#changePassword').removeAttr('disabled')", 6000);
            }

        }
    })
}
