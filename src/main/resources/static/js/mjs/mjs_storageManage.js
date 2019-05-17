function addNewDrug() {

    var DrugReqVO = {

        name: $("#name").val(),
        drugType: $("#drug_type").val(),
        specification: $("#specification").val(),
        unit: $("#unit").val(),
        limitStatus: $("#limitStatus").val(),
        efficacyClassification: $("#efficacyClassification").val(),
        wholesalePrice: $("#wholesalePrice").val(),
        price: $("#price").val(),
        manufacturer: $("#manufacturer").val()
    };
    $.ajax({
        url: "/drugstore/addNewDrug",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(DrugReqVO),
        success: function (data) {

            if (data == "SUCCESS") {
                swal("添加成功！", "", "success")
            } else {
                swal(data, "", "error")
            }
        }
    })

}