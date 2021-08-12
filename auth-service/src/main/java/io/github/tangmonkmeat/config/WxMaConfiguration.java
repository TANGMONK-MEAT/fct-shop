package io.github.tangmonkmeat.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WxMaService 配置
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 下午10:37
 */
@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfiguration {

    private final WxMaProperties wxMaProperties;

    private static WxMaService wxMaService;


    @Autowired
    public WxMaConfiguration(WxMaProperties wxMaProperties){
        this.wxMaProperties = wxMaProperties;
    }

    public static WxMaService getWxMaService(){
        return wxMaService;
    }

    @Bean
    public Object services() {
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        BeanUtils.copyProperties(wxMaProperties, config);
        wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(config);
        return Boolean.TRUE;
    }
}
