package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.ValidationResultBO;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugReqVO;
import com.xgs.hisystem.service.IDrugStoreService;
import com.xgs.hisystem.util.ParamsValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xgs
 * @date 2019-5-12
 * @description:
 */
@RestController
@RequestMapping(value = "/drugstore")
public class DrugStoreController {

    @Autowired
    private IDrugStoreService iDrugStoreService;

    @RequestMapping(value = "/addNewDrug")
    public String addNewDrug(@RequestBody DrugReqVO reqVO) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }

        BaseResponse baseResponse = iDrugStoreService.addNewDrug(reqVO);
        return baseResponse.getMessage();
    }
}
