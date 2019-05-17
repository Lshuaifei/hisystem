package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.takingdrug.MedicalRecordRspVO;
import com.xgs.hisystem.service.ITakingDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xgs
 * @date 2019-5-15
 * @description:
 */
@RestController
@RequestMapping(value = "/takingdrug")
public class TakingDrugController {

    @Autowired
    private ITakingDrugService iTakingDrugService;

    @PostMapping(value = "/getMedicalRecord")
    public MedicalRecordRspVO getMedicalRecord(@RequestParam String prescriptionNum) throws Exception {

        return iTakingDrugService.getMedicalRecord(prescriptionNum);
    }

    @PostMapping(value = "/saveTakingDrugInfo")
    public String saveTakingDrugInfo(@RequestParam String prescriptionNum) {

        BaseResponse baseResponse = iTakingDrugService.saveTakingDrugInfo(prescriptionNum);
        return baseResponse.getMessage();
    }
}
