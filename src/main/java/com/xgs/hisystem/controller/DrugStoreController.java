package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.bo.ValidationResultBO;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugReqVO;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugRspVO;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugSearchReqVO;
import com.xgs.hisystem.service.IDrugStoreService;
import com.xgs.hisystem.util.ParamsValidationUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-12
 * @description:
 */
@RestController
@RequestMapping(value = "/drugstore")
@Api(tags = "药品管理API")
public class DrugStoreController {

    @Autowired
    private IDrugStoreService iDrugStoreService;

    /**
     * 新增药品
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/addNewDrug")
    public String addNewDrug(@RequestBody DrugReqVO reqVO) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }

        BaseResponse baseResponse = iDrugStoreService.addNewDrug(reqVO);
        return baseResponse.getMessage();
    }

    /**
     * 新增剂型
     *
     * @param drugType
     * @return
     */
    @PostMapping(value = "/addDrugType")
    public String addDrugType(@RequestParam String drugType) {

        BaseResponse baseResponse = iDrugStoreService.addDrugType(drugType);
        return baseResponse.getMessage();
    }

    /**
     * 新增功效分类
     *
     * @param efficacyClassification
     * @return
     */
    @PostMapping(value = "/addEfficacyClassification")
    public String addEfficacyClassification(@RequestParam String efficacyClassification) {

        BaseResponse baseResponse = iDrugStoreService.addEfficacyClassification(efficacyClassification);
        return baseResponse.getMessage();
    }

    /**
     * 获取所有剂型
     *
     * @return
     */
    @PostMapping(value = "/getAllDrugType")
    public List<String> getAllDrugType() {
        return iDrugStoreService.getAllDrugType();
    }

    /**
     * 获取所有功效
     *
     * @return
     */
    @PostMapping(value = "/getAllEfficacyClassification")
    public List<String> getAllEfficacyClassification() {
        return iDrugStoreService.getAllEfficacyClassification();
    }

    /**
     * 获取药品信息
     *
     * @param drug
     * @return
     */
    @PostMapping(value = "/getDrugInfor")
    public DrugRspVO getDrugInfor(@RequestParam String drug) {
        DrugRspVO drugRspVO = iDrugStoreService.getDrugInfor(drug);
        return drugRspVO;
    }

    /**
     * 已有药品入库
     *
     * @param drug
     * @param addStorageQuantity
     * @return
     */
    @PostMapping(value = "/addStorageQuantity")
    public String addStorageQuantity(@RequestParam String drug, @RequestParam String addStorageQuantity) {
        BaseResponse baseResponse = iDrugStoreService.addStorageQuantity(drug, addStorageQuantity);

        return baseResponse.getMessage();
    }

    /**
     * 查询药品
     *
     * @param reqVO
     * @return
     */
    @GetMapping(value = "/getAllDrug")
    public PageRspBO<DrugRspVO> getAllDrug(DrugSearchReqVO reqVO) {

        return iDrugStoreService.getAllDrug(reqVO);
    }

    /**
     * 修改药品信息
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/updateDrug")
    public String updateDrug(@RequestBody DrugReqVO reqVO) {
        BaseResponse baseResponse = iDrugStoreService.updateDrug(reqVO);
        return baseResponse.getMessage();
    }

    /**
     * 删除药品
     *
     * @param drugName
     * @return
     */
    @PostMapping(value = "/deleteDrug")
    public String deleteDrug(@RequestParam String drugName) {
        BaseResponse baseResponse = iDrugStoreService.deleteDrug(drugName);
        return baseResponse.getMessage();
    }


}
