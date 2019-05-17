package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.toll.SaveTollInfoReqVO;
import com.xgs.hisystem.pojo.vo.toll.TollMedicalRecordRspVO;
import com.xgs.hisystem.pojo.vo.toll.TollRspVO;
import com.xgs.hisystem.pojo.vo.toll.cardRspVO;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-14
 * @description:
 */
public interface ITollService {

    cardRspVO getCardIdInfor();

    List<TollRspVO> getAllMedicalRecord(String cardId, String tollStatus);

    TollMedicalRecordRspVO getMedicalRecord(String cardId, String registerId) throws Exception;

    BaseResponse<?> saveTollInfo(SaveTollInfoReqVO reqVO);
}