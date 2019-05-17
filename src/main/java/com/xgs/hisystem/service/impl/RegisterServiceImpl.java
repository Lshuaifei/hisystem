package com.xgs.hisystem.service.impl;

import com.sun.jna.Native;
import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.register.*;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.Dcrf32_h;
import com.xgs.hisystem.service.IRegisterService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;

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
    @Autowired
    private IOutpatientQueueRepository iOutpatientQueueRepository;

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
            String patientId = patientInfor.getId();

            List<RegisterEntity> registerList = iRegisterRepository.findByPatientId(patientId);

            //过期的挂号
            List<RegisterEntity> ExpiredList = new ArrayList<>();

            for (RegisterEntity register : registerList) {

                //已挂号未就诊情况下
                if (register.getRegisterStatus() == 1 && register.getTreatmentStatus() == 0) {

                    String createDate = DateUtil.DateTimeToDate(register.getCreateDatetime());
                    String nowDate = DateUtil.getCurrentDateSimpleToString();
                    //当天情况下
                    if (createDate.equals(nowDate)) {
                        patientInforRspVO.setMessage("已挂号，未就诊！");
                        return patientInforRspVO;

                        //不是当天则修改挂号状态为：-1 （过期）
                    } else {
                        register.setRegisterStatus(-1);
                        ExpiredList.add(register);
                    }
                }
            }

            iRegisterRepository.saveAll(ExpiredList);

            patientInforRspVO.setAge(DateUtil.getAge(patientInfor.getBirthday()));
            patientInforRspVO.setCardId(patientInfor.getCardId());
            patientInforRspVO.setName(patientInfor.getName());
            patientInforRspVO.setSex(patientInfor.getSex());
            patientInforRspVO.setNationality(patientInfor.getNationality());
            return patientInforRspVO;
        }

    }

    /**
     * 【没有身份证阅读器，将普通IC卡与病人信息绑定】
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
        patientInfor.setTelphone(reqVO.getTelphone());

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
                registerDoctorRspVO.setWorkAddress(user.getWorkAddress());

                registerDoctorRspList.add(registerDoctorRspVO);
            });

        } else {
            return null;
        }

        return registerDoctorRspList;
    }

    /**
     * 保存挂号记录
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> addRegisterInfor(RegisterInforReqVO reqVO) {

        Optional<UserEntity> user_doctor = iUserRepository.findById(reqVO.getDoctorId());

        if (StringUtils.isEmpty(user_doctor)) {
            return BaseResponse.errormsg("账户登录失效，请重新登录再试！");
        }
        int allowNum = user_doctor.get().getAllowNum();
        int nowNum = user_doctor.get().getNowNum();
        if (nowNum == allowNum) {
            return BaseResponse.errormsg("该医生已挂号人数已达上限，请刷新页面重新选择！");
        }
        user_doctor.get().setNowNum(nowNum + 1);

        try {
            iUserRepository.saveAndFlush(user_doctor.get());
        } catch (Exception e) {
            return BaseResponse.errormsg("挂号异常，请刷新页面重试！");
        }

        String cardId = reqVO.getCardId();

        String doctorName = user_doctor.get().getUsername();

        PatientEntity patient = iPatientRepository.findByCardId(cardId);

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(user)) {
            return null;
        }
        RegisterEntity register = new RegisterEntity();

        register.setDepartment(reqVO.getDepartment());
        register.setDoctor(reqVO.getDoctor());
        register.setOperatorName(user.getUsername());
        register.setOperatorEmail(user.getEmail());
        register.setPatient(patient);
        register.setPayType(reqVO.getPayType());
        register.setRegisterType(reqVO.getRegisterType());
        register.setTreatmentPrice(reqVO.getTreatmentPrice());
        register.setRegisterStatus(1);

        String registeredNum = "RE" + System.currentTimeMillis() + (int) (Math.random() * 900 + 100);
        register.setRegisteredNum(registeredNum);

        try {
            iRegisterRepository.saveAndFlush(register);
        } catch (Exception e) {
            return BaseResponse.errormsg("挂号异常，请刷新页面重试！");
        }
        OutpatientQueueEntity outpatientQueue = new OutpatientQueueEntity();

        RegisterEntity register_outPatient = iRegisterRepository.findByRegisteredNum(registeredNum);

        outpatientQueue.setPatient(patient);

        outpatientQueue.setRegister(register_outPatient);

        outpatientQueue.setUser(user_doctor.get());

        String patientName = patient.getName();
        outpatientQueue.setDescription(patientName + '#' + doctorName);

        outpatientQueue.setOutpatientQueueStatus(1);

        try {
            iOutpatientQueueRepository.saveAndFlush(outpatientQueue);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.errormsg("挂号异常，请刷新页面重试！");
        }
    }

    /**
     * 挂号记录查询
     *
     * @param reqVO
     * @return
     */
    @Override
    public PageRspBO<RegisterRecordRspVO> getRegisterRecord(RegisterRecordSearchReqVO reqVO) {
        Page<RegisterEntity> page = iRegisterRepository.findAll(new Specification<RegisterEntity>() {
            @Override
            public Predicate toPredicate(Root<RegisterEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();

                if (!StringUtils.isEmpty(reqVO.getDepartment())) {
                    predicateList.add(cb.equal(root.get("department"), reqVO.getDepartment()));
                }
                if (!StringUtils.isEmpty(reqVO.getRegisterType())) {
                    predicateList.add(cb.equal(root.get("registerType"), reqVO.getRegisterType()));
                }
                if (!StringUtils.isEmpty(reqVO.getStartTime())) {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("createDatetime"), reqVO.getStartTime()));
                }
                if (!StringUtils.isEmpty(reqVO.getEndTime())) {
                    predicateList.add(cb.lessThanOrEqualTo(root.get("createDatetime"), reqVO.getEndTime()));
                }

                //默认列表
                if (StringUtils.isEmpty(reqVO.getDepartment()) && StringUtils.isEmpty(reqVO.getRegisterType())
                        && StringUtils.isEmpty(reqVO.getStartTime()) && StringUtils.isEmpty(reqVO.getEndTime())) {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("createDatetime"), now().toString()));
                }

                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, PageRequest.of(reqVO.getPageNumber(), reqVO.getPageSize(), Sort.Direction.DESC, "createDatetime"));
        if (page == null) {
            return null;
        }
        List<RegisterEntity> registerList = page.getContent();
        List<RegisterRecordRspVO> registerRecordList = new ArrayList<>();
        registerList.forEach(register -> {
            RegisterRecordRspVO registerRecord = new RegisterRecordRspVO();
            registerRecord.setCardId(register.getPatient().getCardId());
            registerRecord.setDepartment(register.getDepartment());
            registerRecord.setRegisterType(register.getRegisterType());
            registerRecord.setName(register.getPatient().getName());
            registerRecord.setDoctor(register.getDoctor());
            registerRecord.setCreateDateTime(register.getCreateDatetime());
            registerRecord.setCreatePerson(register.getOperatorName());
            registerRecord.setCreatePersonEmail(register.getOperatorEmail());
            registerRecordList.add(registerRecord);
        });
        PageRspBO pageRspBO = new PageRspBO();
        pageRspBO.setTotal(page.getTotalElements());
        pageRspBO.setRows(registerRecordList);
        return pageRspBO;
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
