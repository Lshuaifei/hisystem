package com.xgs.hisystem.service.impl;

import com.sun.jna.Native;
import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.MedicalRecordEntity;
import com.xgs.hisystem.pojo.entity.PatientEntity;
import com.xgs.hisystem.pojo.entity.RegisterEntity;
import com.xgs.hisystem.pojo.entity.UserEntity;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.toll.SaveTollInfoReqVO;
import com.xgs.hisystem.pojo.vo.toll.TollMedicalRecordRspVO;
import com.xgs.hisystem.pojo.vo.toll.TollRspVO;
import com.xgs.hisystem.pojo.vo.toll.cardRspVO;
import com.xgs.hisystem.repository.IMedicalRecordRepository;
import com.xgs.hisystem.repository.IPatientRepository;
import com.xgs.hisystem.repository.IRegisterRepository;
import com.xgs.hisystem.service.Dcrf32_h;
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
        recordRspVO.setExaminationCost(medicalRecord.getExaminationCost());
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
