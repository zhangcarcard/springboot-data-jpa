<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <#import "/common.ftl" as common>
    <#include "/meta.ftl">
    <@common.title name="分组列表" />
    <#include "/head.ftl">
</head>
<body>
<#include "/fileupload.ftl">
<div class="container">
    <div class="row">
        <@common.nav menu="group" submenu="group" />
    </div>

    <div class="row">
        <#include "/statistics.ftl">
        <div class="col-sm-10">
            <div id="toolbar" class="btn-group">
                <button id="btn_add" type="button" class="btn btn-default">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                </button>
                <button id="btn_delete" type="button" class="btn btn-default">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                </button>
            </div>
            <table id="table"></table>
        </div>
    </div>
</div>

<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addModalLabel">新增</h4>
            </div>
            <div class="modal-body">
                <form id="form">
                    <div class="form-group">
                        <label for="name">名称</label>
                        <input type="text" name="name" class="form-control" id="name" placeholder="分组名称" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="comment">备注</label>
                        <input type="text" name="comment" class="form-control" id="comment" placeholder="备注">
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addServerModal" tabindex="-1" role="dialog" aria-labelledby="addServerModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addServerModalLabel">新增</h4>
            </div>
            <div class="modal-body">
                <form id="server-form">
                    <div class="form-group">
                        <input type="hidden" name="id" class="form-control" id="id">
                        <input type="hidden" name="version" class="form-control" id="version">
                        <input type="hidden" name="groupId" class="form-control" id="groupId">
                    </div>
                    <div class="form-group">
                        <label for="eqpName">设备型号</label>
                        <input type="text" name="eqpName" class="form-control" id="eqpName" placeholder="设备型号" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="protocalName">协议名称</label>
                        <select name="protocalName" class="form-control" id="protocalName" required>
                            <#if protocals??>
                                <#list protocals as protocal>
                                <option value="${(protocal.name)!}">${(protocal.name)!}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="ip">IP</label>
                        <input type="text" name="ip" class="form-control" id="ip" placeholder="IP或者域名" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="port">PORT</label>
                        <input type="number" name="port" class="form-control" id="port" placeholder="端口号" required>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<#include "/tail.ftl">
<script>
    $(function() {
        createBootstrapTable('/group/group.do',
                [
                    {checkbox: true, valign: 'middle'},
                    {title: '序号', width: '5%', valign: 'middle', formatter: function(value, row, index) {
                        return index + 1;
                    }},
                    {title: '名称', field: 'name', sortable: true, valign: 'middle'},
                    {title: '创建时间', field: 'gmtCreate', sortable: true, valign: 'middle'},
                    {title: '备注', field: 'comment', valign: 'middle'},
                    {title: '操作', width: '15%', valign: 'middle', events: operateEvents,
                        formatter: function(value, row, index) {
                        return [
                            '<button type="button" class="server-add btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增服务</button>'
                        ].join('');
                    }}
                ], true, function (index, row, $detail) {
                    var groupId = row.id;
                    var cur_table = $detail.html('<table id="child_table"></table>').find('table');
                    $(cur_table).bootstrapTable({
                        url: '/backend/backend.do?groupId=' + groupId,
                        striped: true,
                        cache: false,
                        pagination: true,
                        sidePagination: 'server',
                        pageSize: 20,
                        uniqueId: "id",
                        queryParams: function(params) {
                            params.sortName = params.sort;
                            params.sort = null;
                            return params;
                        },
                        columns: [
                            {title: '序号', width: '5%', valign: 'middle', formatter: function(value, row, index) {
                                    return index + 1;
                            }},
                            {title: '设备型号', field: 'eqpName', sortable: true, valign: 'middle'},
                            {title: '协议名称', field: 'protocalName', sortable: true, valign: 'middle'},
                            {title: 'IP', field: 'ip', sortable: true, valign: 'middle'},
                            {title: 'PORT', field: 'port', sortable: true, valign: 'middle'},
                            {title: '创建时间', field: 'gmtCreate', sortable: true, valign: 'middle'},
                            {title: '修改时间', field: 'gmtModified', sortable: true, valign: 'middle'},
                            {title: '操作', width: '15%', valign: 'middle', events: operateEvents,
                                formatter: function(value, row, index) {
                                    return [
                                        '<button type="button" class="server-edit btn btn-success"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span>编辑</button>',
                                        ' <button type="button" class="server-remove btn btn-danger"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>'
                                    ].join('');
                            }}
                        ]
                    });
                });

        $("#btn_add").click(function() {
            formClear('#form');
            $("#addModalLabel").text("新增");
            $('#addModal').modal();
        });
        $("#btn_delete").click(function() {
            removeRows('#table', '/group/group.do', null, '分组下的转发策略和服务器也会同时删除!');
        });
        $("#form").submit(function(){
            var params = $(this).serialize();
            $.post("/group/group.do", params, function(result) {
                if (result.success) {
                    toastr.success('保存成功.');
                    $('#table').bootstrapTable('refresh', {silent: true});
                    $('#addModal').modal('hide');
                } else {
                    toastr.error(result.message);
                }
            }, "json");
            return false;
        });

        $("#server-form").submit(function(){
            var params = $(this).serialize();
            $.post("/backend/backend.do", params, function(result) {
                if (result.success) {
                    toastr.success('保存成功.');
                    $('#child_table').bootstrapTable('refresh', {silent: true});
                    $('#addServerModal').modal('hide');
                } else {
                    toastr.error(result.message);
                }
            }, "json");
            return false;
        });
    });

    window.operateEvents = {
        'click .server-edit': function (e, value, row, index) {
            formClear('#server-form');
            for (var v in row) {
                $('#' + v).val(row[v]);
            }

            $('#addServerModalLabel').text('编辑');
            $('#addServerModal').modal();
        },
        'click .server-add': function(e, value, row, index) {
            formClear('#server-form');
            $('#groupId').val(row.id);
            $("#addServerModalLabel").text("新增");
            $('#addServerModal').modal();
        },
        'click .server-remove': function (e, value, row, index) {
            var rs = [];
            rs.push(row);
            removeRows('#child_table', '/backend/backend.do', rs)
        }
    }

    function removeRows(table, url, rows, text) {
        rows = rows || [];
        text = text || '删除后，不可恢复!';
        var selections = $(table).bootstrapTable('getSelections');
        if (selections.length == 0 && rows.length == 0) {
            toastr.error("请选择删除的记录!");
        } else {
            swal({
                title: '确认要删除吗?',
                text: text,
                icon: "warning",
                dangerMode: true,
                buttons: {
                    cancel: true,
                    confirm: true,
                },
            }).then((willDelete) => {
                if (willDelete) {
                    for (var i = 0, length = selections.length; i < length; i++) {
                        var row = selections[i];
                        rows.push(row);
                    }

                    $.ajax({
                        url: url,
                        type: "DELETE",
                        data: JSON.stringify(rows),
                        contentType:"application/json; charset=UTF-8",
                        dataType:"json",
                        success: function(result) {
                            if (result.success) {
                                toastr.success('记录已删除.');
                                $(table).bootstrapTable('refresh', {silent: true});
                            } else {
                                toastr.error(result.message);
                            }
                        }
                    });
                }
            }).catch(swal.noop);
        }
    }
</script>
</body>
</html>