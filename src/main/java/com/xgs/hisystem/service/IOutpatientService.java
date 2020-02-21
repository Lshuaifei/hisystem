package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.outpatient.*;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-6
 * @description:
 */
public interface IOutpatientService {

    PatientInforRspVO getCardIdInfor(GetCardIdInforReqVO reqVO) throws Exception;

    BaseResponse<?> changePatientInfor(OtherPatientInforReqVO reqVO);

    List<OutpatientQueueNormalRspVO> getAllPatientNormal();

    List<OutpatientQueueLaterRspVO> getAllPatientLater();

    BaseResponse<?> processLaterMedicalRecord(MedicalRecordReqVO reqVO);

    PatientInforRspVO restorePatientInfor(String registerId) throws Exception;

    List<String> getAllDrug();

    DrugRspVO getDrugInfor(String drug);

    BaseResponse<?> addMedicalRecord(MedicalRecordReqVO reqVO);

    medicalExaminationInfoRspVO getMedicalExamination(String prescriptionNum);

}
