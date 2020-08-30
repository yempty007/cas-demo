package com.example.cas.server.entity;

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
     *
     */
    private String logo;
    /**
     * 描述逻辑服务的必需正则表达式
     * 逻辑服务定义一个或多个服务所在的一个或多个URL
     */
    private String serviceId;
    /**
     * 服务请求票证时可以用于自定义CAS UI的可选主题名称
     */
    private String theme;
    /**
     * 确定服务是否能够代理身份验证
     */
    private String proxyPolicy;
    /**
     * 确定注册服务评估的相对顺序
     * 在两个服务URL表达式包含相同服务的情况下，此标志尤其重要。评估顺序确定首先评估哪个注册并充当内部排序因素
     */
    private String evaluationOrder;
    /**
     * 一组身份验证处理程序名称，必须成功地对凭据进行身份验证才能访问服务
     * 如果定义，则仅选择选定的所需处理程序以响应来自此注册服务的身份验证请求。
     */
    private String requiredHandlers;
    /**
     * 可以将描述属性集的策略以及清除某些内容所需的任何其他过滤逻辑发布到应用程序
     */
    private String attributeReleasePolicy;

    /**
     * 定义启动注销协议后应如何处理此服务。可接受值LogoutType.BACK_CHANNEL，LogoutType.FRONT_CHANNEL或LogoutType.NONE
     */
    private String logoutType;
    /**
     * 定义CAS应如何响应对此服务的请求
     */
    private String responseType;
    /**
     * 指示应该将什么值作为“用户名”的提供者配置应发送回应用程序
     */
    private String usernameAttributeProvider;
    /**
     * 概述和访问此服务规则的策略配置
     * 它描述了服务是被允许，被授权参与SSO，还是基于特定属性定义的角色（又称为RBAC）从CAS角度被授予访问权限
     */
    private String accessStrategy;
    /**
     * 与该服务关联的公共密钥，用于通过加密CAS验证协议响应中的某些元素和属性（例如PGT或凭据）来授权请求
     */
    private String publicKey;
    /**
     * 该服务接收注销请求的URL端点
     */
    private String logoutUrl;
    /**
     * 以键/值对的形式与此服务关联的额外元数据。这用于将自定义字段注入服务定义中，以后由扩展模块使用，以基于每个服务定义其他行为
     */
    private String properties;
    /**
     * 描述此服务认证（通常是多因素认证）所需的配置的策略
     */
    private String multifactorPolicy;
    /**
     * 指定与拥有该应用程序的服务相关联的联系人集合
     */
    private String contacts;

}
