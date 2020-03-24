package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.outpatient.*;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.service.IOutpatientService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-6
 * @description:
 */
@RestController
@RequestMapping(value = "/outpatient")
@Api(tags = "门诊管理API")
public class OutpatientController {

    @Autowired
    private IOutpatientService iOutpatientService;

    /**
     * 读取就诊卡,获取病人信息
     *
     * @return
     */
    @PostMapping(value = "/getCardIdInfor")
    public PatientInforRspVO getCardIdInfor(@RequestBody GetCardIdInforReqVO reqVO) throws Exception {

        return iOutpatientService.getCardIdInfor(reqVO);
    }

    /**
     * 修改患者基础信息
     *
     * @param reqVO
     * @return
     */

    @PostMapping(value = "/changePatientInfor")
    public String changePatientInfor(@RequestBody OtherPatientInforReqVO reqVO) {

        BaseResponse baseResponse = iOutpatientService.changePatientInfor(reqVO);
        return baseResponse.getMessage();
    }

    /**
     * 就诊，稍后处理
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/ProcessLaterMedicalRecord")
    public String processLaterMedicalRecord(@RequestBody MedicalRecordReqVO reqVO) {

        BaseResponse baseResponse = iOutpatientService.processLaterMedicalRecord(reqVO);

        return baseResponse.getMessage();

    }

    /**
     * 从稍后处理恢复到队列
     *
     * @param registerId
     * @return
     */
    @PostMapping(value = "/restorePatientInfor")
    public PatientInforRspVO restorePatientInfor(@RequestParam String registerId) throws Exception {

        return iOutpatientService.restorePatientInfor(registerId);
    }

    /**
     * 所有药品名
     *
     * @return
     */

    @PostMapping(value = "/getAllDrug")
    public List<String> getAllDrug() {
        return iOutpatientService.getAllDrug();
    }

    /**
     * 单个药品规格
     *
     * @param drug
     * @return
     */

    @PostMapping(value = "/getDrugInfor")
    public DrugRspVO getDrugInfor(String drug) {
        return iOutpatientService.getDrugInfor(drug);
    }

    /**
     * 就诊完成，保存病历
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/addMedicalRecord")
    public String addMedicalRecord(@RequestBody MedicalRecordReqVO reqVO) {
        BaseResponse baseResponse = iOutpatientService.addMedicalRecord(reqVO);

        return baseResponse.getMessage();
    }

    /**
     * 就诊获取体检信息
     *
     * @param prescriptionNum
     * @return
     */

    @PostMapping(value = "/getMedicalExamination")
    public medicalExaminationInfoRspVO getMedicalExamination(@RequestParam String prescriptionNum) {

        return iOutpatientService.getMedicalExamination(prescriptionNum);
    }

}
