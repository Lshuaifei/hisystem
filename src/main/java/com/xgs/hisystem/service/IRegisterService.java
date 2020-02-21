package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.register.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/19
 * @description:
 */
public interface IRegisterService {

    PatientInforRspVO getCardIdInfor(GetCardIdInforReqVO reqVO) throws Exception;

    IDcardRspVO getIDcardInfor();

    BaseResponse<?> getDefaultGetCardId();

    BaseResponse<?> addPatientInfor(PatientInforReqVO reqVO) throws Exception;

    BaseResponse<?> coverCardId(PatientInforReqVO reqVO);

    List<RegisterDoctorRspVO> getAllRegisterDoctor(RegisterTypeReqVO reqVO);

    /* BaseResponse<?> changeRegisterNum(String id, String cardId);*/

    BaseResponse<?> addRegisterInfor(RegisterInforReqVO reqVO);

    PageRspBO<RegisterRecordRspVO> getRegisterRecord(RegisterRecordSearchReqVO reqVO);
}
