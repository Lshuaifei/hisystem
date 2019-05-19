package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.medicalExamination.medicalExaminationInfoReqVO;
import com.xgs.hisystem.pojo.vo.medicalExamination.patientInforRspVO;

/**
 * @author xgs
 * @date 2019-5-18
 * @description:
 */
public interface IMedicalExaminationService {

    patientInforRspVO getCardIdInfor() throws Exception;

    BaseResponse<?> saveMedicalExaminationInfo(medicalExaminationInfoReqVO reqVO);
}
