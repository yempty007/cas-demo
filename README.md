# cas-demo

需要在 hosts 中增加映射,避免cookie共用
```
127.0.0.1 cas.server.com  
127.0.0.1 a.casclient.com  
127.0.0.1 b.casclient.com
127.0.0.1 c.casclient.com
```
---
安装 jar 包 (maven 会下载失败)

`
mvn install:install-file -Dfile=lib/xmlsectool-2.0.0-beta-2.jar -DgroupId=net.shibboleth.tool -DartifactId=xmlsectool -Dversion=2.0.0 -Dpackaging=jar
`
---
创建cas-sso数据库，运行 `doc/init.sql`

---
若启动cas-server后，出现表不存在的错误，根据连接的数据库版本及引擎修改cas.serviceRegistry.jpa.dialect的值
