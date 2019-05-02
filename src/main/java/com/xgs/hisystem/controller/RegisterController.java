package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.ValidationResultBO;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.register.*;
import com.xgs.hisystem.service.IRegisterService;
import com.xgs.hisystem.util.ParamsValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/19
 * @description:
 */
@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private IRegisterService iRegisterService;

    /**
     * 读取就诊卡
     *
     * @return
     */
    @PostMapping(value = "/getCardIdInfor")
    public PatientInforRspVO getCardIdInfor() {
        PatientInforRspVO patientInforRspVO = iRegisterService.getCardIdInfor();
        return patientInforRspVO;
    }

    /**
     * 读取身份证
     *
     * @return
     */
    @PostMapping(value = "/getIDcardInfor")
    public IDcardRspVO getIDcardInfor() {

        IDcardRspVO iDcardRspVO = iRegisterService.getIDcardInfor();
        return iDcardRspVO;
    }

    /**
     * 读取新卡
     *
     * @return
     */
    @PostMapping(value = "/getDefaultGetCardId")
    public String getDefaultGetCardId() {
        BaseResponse baseResponse = iRegisterService.getDefaultGetCardId();
        return baseResponse.getMessage();
    }

    /**
     * 办就诊卡
     *
     * @param reqVO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/addPatientInfor")
    public String addPatientInfor(@RequestBody PatientInforReqVO reqVO) throws Exception {

        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }
        BaseResponse baseResponse = iRegisterService.addPatientInfor(reqVO);
        return baseResponse.getMessage();
    }

    /**
     * 补办就诊卡
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/coverCardId")
    public String coverCardId(@RequestBody PatientInforReqVO reqVO) {

        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }
        BaseResponse baseResponse = iRegisterService.coverCardId(reqVO);
        return baseResponse.getMessage();
    }

    @RequestMapping(value = "/getAllRegisterDoctor")
    public List<RegisterDoctorRspVO> getAllRegisterDoctor(RegisterTypeReqVO reqVO) {

        return iRegisterService.getAllRegisterDoctor(reqVO);

    }

    /**
     * 修改实时挂号人数
     *
     * @param id
     * @return
     */

    @PostMapping(value = "/changeRegisterNum")
    public String changeRegisterNum(@RequestParam String id, @RequestParam String cardId) {

        BaseResponse baseResponse = iRegisterService.changeRegisterNum(id, cardId);
        return baseResponse.getMessage();
    }


    @PostMapping(value = "/addRegisterInfor")
    public String addRegisterInfor(@RequestBody RegisterInforReqVO reqVO) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }

        BaseResponse baseResponse = iRegisterService.addRegisterInfor(reqVO);
        return baseResponse.getMessage();
    }
}
