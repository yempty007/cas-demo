package com.example.cas.server.controller;

import com.example.cas.server.entity.ReturnMessage;
import org.apereo.cas.services.RegexRegisteredService;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
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
    public Object addClient(@PathVariable("protocol") String protocol,
                            @PathVariable("serviceId") String serviceId,
                            @PathVariable("id") int id) {
        ReturnMessage returnMessage = new ReturnMessage();
        String url = protocol + "://" + serviceId;
        RegisteredService svc = servicesManager.findServiceBy(url);
        if (svc != null) {
            logger.error("注册service异常,此地址已注册");
            returnMessage.setCode(500);
            returnMessage.setMessage("添加失败");
        } else {
            try {
                String realServiceId = "^" + url + ".*";
                RegexRegisteredService service = new RegexRegisteredService();
                service.setServiceId(realServiceId);
                service.setId(id);
                service.setName(url);
                // 这个是为了单点登出
                service.setLogoutUrl(new URL(url));
                servicesManager.save(service);
                // 执行load让他生效
                servicesManager.load();

                returnMessage.setCode(200);
                returnMessage.setMessage("添加成功");
            } catch (Exception e) {
                logger.error("注册service异常", e);
                returnMessage.setCode(500);
                returnMessage.setMessage("添加失败");
            }
        }
        return returnMessage;
    }

    /**
     * 删除service
     *
     * @param serviceId 服务ID
     * @return
     */
    @DeleteMapping("/{serviceId}")
    public Object deleteClient(@PathVariable("serviceId") String serviceId) {
        ReturnMessage returnMessage = new ReturnMessage();

        RegisteredService service = servicesManager.findServiceBy(serviceId);
        if (service != null) {
            try {
                servicesManager.delete(service);
                // 执行load生效
                servicesManager.load();

                returnMessage.setCode(200);
                returnMessage.setMessage("删除成功");
                return returnMessage;
            } catch (Exception e) {
                logger.error("删除service异常", e);
                returnMessage.setCode(500);
                returnMessage.setMessage("删除失败");
                return returnMessage;
            }
        } else {
            returnMessage.setCode(500);
            returnMessage.setMessage("删除失败,服务不存在");
            return returnMessage;
        }
    }

}
