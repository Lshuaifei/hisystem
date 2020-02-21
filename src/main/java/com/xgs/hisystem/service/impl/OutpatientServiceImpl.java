package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.outpatient.*;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.IOutpatientService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.xgs.hisystem.util.card.Card.defaultGetCardId;

/**
 * @author xgs
 * @date 2019-5-6
 * @description:
 */
@Service
public class OutpatientServiceImpl implements IOutpatientService {

    @Autowired
    private IPatientRepository iPatientRepository;
    @Autowired
    private IRegisterRepository iRegisterRepository;
    @Autowired
    private IOutpatientQueueRepository iOutpatientQueueRepository;
    @Autowired
    private IMedicalRecordRepository iMedicalRecordRepository;
    @Autowired
    private IDrugRepository iDrugRepository;
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IMedicalExaminationRepository iMedicalExaminationRepository;

    private static final Logger logger = LoggerFactory.getLogger(OutpatientServiceImpl.class);

    /**
     * 获取就诊卡信息
     *
     * @return
     */
    @Override
    public PatientInforRspVO getCardIdInfor(GetCardIdInforReqVO reqVO) throws Exception {

        String myCardId = reqVO.getCardId();
        String command = reqVO.getCommand();
        PatientInforRspVO patientInforRspVO = new PatientInforRspVO();
        //手动输入卡号
        if ("1".equals(command)) {
            if (StringUtils.isEmpty(myCardId)) {
                patientInforRspVO.setMessage("请输入就诊卡号！");
                return patientInforRspVO;
            }
        }
        //读卡器输入
        if ("0".equals(command)) {
            String message = defaultGetCardId();
            if ("fail".equals(message)) {
                patientInforRspVO.setMessage("读卡失败！请刷新页面重试");
                return patientInforRspVO;
            } else if ("none".equals(message)) {
                patientInforRspVO.setMessage("未识别到卡片！");
                return patientInforRspVO;
            } else {
                myCardId = message;
            }
        }
        PatientEntity patientInfor = iPatientRepository.findByCardId(myCardId);

        if (StringUtils.isEmpty(patientInfor)) {
            patientInforRspVO.setMessage("未从该卡片识别到信息！");
            return patientInforRspVO;
        }
        String patientId = patientInfor.getId();

        OutpatientQueueEntity outpatientQueue = iOutpatientQueueRepository.findByPatientId(patientId);

        if (StringUtils.isEmpty(outpatientQueue)) {
            patientInforRspVO.setMessage("请先挂号预约！");
            return patientInforRspVO;
        }


        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(user)) {
            return null;
        }

        if (!outpatientQueue.getUser().getId().equals(user.getId())) {
            patientInforRspVO.setMessage("该患者未在您的门诊队列！");
            return patientInforRspVO;
        }
        if (outpatientQueue.getOutpatientQueueStatus() == -1) {
            patientInforRspVO.setMessage("未完成就诊，请从左侧栏恢复！");
            return patientInforRspVO;
        }

        patientInforRspVO.setAge(DateUtil.getAge(patientInfor.getBirthday()));
        BeanUtils.copyProperties(patientInfor, patientInforRspVO);
        patientInforRspVO.setDate(DateUtil.getCurrentDateSimpleToString());
        patientInforRspVO.setDepartment(outpatientQueue.getRegister().getDepartment());

        String registerId = outpatientQueue.getRegister().getId();
        MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByRegisterId(registerId);
        if (StringUtils.isEmpty(medicalRecord)) {
            patientInforRspVO.setPrescriptionNum(String.valueOf(System.currentTimeMillis()));
        } else {
            patientInforRspVO.setPrescriptionNum(medicalRecord.getPrescriptionNum());
        }
        return patientInforRspVO;
    }

    @Override
    public BaseResponse<?> changePatientInfor(OtherPatientInforReqVO reqVO) {
        PatientEntity patient = iPatientRepository.findByCardId(reqVO.getCardId());

        patient.setMaritalStatus(reqVO.getMaritalStatus());
        patient.setCareer(reqVO.getCareer());
        patient.setPersonalHistory(reqVO.getPersonalHistory());
        patient.setPastHistory(reqVO.getPastHistory());
        patient.setFamilyHistory(reqVO.getFamilyHistory());


        try {
            iPatientRepository.saveAndFlush(patient);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.errormsg(Contants.user.FAIL);
        }
    }

    @Override
    public List<OutpatientQueueNormalRspVO> getAllPatientNormal() {

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(user)) {
            return null;
        }

        List<OutpatientQueueEntity> outpatientQueueList = iOutpatientQueueRepository.findByUserId(user.getId());

        if (outpatientQueueList != null && outpatientQueueList.size() > 0) {

            List<OutpatientQueueNormalRspVO> outpatientQueueNormalRspVOList = new ArrayList<>();

            //非当天病人
            List<OutpatientQueueEntity> notQueueList = new ArrayList<>();

            outpatientQueueList.forEach(outpatientQueue -> {

                String createDate = DateUtil.DateTimeToDate(outpatientQueue.getCreateDatetime());
                String nowDate = DateUtil.getCurrentDateSimpleToString();

                if (nowDate.equals(createDate)) {
                    if (outpatientQueue.getOutpatientQueueStatus() == 1) {
                        OutpatientQueueNormalRspVO outpatientQueueNormalRspVO = new OutpatientQueueNormalRspVO();

                        outpatientQueueNormalRspVO.setCardId(outpatientQueue.getRegister().getPatient().getCardId());
                        outpatientQueueNormalRspVO.setPatientName(outpatientQueue.getRegister().getPatient().getName());
                        outpatientQueueNormalRspVOList.add(outpatientQueueNormalRspVO);
                    }
                } else {
                    notQueueList.add(outpatientQueue);
                }
            });
            if (notQueueList.size() > 0) {
                iOutpatientQueueRepository.deleteAll(notQueueList);
            }


            return outpatientQueueNormalRspVOList;
        } else {
            return null;
        }
    }

    @Override
    public List<OutpatientQueueLaterRspVO> getAllPatientLater() {

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(user)) {
            return null;
        }

        List<OutpatientQueueEntity> outpatientQueueList = iOutpatientQueueRepository.findByUserId(user.getId());

        if (outpatientQueueList != null && outpatientQueueList.size() > 0) {

            List<OutpatientQueueLaterRspVO> outpatientQueueLaterRspVOList = new ArrayList<>();

            //非当天病人
            List<OutpatientQueueEntity> notQueueList = new ArrayList<>();

            outpatientQueueList.forEach(outpatientQueue -> {

                String createDate = DateUtil.DateTimeToDate(outpatientQueue.getCreateDatetime());
                String nowDate = DateUtil.getCurrentDateSimpleToString();

                if (nowDate.equals(createDate)) {
                    if (outpatientQueue.getOutpatientQueueStatus() == -1) {

                        OutpatientQueueLaterRspVO outpatientQueueLaterRspVO = new OutpatientQueueLaterRspVO();
                        outpatientQueueLaterRspVO.setCardId(outpatientQueue.getRegister().getPatient().getCardId());
                        outpatientQueueLaterRspVO.setPatientName(outpatientQueue.getRegister().getPatient().getName());
                        outpatientQueueLaterRspVO.setRegisterId(outpatientQueue.getRegister().getId());
                        outpatientQueueLaterRspVOList.add(outpatientQueueLaterRspVO);
                    }
                } else {
                    notQueueList.add(outpatientQueue);
                }
            });
            if (notQueueList.size() > 0) {
                iOutpatientQueueRepository.deleteAll(notQueueList);
            }
            return outpatientQueueLaterRspVOList;
        } else {
            return null;
        }
    }

    /**
     * 稍后处理
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> processLaterMedicalRecord(MedicalRecordReqVO reqVO) {

        String cardId = reqVO.getCardId();

        if (StringUtils.isEmpty(cardId)) {
            return BaseResponse.errormsg("请先读取就诊卡！");
        }

        String patientId = iPatientRepository.findByCardId(cardId).getId();

        OutpatientQueueEntity outpatientQueue = iOutpatientQueueRepository.findByPatientId(patientId);


        RegisterEntity register = outpatientQueue.getRegister();

        MedicalRecordEntity medicalRecordTemp = iMedicalRecordRepository.findByRegisterId(register.getId());

        if (StringUtils.isEmpty(medicalRecordTemp)) {

            MedicalRecordEntity medicalRecord = new MedicalRecordEntity();

            medicalRecord.setConditionDescription(reqVO.getConditionDescription());
            medicalRecord.setRegister(register);

            medicalRecord.setPrescriptionNum(reqVO.getPrescriptionNum());

            RegisterEntity registerEntity = iRegisterRepository.findById(register.getId()).get();
            registerEntity.setTreatmentStatus(1);

            MedicalExaminationEntity medicalExamination = new MedicalExaminationEntity();
            medicalExamination.setPrescriptionNum(reqVO.getPrescriptionNum());

            try {
                iMedicalRecordRepository.saveAndFlush(medicalRecord);
                iRegisterRepository.saveAndFlush(registerEntity);
                iMedicalExaminationRepository.saveAndFlush(medicalExamination);
            } catch (Exception e) {
                return BaseResponse.success(Contants.user.FAIL);
            }

        }
        outpatientQueue.setOutpatientQueueStatus(-1);

        try {
            iOutpatientQueueRepository.saveAndFlush(outpatientQueue);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(Contants.user.FAIL);
        }


    }

    @Override
    public PatientInforRspVO restorePatientInfor(String registerId) throws Exception {

        OutpatientQueueEntity outpatientQueue = iOutpatientQueueRepository.findByRegisterId(registerId);

        MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByRegisterId(registerId);

        if (StringUtils.isEmpty(outpatientQueue) || StringUtils.isEmpty(medicalRecord)) {
            return null;
        }

        PatientInforRspVO patientInforRspVO = new PatientInforRspVO();

        outpatientQueue.setOutpatientQueueStatus(1);
        try {
            iOutpatientQueueRepository.saveAndFlush(outpatientQueue);
        } catch (Exception e) {
            patientInforRspVO.setMessage("系统异常，请稍后重试！");
            return patientInforRspVO;
        }
        PatientEntity patientInfor = outpatientQueue.getPatient();

        patientInforRspVO.setAge(DateUtil.getAge(patientInfor.getBirthday()));
        patientInforRspVO.setCardId(patientInfor.getCardId());
        patientInforRspVO.setName(patientInfor.getName());
        patientInforRspVO.setSex(patientInfor.getSex());
        patientInforRspVO.setNationality(patientInfor.getNationality());
        patientInforRspVO.setCareer(patientInfor.getCareer());
        patientInforRspVO.setMaritalStatus(patientInfor.getMaritalStatus());
        patientInforRspVO.setPersonalHistory(patientInfor.getPersonalHistory());
        patientInforRspVO.setPastHistory(patientInfor.getPastHistory());
        patientInforRspVO.setFamilyHistory(patientInfor.getFamilyHistory());

        patientInforRspVO.setConditionDescription(medicalRecord.getConditionDescription());
        patientInforRspVO.setPrescriptionNum(medicalRecord.getPrescriptionNum());
        patientInforRspVO.setDepartment(outpatientQueue.getRegister().getDepartment());
        patientInforRspVO.setDate(DateUtil.getCurrentDateSimpleToString());

        return patientInforRspVO;
    }

    @Override
    public List<String> getAllDrug() {

        List<DrugEntity> drugEntityList = iDrugRepository.findAll();

        List<String> drugList = new ArrayList<>();
        drugEntityList.forEach(drug -> {

            drugList.add(drug.getName());
        });
        return drugList;
    }

    @Override
    public DrugRspVO getDrugInfor(String drug) {

        DrugEntity drugEntity = iDrugRepository.findByName(drug);
        if (StringUtils.isEmpty(drugEntity)) {
            return null;
        }
        DrugRspVO drugRspVO = new DrugRspVO();
        drugRspVO.setSpecification(drugEntity.getSpecification() + "/" + drugEntity.getUnit());
        drugRspVO.setPrice(drugEntity.getPrice());
        return drugRspVO;
    }


    /**
     * 就诊完成，保存病历
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> addMedicalRecord(MedicalRecordReqVO reqVO) {

        MedicalRecordEntity medicalR = iMedicalRecordRepository.findByPrescriptionNum(reqVO.getPrescriptionNum());


        String cardId = reqVO.getCardId();


        String patientId = iPatientRepository.findByCardId(cardId).getId();
        OutpatientQueueEntity outpatientQueue = iOutpatientQueueRepository.findByPatientId(patientId);
        String doctorId = outpatientQueue.getRegister().getDoctorId();
        UserEntity userEntity = iUserRepository.findById(doctorId).get();
        userEntity.setUpdateTime(DateUtil.getCurrentDateSimpleToString());
        if (!StringUtils.isEmpty(medicalR)) {
            medicalR.setConditionDescription(reqVO.getConditionDescription());
            medicalR.setDiagnosisResult(reqVO.getDiagnosisResult());
            medicalR.setDrugCost(reqVO.getDrugCost());
            medicalR.setMedicalOrder(reqVO.getMedicalOrder());
            medicalR.setPrescription(reqVO.getPrescription());

            try {
                iMedicalRecordRepository.saveAndFlush(medicalR);
                iOutpatientQueueRepository.delete(outpatientQueue);
                iUserRepository.saveAndFlush(userEntity);
                return BaseResponse.success(Contants.user.SUCCESS);
            } catch (Exception e) {
                return BaseResponse.success(Contants.user.FAIL);
            }


        }


        RegisterEntity register = outpatientQueue.getRegister();


        MedicalRecordEntity medicalRecord = new MedicalRecordEntity();

        medicalRecord.setConditionDescription(reqVO.getConditionDescription());
        medicalRecord.setRegister(register);

        medicalRecord.setPrescriptionNum(reqVO.getPrescriptionNum());

        medicalRecord.setDiagnosisResult(reqVO.getDiagnosisResult());
        medicalRecord.setDrugCost(reqVO.getDrugCost());
        medicalRecord.setMedicalOrder(reqVO.getMedicalOrder());
        medicalRecord.setPrescription(reqVO.getPrescription());

        RegisterEntity registerEntity = iRegisterRepository.findById(register.getId()).get();
        registerEntity.setTreatmentStatus(1);

        try {
            iMedicalRecordRepository.saveAndFlush(medicalRecord);
            iRegisterRepository.saveAndFlush(registerEntity);
            iOutpatientQueueRepository.delete(outpatientQueue);
            iUserRepository.saveAndFlush(userEntity);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(Contants.user.FAIL);
        }

    }

    @Override
    public medicalExaminationInfoRspVO getMedicalExamination(String prescriptionNum) {

        MedicalExaminationEntity medicalExamination = iMedicalExaminationRepository.findByPrescriptionNum(prescriptionNum);

        medicalExaminationInfoRspVO rspVO = new medicalExaminationInfoRspVO();
        if (StringUtils.isEmpty(medicalExamination)) {
            rspVO.setMessage("未查询到相关体检信息！");
            return rspVO;
        }

        rspVO.setHeartRate(medicalExamination.getHeartRate());
        rspVO.setBodyTemperature(medicalExamination.getBodyTemperature());
        rspVO.setBloodPressure(medicalExamination.getBloodPressure());

        rspVO.setPulse(medicalExamination.getPulse());

        return rspVO;
    }
}
