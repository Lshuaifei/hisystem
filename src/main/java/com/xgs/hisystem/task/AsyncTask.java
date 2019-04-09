package com.xgs.hisystem.task;

import com.alibaba.fastjson.JSON;
import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.entity.LoginInforEntity;
import com.xgs.hisystem.pojo.entity.UserEntity;
import com.xgs.hisystem.pojo.vo.getAddress.GetAddressRspVO;
import com.xgs.hisystem.repository.ILoginInforRepository;
import com.xgs.hisystem.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/25
 */
@Component
public class AsyncTask {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private ILoginInforRepository iLoginInforRepository;

    /**
     * 保存登录信息 异步线程
     *
     * @param ip
     * @param broswer
     * @return
     */
    @Async("myTaskAsyncPool")
    public void saveLoginInfor(String ip, String broswer, String email) {

        UserEntity user = iUserRepository.findByEmail(email);
        String userId = user.getId();
        LoginInforEntity CheckUserInformation = iLoginInforRepository.findByLoginIpAndLoginBroswerAndUserId(ip, broswer, userId);


        if (CheckUserInformation == null) {

            LoginInforEntity userInformation = new LoginInforEntity();
            userInformation.setLoginIp(ip);
            userInformation.setLoginBroswer(broswer);
            userInformation.setUser(user);
            userInformation.setDescription(email);

            RestTemplate restTemplate = new RestTemplate();

            //调百度地图api，通过ip获取地理位置
            String url = Contants.url.BAIDU_URL + ip;
            String result = restTemplate.getForObject(url, String.class);

            if (!StringUtils.isEmpty(result)) {

                GetAddressRspVO addressRspVO = JSON.parseObject(result, GetAddressRspVO.class);
                String loginAddress = addressRspVO.getContent().getAddress();

                userInformation.setLoginAddress(loginAddress);
            }
            iLoginInforRepository.saveAndFlush(userInformation);
        }
    }

    @Async("myTaskAsyncPool")
    public void getRoleApply() {


    }
}
