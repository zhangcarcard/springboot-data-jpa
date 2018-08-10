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
            <h2>1、添加协议</h2>
            <h2>2、添加分组</h2>
            <h2>3、添加转发策略</h2>

            <h2>名词解释：</h2>
            <h3>协议：使用HASH算法时，添加策略时需指定协议，HASH KEY通过协议从消息中解析出来。</h3>
            <h3>分组：后端服务器分组。</h3>
            <h3>转发策略：添加策略时，需指定对应的分组。</h3>
        </div>
    </div>
</div>
<#include "/tail.ftl">
</body>
</html>