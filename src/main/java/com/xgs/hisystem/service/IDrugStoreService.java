package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugReqVO;

/**
 * @author xgs
 * @date 2019-5-12
 * @description:
 */
public interface IDrugStoreService {

    BaseResponse<?> addNewDrug(DrugReqVO reqVO);
}
