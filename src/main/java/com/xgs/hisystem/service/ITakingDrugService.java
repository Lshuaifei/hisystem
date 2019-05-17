package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.takingdrug.MedicalRecordRspVO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xgs
 * @date 2019-5-15
 * @description:
 */
public interface ITakingDrugService {

    MedicalRecordRspVO getMedicalRecord(@RequestParam String prescriptionNum) throws Exception;

    BaseResponse<?> saveTakingDrugInfo(String prescriptionNum);
}
