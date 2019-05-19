package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.ValidationResultBO;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.medicalExamination.medicalExaminationInfoReqVO;
import com.xgs.hisystem.pojo.vo.medicalExamination.patientInforRspVO;
import com.xgs.hisystem.service.IMedicalExaminationService;
import com.xgs.hisystem.util.ParamsValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xgs
 * @date 2019-5-18
 * @description:
 */
@RestController
@RequestMapping(value = "medicalExamination")
public class MedicalExaminationController {

    @Autowired
    private IMedicalExaminationService iMedicalExaminationService;

    @PostMapping(value = "/getCardIdInfor")
    public patientInforRspVO getCardIdInfor() throws Exception {

        patientInforRspVO patientInforRspVO = iMedicalExaminationService.getCardIdInfor();
        return patientInforRspVO;
    }

    @PostMapping(value = "/saveMedicalExaminationInfo")
    public String saveMedicalExaminationInfo(@RequestBody medicalExaminationInfoReqVO reqVO) {

        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }

        BaseResponse baseResponse = iMedicalExaminationService.saveMedicalExaminationInfo(reqVO);
        return baseResponse.getMessage();
    }
}
