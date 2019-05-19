package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.medicalExamination.medicalExaminationInfoReqVO;
import com.xgs.hisystem.pojo.vo.medicalExamination.patientInforRspVO;
import com.xgs.hisystem.repository.IMedicalExaminationRepository;
import com.xgs.hisystem.repository.IMedicalRecordRepository;
import com.xgs.hisystem.repository.IOutpatientQueueRepository;
import com.xgs.hisystem.repository.IPatientRepository;
import com.xgs.hisystem.service.IMedicalExaminationService;
import com.xgs.hisystem.util.DateUtil;
import com.xgs.hisystem.util.card.Card;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author xgs
 * @date 2019-5-18
 * @description:
 */
@Service
public class MedicalExaminationServiceImpl implements IMedicalExaminationService {

    @Autowired
    private IPatientRepository iPatientRepository;

    @Autowired
    private IMedicalExaminationRepository iMedicalExaminationRepository;
    @Autowired
    private IOutpatientQueueRepository iOutpatientQueueRepository;
    @Autowired
    private IMedicalRecordRepository iMedicalRecordRepository;

    @Override
    public patientInforRspVO getCardIdInfor() throws Exception {

        String message = Card.defaultGetCardId();

        patientInforRspVO patientInforRspVO = new patientInforRspVO();

        if (message.equals("fail")) {
            patientInforRspVO.setMessage("读卡失败！请刷新页面重试");
            return patientInforRspVO;
        } else if (message.equals("none")) {
            patientInforRspVO.setMessage("未识别到卡片！");
            return patientInforRspVO;
        } else {
            String cardId = message;

            PatientEntity patientInfor = iPatientRepository.findByCardId(cardId);

            if (StringUtils.isEmpty(patientInfor)) {
                patientInforRspVO.setMessage("未从该卡片识别到信息！");
                return patientInforRspVO;
            }


            OutpatientQueueEntity outpatientQueueEntity = iOutpatientQueueRepository.findByPatientId(patientInfor.getId());
            if (StringUtils.isEmpty(outpatientQueueEntity)) {
                patientInforRspVO.setMessage("请先到挂号处挂号！");
                return patientInforRspVO;
            }

            String registerId = outpatientQueueEntity.getRegister().getId();

            MedicalRecordEntity medicalRecordEntity = iMedicalRecordRepository.findByRegisterId(registerId);
            if (StringUtils.isEmpty(medicalRecordEntity)) {
                patientInforRspVO.setMessage("医生门诊处未处理！");
                return patientInforRspVO;
            }

            patientInforRspVO.setAge(DateUtil.getAge(patientInfor.getBirthday()));
            patientInforRspVO.setCardId(patientInfor.getCardId());
            patientInforRspVO.setName(patientInfor.getName());
            patientInforRspVO.setSex(patientInfor.getSex());
            patientInforRspVO.setNationality(patientInfor.getNationality());
            return patientInforRspVO;
        }
    }

    @Override
    public BaseResponse<?> saveMedicalExaminationInfo(medicalExaminationInfoReqVO reqVO) {

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(user)) {
            return null;
        }

        String patientId = iPatientRepository.findByCardId(reqVO.getCardId()).getId();


        OutpatientQueueEntity outpatientQueueEntity = iOutpatientQueueRepository.findByPatientId(patientId);
        if (StringUtils.isEmpty(outpatientQueueEntity)) {
            return BaseResponse.errormsg("请先预约挂号！");
        }

        String registerId = outpatientQueueEntity.getRegister().getId();

        MedicalRecordEntity medicalRecordEntity = iMedicalRecordRepository.findByRegisterId(registerId);
        if (StringUtils.isEmpty(medicalRecordEntity)) {
            return BaseResponse.errormsg("医生门诊处未处理！");
        }

        String prescriptionNum = medicalRecordEntity.getPrescriptionNum();

        MedicalExaminationEntity medicalExamination = iMedicalExaminationRepository.findByPrescriptionNum(prescriptionNum);

        medicalExamination.setBloodPressure(reqVO.getBloodPressure());
        medicalExamination.setBodyTemperature(reqVO.getBodyTemperature());
        medicalExamination.setHeartRate(reqVO.getHeartRate());
        medicalExamination.setPulse(reqVO.getPulse());
        medicalExamination.setExaminationOperator(user.getId());
        medicalExamination.setExaminationCost(Integer.parseInt(reqVO.getExaminationCost()));
        try {
            iMedicalExaminationRepository.saveAndFlush(medicalExamination);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(Contants.user.FAIL);
        }
    }
}
