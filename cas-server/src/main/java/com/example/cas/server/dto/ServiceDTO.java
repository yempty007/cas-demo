package com.example.cas.server.dto;

/**
 * @Description service实体类
 * guide: https://apereo.github.io/cas/5.3.x/installation/Service-Management.html#service-management
 * @Author yempty
 * @Date 2020/8/30 21:02
 * @Version 1.0
 */
public class ServiceDTO {

    /**
     * 必需的唯一标识符。该值必须是有效的数值
     */
    private String id;
    /**
     * 必填名称（255字符或更少）
     */
    private String name;
    /**
     * 服务的可选自由文本描述。（个255字符以内）
     */
    private String description;
    /**
     * 服务信息指南的可选自由文本链接
     */
    private String informationUrl;
    /**
     * 可选的自由文本链接，指向服务隐私政策
     */
    private String privacyUrl;
    /**
     * logo地址
     */
    private String logo;
    /**
     * 描述逻辑服务的必需正则表达式
     * 逻辑服务定义一个或多个服务所在的一个或多个URL
     */
    private String serviceId;
    /**
     * 确定注册服务评估的相对顺序
     * 在两个服务URL表达式包含相同服务的情况下，此标志尤其重要。评估顺序确定首先评估哪个注册并充当内部排序因素
     */
    private Integer evaluationOrder;
    /**
     * 定义启动注销协议后应如何处理此服务。可接受值LogoutType.BACK_CHANNEL，LogoutType.FRONT_CHANNEL或LogoutType.NONE
     */
    private String logoutType;
    /**
     * 定义CAS应如何响应对此服务的请求
     */
    private String responseType;
    /**
     * 与该服务关联的公共密钥，用于通过加密CAS验证协议响应中的某些元素和属性（例如PGT或凭据）来授权请求
     */
    private String publicKey;
    /**
     * 该服务接收注销请求的URL端点
     */
    private String logoutUrl;
    /**
     * 指定与拥有该应用程序的服务相关联的联系人集合
     */
    private String contacts;

}
