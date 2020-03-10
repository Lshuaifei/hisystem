package com.xgs.hisystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.toll.SaveTollInfoReqVO;
import com.xgs.hisystem.pojo.vo.toll.TollMedicalRecordRspVO;
import com.xgs.hisystem.pojo.vo.toll.TollRspVO;
import com.xgs.hisystem.pojo.vo.toll.cardRspVO;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.ITollService;
import com.xgs.hisystem.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ITollTakeDrugInfoRepository iTollTakeDrugInfoRepository;

    private static final Logger logger = LoggerFactory.getLogger(TollServiceImpl.class);

    @Override
    public cardRspVO getCardIdInfor() {

        String cardId = defaultGetCardId();

        cardRspVO cardRspVO = new cardRspVO();

        if ("fail".equals(cardId)) {
            cardRspVO.setMessage("读卡失败！请刷新页面重试");
            return cardRspVO;
        } else if ("none".equals(cardId)) {
            cardRspVO.setMessage("未识别到卡片！");
            return cardRspVO;
        } else {
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

        Optional<PatientEntity> patient=iPatientRepository.findById(patientId);

        //患者处方存在已划价收费未取药，禁止划价
        TollTakeDrugInfoEntity tollTakeDrugInfo=iTollTakeDrugInfoRepository.findByPatientIdAndTakingDrugStatus(patientId,0);
        if (tollTakeDrugInfo!=null){
            return null;
        }

        int chargeStatus = Integer.parseInt(tollStatus);

        List<RegisterEntity> registerList = iRegisterRepository.findAll((Specification<RegisterEntity>) (root, query, cb) -> {
            List<Predicate> predicateList=new ArrayList<>();

            predicateList.add(cb.equal(root.get("patient"),patient.get() ));
            predicateList.add(cb.equal(root.get("chargeStatus"),chargeStatus ));
            predicateList.add(cb.equal(root.get("registerStatus"),1 ));
            predicateList.add(cb.equal(root.get("treatmentStatus"),1 ));

            query.where(predicateList.toArray(new Predicate[predicateList.size()]));
            return null;
        }, Sort.by(Sort.Direction.DESC,"createDatetime"));

        if (registerList == null || registerList.size() <= 0) {
            return null;
        }
        List<TollRspVO> tollRspVOList = new ArrayList<>();

        for (RegisterEntity register : registerList) {
            TollRspVO tollRspVO = new TollRspVO();
            MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByRegisterId(register.getId());
            if (StringUtils.isEmpty(medicalRecord)) {
                continue;
            }
            tollRspVO.setPrescriptionNum(medicalRecord.getPrescriptionNum());
            tollRspVO.setRegisterId(register.getId());
            tollRspVO.setRegisterType(register.getRegisterType());
            tollRspVO.setDepartment(register.getDepartment());
            tollRspVO.setDoctorName(register.getDoctor());
            tollRspVO.setOutpatientDate(medicalRecord.getCreateDatetime());
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
        recordRspVO.setCreateDate(DateUtil.DateTimeToDate(medicalRecord.getCreateDatetime()));
        recordRspVO.setDiagnosisResult(medicalRecord.getDiagnosisResult());
        recordRspVO.setDrugCost(medicalRecord.getDrugCost());
        recordRspVO.setMedicalOrder(medicalRecord.getMedicalOrder());
        recordRspVO.setName(patient.getName());
        recordRspVO.setNationality(patient.getNationality());
        recordRspVO.setPrescription(medicalRecord.getPrescription());
        recordRspVO.setSex(patient.getSex());
        recordRspVO.setNowDate(DateUtil.getCurrentDateSimpleToString());
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

        Optional<RegisterEntity> register= iRegisterRepository.findById(reqVO.getRegisterId());
        if (!register.isPresent()) {
            return BaseResponse.errormsg("未查询到相关挂号记录！");
        }


        MedicalRecordEntity medicalRecord = iMedicalRecordRepository.findByPrescriptionNum(reqVO.getPrescriptionNum());
        if (medicalRecord==null) {
            return BaseResponse.errormsg("未查询到相关就诊记录！");
        }
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        if (user==null) {
            return BaseResponse.errormsg("登录信息异常！");
        }
        TollTakeDrugInfoEntity tollTakeDrugInfo=new TollTakeDrugInfoEntity();
        tollTakeDrugInfo.setPrescriptionNum(medicalRecord.getPrescriptionNum());
        tollTakeDrugInfo.setTakingDrugStatus(0);
        tollTakeDrugInfo.setTollOperator(user.getId());
        tollTakeDrugInfo.setTollDateTime(DateUtil.getCurrentDateToString());
        tollTakeDrugInfo.setPatientId(register.get().getPatient().getId());
        //更新收费状态
        register.get().setChargeStatus(1);

        try {
            iRegisterRepository.saveAndFlush(register.get());
            iTollTakeDrugInfoRepository.saveAndFlush(tollTakeDrugInfo);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            logger.error("req={},保存划价收费—拿药信息异常！msg={}", JSON.toJSONString(reqVO,true),e.toString());
            return BaseResponse.errormsg("操作异常，请稍后重试！");
        }
    }

}
