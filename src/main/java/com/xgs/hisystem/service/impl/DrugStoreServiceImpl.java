package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.DrugEntity;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.drugStorage.DrugReqVO;
import com.xgs.hisystem.repository.IDrugRepository;
import com.xgs.hisystem.service.IDrugStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xgs
 * @date 2019-5-12
 * @description:
 */
@Service
public class DrugStoreServiceImpl implements IDrugStoreService {

    @Autowired
    private IDrugRepository iDrugRepository;

    @Override
    public BaseResponse<?> addNewDrug(DrugReqVO reqVO) {

        DrugEntity drug = new DrugEntity();
        drug.setName(reqVO.getName());
        drug.setDrugType(reqVO.getDrugType());
        drug.setEfficacyClassification(reqVO.getEfficacyClassification());
        drug.setLimitStatus(reqVO.getLimitStatus());
        drug.setManufacturer(reqVO.getManufacturer());
        drug.setSpecification(reqVO.getSpecification());
        drug.setUnit(reqVO.getUnit());
        drug.setPrice(reqVO.getPrice());
        drug.setWholesalePrice(reqVO.getWholesalePrice());

        try {
            iDrugRepository.saveAndFlush(drug);
            return BaseResponse.errormsg(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.errormsg(Contants.user.FAIL);
        }
    }
}
