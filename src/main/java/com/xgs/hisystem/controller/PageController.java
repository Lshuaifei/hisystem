package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.applyRspVO;
import com.xgs.hisystem.service.IAdminService;
import com.xgs.hisystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
@Controller
public class PageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IAdminService iAdminService;

    @RequestMapping(value = "/")
    public String login() {
        return "login";
    }

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/main")
    public String main(Model model) {
        return "main";
    }

    /**
     * 邮件发送成功
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/fmail")
    public String fmail(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        model.addAttribute("email", email);
        return "email/fmail";
    }

    /**
     * 激活用户状态
     *
     * @param email
     * @param validateCode
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/activation", method = RequestMethod.GET)
    public String activation(String email, String validateCode, Model model) throws ParseException {

        BaseResponse baseResponse = iUserService.activation(email, validateCode);
        if (baseResponse.getStatus().equals(1)) {
            return "email/dmail";
        } else {
            return "error";
        }
    }

    /**
     * 账号设置
     *
     * @return
     */
    @RequestMapping(value = "/accountset")
    public String AccountSet() {
        return "accountset";
    }

    /**
     * 角色审核
     *
     * @return
     */
    @RequestMapping("/toApply")
    public String toApply() {
        return "/admin/roleApply";
    }

    /**
     * 导航栏通知数量显示，角色审核
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/getRoleNotice")
    public String getRoleNotice(Model model) {

        List<applyRspVO> applyRspList = iAdminService.getRoleNotice();

        model.addAttribute("applyRspList", applyRspList);
        return "common/common_head::notice";

    }

    @RequestMapping(value = "/toUserinfo")
    public String toUserinfo() {
        return "userInfo";
    }


}
