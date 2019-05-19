package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.toll.SaveTollInfoReqVO;
import com.xgs.hisystem.pojo.vo.toll.TollMedicalRecordRspVO;
import com.xgs.hisystem.pojo.vo.toll.TollRspVO;
import com.xgs.hisystem.pojo.vo.toll.cardRspVO;
import com.xgs.hisystem.repository.IMedicalExaminationRepository;
import com.xgs.hisystem.repository.IMedicalRecordRepository;
import com.xgs.hisystem.repository.IPatientRepository;
import com.xgs.hisystem.repository.IRegisterRepository;
import com.xgs.hisystem.service.ITollService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.xgs.hisystem.util.card.Card.defaultGetCardId;


/**
 * @author xgs
 * @date 2019-5-14
 * @description:
 */
@Service
public class TollServiceImpl implements ITollService {

    @Autowired
    private IPatientRepository iPatientRepository;
    @Autowired
    private IRegisterRepository iRegisterRepository;
    @Autowired
    private IMedicalRecordRepository iMedicalRecordRepository;
    @Autowired
    private IMedicalExaminationRepository iMedicalExaminationRepository;

    private static final Logger logger = LoggerFactory.getLogger(TollServiceImpl.class);

    @Override
    public cardRspVO getCardIdInfor() {

        String message = defaultGetCardId();

        cardRspVO cardRspVO = new cardRspVO();

        if (message.equals("fail")) {
            cardRspVO.setMessage("读卡失败！请刷新页面重试");
            return cardRspVO;
        } else if (message.equals("none")) {
            cardRspVO.setMessage("未识别到卡片！");
            return cardRspVO;
        } else {
            String cardId = message;

            PatientEntity patientInfor = iPatientRepository.findByCardId(cardId);

            if (StringUtils.isEmpty(patientInfor)) {
                cardRspVO.setMessage("未从该卡片识别到信息！");
                return cardRspVO;
            }

            cardRspVO.setCardId(cardId);

            return cardRspVO;
        }
    }

    @Override
    public List<TollRspVO> getAllMedicalRecord(String cardId, String tollStatus) {


        if (StringUtils.isEmpty(cardId) || StringUtils.isEmpty(tollStatus)) {
            return null;
        }
        String patientId = iPatientRepository.findByCardId(cardId).getId();

        int chargeStatus = Integer.parseInt(tollStatus);

        List<RegisterEntity> registerList = iRegisterRepository.findByPatientIdAndChargeStatusAndRegisterStatusAndTreatmentStatus(patientId, chargeStatus, 1, 1);

        if (registerList == null || registerList.size() <= 0) {
            return null;
        }
        List<TollRspVO> tollRspVOList = new ArrayList<>();

        for (RegisterEntity register : registerList) {
            TollRspVO tollRspVO = new TollRspVO();
            tollRspVO.setRegisterId(register.getId());
            tollRspVO.setRegisterType(register.getRegisterType());
            tollRspVO.setDepartment(register.getDepartment());
            tollRspVO.setDoctorName(register.getDoctor());

            MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByRegisterId(register.getId());

            if (StringUtils.isEmpty(medicalRecord)) {
                continue;
            }

            tollRspVO.setPrescriptionNum(medicalRecord.getPrescriptionNum());

            tollRspVOList.add(tollRspVO);

        }
        return tollRspVOList;
    }

    @Override
    public TollMedicalRecordRspVO getMedicalRecord(String cardId, String registerId) throws Exception {

        PatientEntity patient = iPatientRepository.findByCardId(cardId);

        MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByRegisterId(registerId);

        if (StringUtils.isEmpty(patient) || StringUtils.isEmpty(medicalRecord)) {
            return null;
        }
        TollMedicalRecordRspVO recordRspVO = new TollMedicalRecordRspVO();
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
        return recordRspVO;
    }


    /**
     * 划价收费完成，保存信息
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> saveTollInfo(SaveTollInfoReqVO reqVO) {

        RegisterEntity registerEntity = iRegisterRepository.findById(reqVO.getRegisterId()).get();

        if (StringUtils.isEmpty(registerEntity)) {
            return null;
        }
        registerEntity.setChargeStatus(1);

        MedicalRecordEntity medicalRecordEntity = iMedicalRecordRepository.findByPrescriptionNum(reqVO.getPrescriptionNum());

        if (StringUtils.isEmpty(medicalRecordEntity)) {
            return null;
        }
        int tollFrequency = medicalRecordEntity.getTollFrequency();

        medicalRecordEntity.setTollFrequency(tollFrequency + 1);
        medicalRecordEntity.setTollDateTime(DateUtil.getCurrentDateToString());

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        if (StringUtils.isEmpty(user)) {
            return null;
        }

        medicalRecordEntity.setTollOperator(user.getId());


        try {
            iRegisterRepository.saveAndFlush(registerEntity);
            iMedicalRecordRepository.saveAndFlush(medicalRecordEntity);

            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(Contants.user.FAIL);
        }
    }

}
