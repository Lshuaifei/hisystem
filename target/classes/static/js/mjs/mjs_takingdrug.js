$(window).preloader();
/*Split是一个轻量级的可以动态调整的视图或面板分栏Layout布局工具。
没有任何外部依赖，只需要一两个有父元素的普通元素。可将页面水平或垂直分割，你不需要自己在面板之间手动插入分割条。
视图支持复杂的比较进行分割，通过拖拉分割栏增大显示。*/
Split(['#myprescription', '#takingDrugOperation'], {
    sizes: [65, 35],
    minSize: [800, 400]
});

function getMedicalRecord() {
    var prescriptionNum = $("#prescriptionNum_input").val();

    if (prescriptionNum == null || prescriptionNum === '') {
        swal("请填写处方号！", "", "error");
        return false;
    }

    $.ajax({
        url: "/takingdrug/getMedicalRecord",
        type: "post",
        data: {
            "prescriptionNum": prescriptionNum
        },
        success: function (data) {
            if (data.message == null) {
                $("#name").val(data.name);
                $("#sex").val(data.sex);
                $("#nationality").val(data.nationality);
                $("#age").val(data.age);
                $("#prescriptionNum").val(prescriptionNum);
                $("#createDate").val(data.createDate);
                $("#department").val(data.department);
                $("#diagnosisResult").val(data.diagnosisResult);
                $("#medicalOrder").html(data.medicalOrder);
                $("#drugCost").val(data.drugCost);
                $("#doctorName").val(data.doctorName);
                $("#prescription").html(data.prescription);
                $("#examinationCost").val(data.examinationCost);
                $("#nowDate").html(data.nowDate);
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

function saveTakingDrugInfo() {

    var prescriptionNum = $("#prescriptionNum_input").val();

    if (prescriptionNum == null || prescriptionNum === '') {
        swal("请填写处方号！", "", "error");
        return false;
    }

    $.ajax({
        url: "/takingdrug/saveTakingDrugInfo",
        type: "post",
        data: {
            "prescriptionNum": prescriptionNum
        },
        success: function (data) {

            if (data !== null && data.status === 1) {

                swal({
                    title: "提交成功！",
                    type: "success",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true
                }, function () {
                    window.location.reload()
                })
            } else {
                swal(data.message, "", "error")
            }
        }

    })
}