package com.wuyan.vaccinereservation.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.wuyan.vaccinereservation.model.pojo.WxMaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class WxMaConfig {
    @Autowired
    private WxMaProperties wxMaProperties;

    /**
     * 初始化微信服务 会自动维护获取access_token
     */
    @Bean
    public WxMaService wxMaService() {
        log.info("============初始化微信小程序服务============");
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxMaProperties.getAppid());
        config.setSecret(wxMaProperties.getSecret());
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}