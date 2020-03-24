package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.register.GetCardIdInforReqVO;
import com.xgs.hisystem.pojo.vo.toll.SaveTollInfoReqVO;
import com.xgs.hisystem.pojo.vo.toll.TollMedicalRecordRspVO;
import com.xgs.hisystem.pojo.vo.toll.TollRspVO;
import com.xgs.hisystem.pojo.vo.toll.cardRspVO;
import com.xgs.hisystem.service.ITollService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-14
 * @description:
 */
@RestController
@RequestMapping(value = "/toll")
@Api(tags = "收费管理API")
public class TollController {

    @Autowired
    private ITollService iTollService;

    @PostMapping(value = "/getCardIdInfor")
    public cardRspVO getCardIdInfor() {

        return iTollService.getCardIdInfor();
    }

    /**
     * 获取病历信息
     *
     * @param cardId
     * @param tollStatus
     * @return
     */
    @GetMapping(value = "/getAllMedicalRecord")
    public List<TollRspVO> getAllMedicalRecord(@RequestParam String cardId,
                                               @RequestParam String tollStatus) {

        return iTollService.getAllMedicalRecord(cardId, tollStatus);
    }

    /**
     * 获取处方笺信息
     *
     * @param cardId
     * @param registerId
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/getMedicalRecord")
    public TollMedicalRecordRspVO getMedicalRecord(@RequestParam String cardId,
                                                   @RequestParam String registerId) throws Exception {

        return iTollService.getMedicalRecord(cardId, registerId);
    }


    /**
     * 划价收费完成，保存信息
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/saveTollInfo")
    public String saveTollInfo(@RequestBody SaveTollInfoReqVO reqVO) {

        BaseResponse baseResponse = iTollService.saveTollInfo(reqVO);

        return baseResponse.getMessage();
    }
}
