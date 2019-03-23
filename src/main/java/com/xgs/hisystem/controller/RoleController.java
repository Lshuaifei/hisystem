package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.RoleVO;
import com.xgs.hisystem.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
@Controller
public class RoleController {

    @Autowired
    private IRoleService iRoleService;

    @RequestMapping(value = "/createRole",method = RequestMethod.POST)
    @ResponseBody
    public String createRole(@RequestBody RoleVO roleVO){

        BaseResponse baseResponse=iRoleService.createRole(roleVO);
        return baseResponse.getMessage();

    }
}
