package com.xgs.hisystem.service.impl;

import com.sun.jna.Native;
import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.IDcardEntity;
import com.xgs.hisystem.pojo.entity.PatientEntity;
import com.xgs.hisystem.pojo.entity.RegisterEntity;
import com.xgs.hisystem.pojo.entity.UserEntity;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.register.*;
import com.xgs.hisystem.repository.IIDcardRepository;
import com.xgs.hisystem.repository.IPatientRepository;
import com.xgs.hisystem.repository.IRegisterRepository;
import com.xgs.hisystem.repository.IUserRepository;
import com.xgs.hisystem.service.Dcrf32_h;
import com.xgs.hisystem.service.IRegisterService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xgs
 * @date 2019/4/19
 * @description:
 */
@Service
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IIDcardRepository iiDcardRepository;

    @Autowired
    private IPatientRepository iPatientRepository;
    @Autowired
    private IRegisterRepository iRegisterRepository;

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    /**
     * 获取就诊卡信息
     *
     * @return
     */
    @Override
    public PatientInforRspVO getCardIdInfor() {

        String message = defaultGetCardId();

        PatientInforRspVO patientInforRspVO = new PatientInforRspVO();

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
                patientInforRspVO.setMessage("未从该卡片识别到卡片信息！");
                return patientInforRspVO;
            }
            patientInforRspVO.setAge(patientInfor.getAge());
            patientInforRspVO.setCardId(patientInfor.getCardId());
            patientInforRspVO.setName(patientInfor.getName());
            patientInforRspVO.setSex(patientInfor.getSex());
            patientInforRspVO.setNationality(patientInfor.getNationality());
            return patientInforRspVO;
        }

    }

    /**
     * 【没有身份证阅读器，将普通IC卡与病人信息绑定】
     * <p>
     * 获取身份证信息
     *
     * @return
     */
    @Override
    public IDcardRspVO getIDcardInfor() {

        String message = defaultGetCardId();

        IDcardRspVO iDcardRspVO = new IDcardRspVO();

        if (message.equals("fail")) {
            iDcardRspVO.setMessage("读卡失败！请刷新页面重试");
            return iDcardRspVO;
        } else if (message.equals("none")) {
            iDcardRspVO.setMessage("未识别到卡片！");
            return iDcardRspVO;
        } else {
            String cardId = message;

            Optional<IDcardEntity> iDcardEntity = iiDcardRepository.findById(cardId);

            if (!iDcardEntity.isPresent()) {
                iDcardRspVO.setMessage("未从该卡片识别到证件信息！");
                return iDcardRspVO;
            }
            iDcardRspVO.setAddress(iDcardEntity.get().getAddress());
            iDcardRspVO.setBirth(iDcardEntity.get().getBirthday());
            iDcardRspVO.setIdcard(iDcardEntity.get().getIdCard());
            iDcardRspVO.setName(iDcardEntity.get().getName());
            iDcardRspVO.setNationality(iDcardEntity.get().getNationality());
            iDcardRspVO.setSex(iDcardEntity.get().getSex());
            return iDcardRspVO;
        }
    }

    /**
     * 读取新卡
     *
     * @return
     */
    @Override
    public BaseResponse<?> getDefaultGetCardId() {

        return BaseResponse.success(defaultGetCardId());
    }

    /**
     * 添加就诊卡
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> addPatientInfor(PatientInforReqVO reqVO) throws Exception {

        PatientEntity patientEntity1 = iPatientRepository.findByCardId(reqVO.getCardId());
        if (!StringUtils.isEmpty(patientEntity1)) {
            return BaseResponse.errormsg(Contants.register.ACTIVATED);
        }
        //补办就诊卡
        PatientEntity patientEntity2 = iPatientRepository.findByIdCard(reqVO.getIdcard());
        if (!StringUtils.isEmpty(patientEntity2)) {
            return BaseResponse.errormsg(Contants.register.COVER);
        }

        PatientEntity patientInfor = new PatientEntity();
        patientInfor.setAddress(reqVO.getAddress());
        patientInfor.setBirthday(reqVO.getBirth());
        patientInfor.setSex(reqVO.getSex());
        patientInfor.setNationality(reqVO.getNationality());
        patientInfor.setName(reqVO.getName());
        patientInfor.setIdCard(reqVO.getIdcard());
        patientInfor.setCardId(reqVO.getCardId());
        patientInfor.setAge(DateUtil.getAge(reqVO.getBirth()));
        patientInfor.setRegisterStatus(0);

        try {
            iPatientRepository.saveAndFlush(patientInfor);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(Contants.user.FAIL);
        }
    }

    /**
     * 补办就诊卡
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> coverCardId(PatientInforReqVO reqVO) {

        PatientEntity patientInfor = iPatientRepository.findByIdCard(reqVO.getIdcard());
        if (StringUtils.isEmpty(patientInfor)) {
            return BaseResponse.errormsg(Contants.user.FAIL);
        }
        patientInfor.setCardId(reqVO.getCardId());

        try {
            iPatientRepository.saveAndFlush(patientInfor);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.success(Contants.user.FAIL);
        }
    }

    /**
     * 挂号获取医生
     *
     * @param reqVO
     * @return
     */
    @Override
    public List<RegisterDoctorRspVO> getAllRegisterDoctor(RegisterTypeReqVO reqVO) {

        if (StringUtils.isEmpty(reqVO)) {
            return null;
        }
        List<RegisterDoctorRspVO> registerDoctorRspList = new ArrayList<>();
        List<UserEntity> userList = iUserRepository.findByDepartmentAndDepartmentType(reqVO.getDepartment(), reqVO.getRegisterType());
        if (userList != null && userList.size() > 0) {

            RegisterDoctorRspVO registerDoctorRspVO = new RegisterDoctorRspVO();
            userList.forEach(user -> {
                registerDoctorRspVO.setDoctorName(user.getUsername());
                registerDoctorRspVO.setAllowNum(user.getAllowNum());
                registerDoctorRspVO.setNowNum(user.getNowNum());
                registerDoctorRspVO.setWorkDateTime(user.getWorkDateTime());
                registerDoctorRspVO.setPrice(user.getTreatmentPrice());
                registerDoctorRspVO.setId(user.getId());

                registerDoctorRspList.add(registerDoctorRspVO);
            });

        } else {
            return null;
        }

        return registerDoctorRspList;
    }

    /**
     * 修改实时挂号人数
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse<?> changeRegisterNum(String id, String cardId) {

        if (StringUtils.isEmpty(cardId)) {
            return BaseResponse.errormsg("请读取就诊卡！");
        }

        PatientEntity patient = iPatientRepository.findByCardId(cardId);
        if (patient.getRegisterStatus() == 1) {
            return BaseResponse.errormsg("该就诊卡已挂号！");
        }
        Optional<UserEntity> user = iUserRepository.findById(id);
        int allowNum = user.get().getAllowNum();
        int nowNum = user.get().getNowNum();
        if (nowNum == allowNum) {
            return BaseResponse.errormsg("该医生已挂号人数已达上限，请刷新页面重新选择！");
        }
        user.get().setNowNum(nowNum + 1);

        try {
            iUserRepository.saveAndFlush(user.get());
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.errormsg("挂号异常，请刷新页面重试！");
        }

    }

    @Override
    public BaseResponse<?> addRegisterInfor(RegisterInforReqVO reqVO) {
        PatientEntity patient = iPatientRepository.findByCardId(reqVO.getCardId());
        if (patient.getRegisterStatus() == 1) {
            return BaseResponse.errormsg("该就诊卡已挂号！");
        }
        patient.setRegisterStatus(1);
        iPatientRepository.saveAndFlush(patient);
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        RegisterEntity register = new RegisterEntity();

        register.setDepartment(reqVO.getDepartment());
        register.setDoctor(reqVO.getDoctor());
        register.setOperator(user.getUsername());
        register.setPatient(patient);
        register.setPayType(reqVO.getPayType());
        register.setRegisterType(reqVO.getRegisterType());
        register.setTreatmentPrice(reqVO.getTreatmentPrice());

        try {
            iRegisterRepository.saveAndFlush(register);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.errormsg("提交信息异常！");
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
