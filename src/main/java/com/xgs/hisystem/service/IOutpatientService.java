package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.outpatient.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-6
 * @description:
 */
public interface IOutpatientService {

    PatientInforRspVO getCardIdInfor() throws Exception;

    BaseResponse<?> changePatientInfor(OtherPatientInforReqVO reqVO);

    List<OutpatientQueueNormalRspVO> getAllPatientNormal();

    List<OutpatientQueueLaterRspVO> getAllPatientLater();

    BaseResponse<?> ProcessLaterMedicalRecord(MedicalRecordReqVO reqVO);

    PatientInforRspVO restorePatientInfor(String registerId) throws Exception;

    List<String> getAllDrug();

    DrugRspVO getDrugInfor(String drug);

    BaseResponse<?> addMedicalRecord(MedicalRecordReqVO reqVO);

}
