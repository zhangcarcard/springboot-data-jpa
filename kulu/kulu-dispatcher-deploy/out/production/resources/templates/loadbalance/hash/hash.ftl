<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <#import "/common.ftl" as common>
    <#include "/meta.ftl">
    <@common.title name="HASH转发配置" />
    <#include "/head.ftl">
</head>
<body>
<#include "/fileupload.ftl">

<div class="container">
    <div class="row">
        <@common.nav menu="loadbalance" submenu="hash" />
    </div>

    <div class="row">
        <#include "/statistics.ftl">
        <div class="col-sm-10">
            <div id="toolbar">
                <div class="btn-group">
                    <button id="btn_add" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>
                    <button id="btn_delete" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>
                    <button id="btn_batch" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量导入
                    </button>
                    <a type="button" class="btn btn-default" href="/file/template/hash.do">
                        <span class="glyphicon glyphicon-cloud-download" aria-hidden="true"></span>模板下载
                    </a>
                </div>
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
                        <input type="hidden" name="id" class="form-control" id="id">
                        <input type="hidden" name="version" class="form-control" id="version">
                    </div>
                    <div class="form-group">
                        <label for="name">名称</label>
                        <input type="text" name="name" class="form-control" id="name" placeholder="名称" required>
                    </div>
                    <div class="form-group">
                        <label for="key">KEY</label>
                        <input type="text" name="key" class="form-control" id="key" placeholder="根据协议解析出来的KEY" required>
                    </div>
                    <div class="form-group">
                        <label for="groupId">分组名称</label>
                        <select name="groupId" class="form-control" id="groupId" required>
                            <#if groups??>
                                <#list groups as group>
                                <option value="${(group.id)!}">${(group.name)!}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="eqpName">设备型号</label>
                        <select name="eqpName" class="form-control" id="eqpName" required>
                            <!--<#if eqpNames??>
                                <#list eqpNames as eqpName>
                                <option value="${(eqpName)!}">${(eqpName)!}</option>
                                </#list>
                            </#if>-->
                        </select>
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
        createBootstrapTable('/lb//hash.do',
                [
                    {checkbox: true, valign: 'middle'},
                    {title: '序号', valign: 'middle', formatter: function(value, row, index) {
                            return index + 1;
                        }},
                    {title: '名称', field: 'name', sortable: true, valign: 'middle'},
                    {title: 'KEY', field: 'key', sortable: true, valign: 'middle'},
                    {title: '分组名称', field: 'groupName', sortable: true, valign: 'middle', formatter: function (value, row, index) {
                            return row.group == undefined ? null : row.group.name;
                    }},
                    {title: '设备型号', field: 'eqpName', sortable: true, valign: 'middle'},
                    {title: '最后上线时间', field: 'gmtCreate', sortable: true, valign: 'middle'},
                    {title: '操作', valign: 'middle', events: operateEvents,
                        formatter: function(value, row, index) {
                            return [
                                '<button type="button" class="edit btn btn-success"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 编辑</button>'
                            ].join('');
                        }}
                ]);

        createFileInput('#pupload', '/file/template/hash.do', function (event, data, previewId, index) {
            if (data.response.success) {
                $('#table').bootstrapTable('refresh', {silent: true});
                $("#fileinputModal").modal("hide");
            } else {
                toastr.error(data.response.message);
            }
        });
        $("#btn_batch").click(function() {
            $("#pupload").fileinput('refresh');
            $("#fileinputModal").modal("show");
        });
        $("#btn_add").click(function() {
            formClear('#form');
            if ($('#groupId').val() != undefined) {
                $('#groupId').change();
            }
            $("#addModalLabel").text("新增");
            $('#addModal').modal();
        });
        $("#btn_delete").click(function() {
            var selections = $('#table').bootstrapTable('getSelections');
            if (selections.length == 0) {
                toastr.error("请选择删除的记录!");
            } else {
                swal({
                    title: '确认要删除吗?',
                    text: "删除后，不可恢复!",
                    icon: "warning",
                    dangerMode: true,
                    buttons: {
                        cancel: true,
                        confirm: true,
                    },
                }).then((willDelete) => {
                    if (willDelete) {
                        $.ajax({
                            url: "/lb/hash.do",
                            type: "DELETE",
                            data: JSON.stringify(selections),
                            contentType:"application/json; charset=UTF-8",
                            dataType:"json",
                            success: function(result) {
                                if (result.success) {
                                    toastr.success('记录已删除.');
                                    $('#table').bootstrapTable('refresh', {silent: true});
                                } else {
                                    toastr.error(result.message);
                                }
                            }
                        });
                    }
                }).catch(swal.noop);
            }
        });

        $("#form").submit(function(){
            var params = $(this).serialize();
            $.post("/lb/hash.do", params, function(result) {
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

        $('#groupId').change(function() {
            var params = {groupId: $('#groupId').val()};
            $.get("/backend/eqpNames.do", params, function(result) {
                if (result.success) {
                    var options = [];
                    $.each(result.data, function (i, item) {
                        var option = $('<option></option>');
                        option.attr('value', item);
                        option.text(item);
                        options.push(option);
                    });
                    $('#eqpName').html(options);
                } else {
                    toastr.error(result.message);
                }
            }, "json");
        });
    });

    window.operateEvents = {
        'click .edit': function (e, value, row, index) {
            formClear('#form');
            for (var v in row) {
                if (v == 'groupId' && row.group != undefined) {
                    $('#groupId').val(row.group.id);
                    $('#groupId').change();
                } else if (v == 'eqpName') {
                    $('#eqpName').val(row[v]);
                } else {
                    $('#' + v).val(row[v]);
                }
            }

            $('#addModalLabel').text('编辑');
            $('#addModal').modal();
        }
    }
</script>
</body>
</html>