$(window).preloader();

function getCardIdInfor() {
    $.ajax({
        url: "/medicalExamination/getCardIdInfor",
        type: "post",
        success: function (data) {
            if (null == data.message) {
                $("#cardId").val(data.cardId);
                $("#name").val(data.name);
                $("#sex").val(data.sex);
                $("#nationality").val(data.nationality);
                $("#age").val(data.age)
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

function savemedicalExaminationInfo() {

    var medicalExaminationInfoReqVO = {
        cardId: $("#cardId").val(),
        bodyTemperature: $("#bodyTemperature").val(),
        pulse: $("#pulse").val(),
        heartRate: $("#heartRate").val(),
        bloodPressure: $("#bloodPressure").val(),
        examinationCost: $("#examinationCost").val()
    };

    $.ajax({
        url: "/medicalExamination/saveMedicalExaminationInfo",
        type: "post",
        data: JSON.stringify(medicalExaminationInfoReqVO),
        contentType: "application/json",
        success: function (data) {
            if (data == "SUCCESS") {
                swal({
                    title: "保存成功！",
                    type: "success",
                }, function () {
                    setTimeout(function () {
                        window.location.reload()
                    }, 500)
                })


            } else if (data == "FAIL") {
                swal("系统异常，请稍后重试！", "", "error")
            } else {
                swal(data, "", "error")
            }
        }
    })

}