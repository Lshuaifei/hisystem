package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.*;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
public interface IAdminService {

    BaseResponse<?> createRole(RoleVO roleVO);

    BaseResponse<?> addRole(AddRoleVO addRoleVO);

    PageRspBO<applyRspVO> getRoleApply(BasePageReqVO reqVO);

    BaseResponse<?> saveUserAndSendEmailTemp(UserRegisterReqVO reqVO);

    List<applyRspVO> getRoleNotice();

    BaseResponse<?> changeRoleStatus(String status, String email);

    BaseResponse<?> addAnnouncement(AnnouncementVO reqVO);

    PageRspBO<AnnouncementVO> getAnnouncement(BasePageReqVO reqVO);

    BaseResponse<?> changeAnnouncement(AnnouncementVO announcementVO);

    BaseResponse<?> deleteAnnouncement(String id);

    BaseResponse<?> add_Announcement(String id);

    BaseResponse<?> sub_Announcement(String id);
}
