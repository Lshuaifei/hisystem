package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.vo.AccountRoleVO;
import com.xgs.hisystem.pojo.vo.AnnouncementVO;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.applyRspVO;
import com.xgs.hisystem.pojo.vo.outpatient.OutpatientQueueLaterRspVO;
import com.xgs.hisystem.pojo.vo.outpatient.OutpatientQueueNormalRspVO;
import com.xgs.hisystem.service.IAdminService;
import com.xgs.hisystem.service.IOutpatientService;
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
    @Autowired
    private IOutpatientService iOutpatientService;

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


    @RequestMapping(value = "/accountset")
    public String AccountSet() {
        return "accountset";
    }


    @RequestMapping("/toApply")
    public String toApply() {
        return "/admin/roleApply";
    }

    @RequestMapping(value = "/register")
    public String getUserID() {
        return "register/register";
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


    @RequestMapping(value = "/announcement")
    public String announcement() {
        return "admin/announcement";
    }

    /**
     * 首页公告显示
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/annDisplay")
    public String annDisplay(Model model) {

        List<AnnouncementVO> annList = iUserService.annDisplay();
        model.addAttribute("annList", annList);

        return "main::ann";
    }

    /**
     * 获取当前用户所有角色
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAccountRole")
    public String getAccountRole(Model model) {

        List<String> accountRoleList = iUserService.getAccountRole();
        model.addAttribute("accountRole", accountRoleList);

        return "accountset::accountRole";
    }

    @RequestMapping(value = "/registerRecord")
    public String registerRecord() {
        return "register/registerRecord";
    }

    @RequestMapping(value = "/outpatient")
    public String outpatient() {
        return "outpatient/outpatient";
    }


    /**
     * 门诊医生当天所有普通病人
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAllPatientNormal")
    public String getAllPatientNormal(Model model) {

        List<OutpatientQueueNormalRspVO> outpatientQueueNormalList = iOutpatientService.getAllPatientNormal();
        model.addAttribute("outpatientQueueNormalList", outpatientQueueNormalList);

        return "outpatient/outpatient::patientNormal";
    }

    /**
     * 稍后处理病人
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAllPatientLater")
    public String getAllPatientLater(Model model) {

        List<OutpatientQueueLaterRspVO> outpatientQueueLaterList = iOutpatientService.getAllPatientLater();
        model.addAttribute("outpatientQueueLaterList", outpatientQueueLaterList);

        return "outpatient/outpatient::patientLater";
    }

    @RequestMapping(value = "/storageManage")
    public String storageManage() {

        return "drugStore/storageManage";
    }

    @RequestMapping(value = "/toll")
    public String toll() {

        return "toll/toll";
    }

    @RequestMapping(value = "/takingdrug")
    public String takingdrug() {

        return "takingdrug/takingdrug";
    }


    @RequestMapping(value = "/medicalExamination")
    public String medicalExamination() {

        return "medicalExamination/medicalExamination";
    }

    @RequestMapping(value = "/drugManage")
    public String drugManage() {

        return "drugStore/drugManage";
    }


    @RequestMapping(value = "/medicalRecord")
    public String medicalRecord() {

        return "outpatient/medicalRecord";
    }
}

