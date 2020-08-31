package com.example.cas.server.controller;

import com.example.cas.server.common.result.Result;
import org.apereo.cas.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

/**
 * @Description ServiceController
 * @Author yempty
 * @Date 2020/8/26 13:24
 */
@RestController
@RequestMapping("services")
public class ServiceController {

    private Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    /**
     * 注册service
     *
     * @param serviceId 服务ID
     * @param id        序号
     * @return
     */
    @PostMapping("/{protocol}/{serviceId}/{id}")
    public Result<String> addClient(@PathVariable("protocol") String protocol,
                                    @PathVariable("serviceId") String serviceId,
                                    @PathVariable("id") int id) {
        Result<String> result = null;
        String url = protocol + "://" + serviceId;
        RegisteredService svc = servicesManager.findServiceBy(url);
        if (svc != null) {
            logger.error("注册service异常,此地址已注册");
            result = Result.error("添加服务失败!此地址已注册!");
        } else {
            try {
                String realServiceId = "^" + url + ".*";
                RegexRegisteredService service = new RegexRegisteredService();

                ReturnAllAttributeReleasePolicy policy = new ReturnAllAttributeReleasePolicy();
                service.setAttributeReleasePolicy(policy);
                // 允许返回字段
                // ReturnAllowedAttributeReleasePolicy policy = new ReturnAllowedAttributeReleasePolicy();
                // List<String> attributes = new ArrayList<>();
                // attributes.add("id");
                // attributes.add("create_time");
                // policy.setAllowedAttributes(attributes);
                RegisteredServicePublicKey publicKey = new RegisteredServicePublicKeyImpl("E:\\public.txt", "RSA");
                service.setPublicKey(publicKey);
                service.setAttributeReleasePolicy(policy);
                service.setServiceId(realServiceId);
                service.setId(id);
                service.setName(url);
                // 这个是为了单点登出
                service.setLogoutUrl(new URL(url));
                servicesManager.save(service);
                // 执行load让他生效
                servicesManager.load();

                result = Result.okMsg("添加服务成功!");
            } catch (Exception e) {
                logger.error("注册service异常", e);
                result = Result.error("添加服务失败!");
            }
        }
        return result;
    }

    /**
     * 删除service
     *
     * @param serviceId 服务ID
     * @return
     */
    @DeleteMapping("/{serviceId}")
    public Result<String> deleteClient(@PathVariable("serviceId") String serviceId) {

        Result<String> result = null;

        RegisteredService service = servicesManager.findServiceBy(serviceId);
        if (service != null) {
            try {
                servicesManager.delete(service);
                // 执行load生效
                servicesManager.load();

                result = Result.error("删除service成功!");
            } catch (Exception e) {
                logger.error("删除service异常", e);
                result = Result.error("删除service异常!");
            }
        } else {
            result = Result.error("删除service失败!service不存在!");
        }
        return result;
    }

}
