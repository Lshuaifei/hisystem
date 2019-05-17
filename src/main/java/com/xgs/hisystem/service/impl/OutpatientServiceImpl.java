package com.xgs.hisystem.service.impl;

import com.sun.jna.Native;
import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.outpatient.*;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.Dcrf32_h;
import com.xgs.hisystem.service.IOutpatientService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    /**
     * 获取就诊卡信息
     *
     * @return
     */
    @Override
    public PatientInforRspVO getCardIdInfor() throws Exception {

        String message = defaultGetCardId();

        PatientInforRspVO patientInforRspVO = new PatientInforRspVO();

        if (message.equals("fail")) {
            patientInforRspVO.setMessage("读卡失败！请刷新页面或稍后重试");
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
            patientInforRspVO.setCardId(patientInfor.getCardId());
            patientInforRspVO.setName(patientInfor.getName());
            patientInforRspVO.setSex(patientInfor.getSex());
            patientInforRspVO.setNationality(patientInfor.getNationality());
            patientInforRspVO.setCareer(patientInfor.getCareer());
            patientInforRspVO.setMaritalStatus(patientInfor.getMaritalStatus());
            patientInforRspVO.setPersonalHistory(patientInfor.getPersonalHistory());
            patientInforRspVO.setPastHistory(patientInfor.getPastHistory());
            patientInforRspVO.setFamilyHistory(patientInfor.getFamilyHistory());
            patientInforRspVO.setDate(DateUtil.getCurrentDateSimpleToString());
            patientInforRspVO.setDepartment(outpatientQueue.getRegister().getDepartment());
            return patientInforRspVO;


        }
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

                if (createDate.equals(nowDate)) {
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
            if (notQueueList != null && notQueueList.size() > 0) {
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

                if (createDate.equals(nowDate)) {
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
            if (notQueueList != null && notQueueList.size() > 0) {
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
    public BaseResponse<?> ProcessLaterMedicalRecord(MedicalRecordReqVO reqVO) {

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

            try {
                iMedicalRecordRepository.saveAndFlush(medicalRecord);
                iRegisterRepository.saveAndFlush(registerEntity);
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

        if (!StringUtils.isEmpty(medicalR)) {
            medicalR.setConditionDescription(reqVO.getConditionDescription());
            medicalR.setDiagnosisResult(reqVO.getDiagnosisResult());
            medicalR.setDrugCost(reqVO.getDrugCost());
            medicalR.setMedicalOrder(reqVO.getMedicalOrder());
            medicalR.setPrescription(reqVO.getPrescription());


            try {
                iMedicalRecordRepository.saveAndFlush(medicalR);
                iOutpatientQueueRepository.delete(outpatientQueue);
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
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(Contants.user.FAIL);
        }

    }


    /**
     * 默认获取IC卡号
     *
     * @return
     */
    public static String defaultGetCardId() {
        Dcrf32_h dcrf32_h;
        try {
            dcrf32_h = (Dcrf32_h) Native.loadLibrary("dcrf32", Dcrf32_h.class);
        } catch (Exception e) {
            return "fail";
        }

        int result;
        int handle;
        int[] snr = new int[1];
        byte[] send_buffer = new byte[2048];
        byte[] recv_buffer = new byte[2048];


        result = dcrf32_h.dc_init((short) 100, 115200);
        if (result < 0) {
            logger.info("dc_init ...error ");
            return "fail";
        }

        handle = result;

        result = dcrf32_h.dc_config_card(handle, (byte) 0x41);//设置非接卡型为A
        result = dcrf32_h.dc_card(handle, (byte) 0, snr);
        if (result != 0) {
            logger.info("dc_card ...error ");
            dcrf32_h.dc_exit(handle);
            return "none";
        }


        recv_buffer[0] = (byte) ((snr[0] >>> 24) & 0xff);
        recv_buffer[1] = (byte) ((snr[0] >>> 16) & 0xff);
        recv_buffer[2] = (byte) ((snr[0] >>> 8) & 0xff);
        recv_buffer[3] = (byte) ((snr[0] >>> 0) & 0xff);
        String cardid = print_bytes(recv_buffer, 4);

        if (cardid.equals("00000000")) {
            logger.info("未识别到卡片！");
            return "none";
        }

        /* 蜂鸣*/
        dcrf32_h.dc_beep(handle, (short) 10);
        if (result != 0) {
            logger.info("dc_beep ...error ");
            dcrf32_h.dc_exit(handle);
            return "fail";
        }

        result = dcrf32_h.dc_exit(handle);
        if (result != 0) {
            logger.info("dc_exit ...error ");
            return "fail";
        }

        return cardid;
    }

    private static String print_bytes(byte[] b, int length) {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < length; ++i) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            temp.add(hex.toUpperCase());
        }
        return String.join("", temp);
    }
}
