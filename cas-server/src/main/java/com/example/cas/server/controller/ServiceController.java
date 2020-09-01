package com.example.cas.server.controller;

import cn.hutool.core.util.IdUtil;
import com.example.cas.server.common.result.Result;
import com.example.cas.server.dto.ServiceDTO;
import org.apereo.cas.services.RegexRegisteredService;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ReturnAllAttributeReleasePolicy;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URL;
import java.util.OptionalInt;

/**
 * @Description 服务管理控制器
 * @Author yempty
 * @Date 2020/8/26 13:24
 */
@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    /**
     * 注册service
     *
     * @param serviceDTO 服务DTO
     * @return
     */
    @PostMapping
    public Result<String> addClient(@RequestBody @Valid ServiceDTO serviceDTO) {
        Result<String> result = null;
        RegisteredService registeredService = servicesManager.findServiceBy(serviceDTO.getAddress());
        if (registeredService != null) {
            logger.error("注册service失败,此地址[{}]已注册", serviceDTO.getAddress());
            result = Result.error("添加服务失败!此地址已注册!");
        } else {
            try {
                RegexRegisteredService service = new RegexRegisteredService();
                ReturnAllAttributeReleasePolicy policy = new ReturnAllAttributeReleasePolicy();
                service.setAttributeReleasePolicy(policy);

                long id = IdUtil.createSnowflake(0, 0).nextId();
                service.setId(id);

                String serviceId = "^(https|http)://" + serviceDTO.getAddress() + "/.*";
                service.setServiceId(serviceId);

                service.setName(serviceDTO.getName());
                service.setDescription(serviceDTO.getDescription());
                service.setLogo(serviceDTO.getLogo());
                service.setEvaluationOrder(OptionalInt.of(serviceDTO.getEvaluationOrder()).orElse(1));
                service.setLogoutUrl(new URL(serviceDTO.getLogoutUrl()));

                servicesManager.save(service);

                // 执行load立即生效
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
