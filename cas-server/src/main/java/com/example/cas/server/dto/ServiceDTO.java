package com.example.cas.server.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description service实体类
 * guide: https://apereo.github.io/cas/5.3.x/installation/Service-Management.html#service-management
 * @Author yempty
 * @Date 2020/8/30 21:02
 * @Version 1.0
 */
public class ServiceDTO {

    /**
     * 必填名称（255字符或更少）
     */
    @NotBlank(message = "名称必须填写")
    private String name;
    /**
     * 服务的可选自由文本描述。（个255字符以内）
     */
    private String description;
    /**
     * logo地址
     */
    private String logo;
    /**
     * 客户端系统的访问地址，不带协议，带端口
     */
    @NotBlank(message = "客户端系统的访问地址，不带协议，带端口(例：192.168.1.150:8080,www.baidu.com)")
    private String address;
    /**
     * 确定注册服务评估的相对顺序
     * 在两个服务URL表达式包含相同服务的情况下，此标志尤其重要。评估顺序确定首先评估哪个注册并充当内部排序因素
     */
    private Integer evaluationOrder;
    /**
     * 该服务接收注销请求的URL端点
     */
    @NotBlank(message = "该服务接收注销请求的URL端点")
    private String logoutUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEvaluationOrder() {
        return evaluationOrder;
    }

    public void setEvaluationOrder(Integer evaluationOrder) {
        this.evaluationOrder = evaluationOrder;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }
}
