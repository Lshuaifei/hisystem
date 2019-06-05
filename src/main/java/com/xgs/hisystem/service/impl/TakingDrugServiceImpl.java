package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.takingdrug.MedicalRecordRspVO;
import com.xgs.hisystem.repository.IMedicalExaminationRepository;
import com.xgs.hisystem.repository.IMedicalRecordRepository;
import com.xgs.hisystem.service.ITakingDrugService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author xgs
 * @date 2019-5-15
 * @description:
 */
@Service
public class TakingDrugServiceImpl implements ITakingDrugService {

    @Autowired
    private IMedicalRecordRepository iMedicalRecordRepository;
    @Autowired
    private IMedicalExaminationRepository iMedicalExaminationRepository;

    @Override
    public MedicalRecordRspVO getMedicalRecord(String prescriptionNum) throws Exception {

        MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByPrescriptionNum(prescriptionNum);
        MedicalRecordRspVO recordRspVO = new MedicalRecordRspVO();

        if (StringUtils.isEmpty(medicalRecord)) {
            recordRspVO.setMessage("该处方号未查询到任何信息！");
            return recordRspVO;
        }
        RegisterEntity register = medicalRecord.getRegister();
        PatientEntity patient = medicalRecord.getRegister().getPatient();

        recordRspVO.setAge(DateUtil.getAge(patient.getBirthday()));
        recordRspVO.setDate(DateUtil.DateTimeToDate(medicalRecord.getCreateDatetime()));
        recordRspVO.setDiagnosisResult(medicalRecord.getDiagnosisResult());
        recordRspVO.setDrugCost(medicalRecord.getDrugCost());
        recordRspVO.setMedicalOrder(medicalRecord.getMedicalOrder());
        recordRspVO.setName(patient.getName());
        recordRspVO.setNationality(patient.getNationality());
        recordRspVO.setPrescription(medicalRecord.getPrescription());
        recordRspVO.setSex(patient.getSex());

        MedicalExaminationEntity medicalExamination = iMedicalExaminationRepository.findByPrescriptionNum(medicalRecord.getPrescriptionNum());
        if (!StringUtils.isEmpty(medicalExamination)) {
            recordRspVO.setExaminationCost(medicalExamination.getExaminationCost());
        }
        recordRspVO.setDoctorName(register.getDoctor());
        recordRspVO.setDepartment(register.getDepartment());

        return recordRspVO;
    }

    @Override
    public BaseResponse<?> saveTakingDrugInfo(String prescriptionNum) {


        if (StringUtils.isEmpty(prescriptionNum)) {

            return BaseResponse.errormsg("请填写处方号！");
        }

        MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByPrescriptionNum(prescriptionNum);

        MedicalRecordRspVO recordRspVO = new MedicalRecordRspVO();

        if (StringUtils.isEmpty(medicalRecord)) {

            return BaseResponse.errormsg("该处方号未查询到任何信息！");
        }
        if (StringUtils.isEmpty(medicalRecord.getTollDateTime())) {
            return BaseResponse.errormsg("该处方未划价！");
        }
        String tollDateTime = DateUtil.DateTimeToDate(medicalRecord.getTollDateTime());
        String nowDate = DateUtil.getCurrentDateSimpleToString();

        if (!tollDateTime.equals(nowDate)) {
            return BaseResponse.errormsg("该处方划价已逾期！");
        }

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        if (StringUtils.isEmpty(user)) {
            return null;
        }

        int takingDrugFrequency = medicalRecord.getTakingDrugFrequency();

        medicalRecord.setTakingDrugFrequency(takingDrugFrequency + 1);
        medicalRecord.setTakingDrugDateTime(DateUtil.getCurrentDateToString());
        medicalRecord.setTakingDrugOperator(user.getId());

        try {
            iMedicalRecordRepository.saveAndFlush(medicalRecord);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(Contants.user.FAIL);
        }

    }
}
