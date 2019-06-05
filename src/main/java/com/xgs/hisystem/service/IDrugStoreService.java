package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugReqVO;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugRspVO;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugSearchReqVO;

import java.util.List;

/**
 * @author xgs
 * @date 2019-5-12
 * @description:
 */
public interface IDrugStoreService {

    BaseResponse<?> addNewDrug(DrugReqVO reqVO);

    BaseResponse<?> addDrugType(String drugType);

    BaseResponse<?> addEfficacyClassification(String efficacyClassification);

    List<String> getAllDrugType();

    List<String> getAllEfficacyClassification();

    DrugRspVO getDrugInfor(String drug);

    BaseResponse<?> addStorageQuantity(String drug, String addStorageQuantity);

    PageRspBO<DrugRspVO> getAllDrug(DrugSearchReqVO reqVO);

    BaseResponse<?> updateDrug(DrugReqVO reqVO);

    BaseResponse<?> deleteDrug(String drugName);
}
