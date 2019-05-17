Split(['#myprescription', '#takingDrugOperation'], {
    sizes: [65, 35],
    minSize: [800, 400]
});

function getMedicalRecord() {
    var prescriptionNum = $("#prescriptionNum_input").val();

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
                $("#date").val(data.date);
                $("#department").val(data.department);
                $("#diagnosisResult").val(data.diagnosisResult);
                $("#medicalOrder").html(data.medicalOrder);
                $("#drugCost").val(data.drugCost);
                $("#doctorName").val(data.doctorName);
                $("#prescription").html(data.prescription);

            } else {
                swal(data, "", "error")
            }
        }
    })
}

function saveTakingDrugInfo() {


    $.ajax({
        url: "/takingdrug/saveTakingDrugInfo",
        type: "post",
        data: {
            "prescriptionNum": $("#prescriptionNum_input").val()
        },
        success: function (data) {

            if (data == "SUCCESS") {
                swal({
                    title: "提交成功！",
                    type: "success",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true
                }, function () {
                    window.location.reload()
                })
            } else if (data == "FAIL") {
                swal("系统异常，请稍后重试！", "", "error")
            } else {
                swal(data, "", "error")
            }
        }

    })
}