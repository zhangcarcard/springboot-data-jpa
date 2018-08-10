<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <#import "/common.ftl" as common>
    <#include "/meta.ftl">
    <@common.title name="首页" />
    <#include "/head.ftl">
</head>
<body>
<div class="container">
    <div class="row">
        <@common.nav menu="index" submenu=""/>
    </div>
    <div class="row">
        <#include "/statistics.ftl">
        <div class="col-sm-10">
            <h2>1、添加设备类型</h2>
            <h2>2、添加服务协议</h2>
            <h2>3、添加分组</h2>
            <h2>4、添加转发策略</h2>

            <h2>名词解释：</h2>
            <h3>设备类型：转发服务接入端口</h3>
            <h3>服务协议：使用HASH算法时，添加策略时需指定协议，HASH KEY通过协议从消息中解析出来。</h3>
            <h3>分组：同一个系统可以有不同的分组。</h3>
            <h3>转发策略：添加策略时，需指定对应的协议和分组。</h3>
        </div>
    </div>
</div>
<#include "/tail.ftl">
</body>
</html>