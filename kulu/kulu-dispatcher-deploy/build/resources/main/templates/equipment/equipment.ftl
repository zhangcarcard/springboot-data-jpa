<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <#import "/common.ftl" as common>
    <#include "/meta.ftl">
    <@common.title name="设备列表" />
    <#include "/head.ftl">
</head>
<body>
<#include "/fileupload.ftl">
<div class="container">
    <@common.nav menu="equipment" submenu="equipment" />

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
                        <input type="text" name="name" class="form-control" id="name" placeholder="设备名称" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="port">转发服务端口</label>
                        <input type="number" name="port" class="form-control" id="port" placeholder="转发服务端口" required>
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

<#include "/tail.ftl">
<script>
    $(function() {
        createBootstrapTable('/equipment/equipment.do',
                [
                    {checkbox: true, valign: 'middle'},
                    {title: '序号', width: '5%', valign: 'middle', formatter: function(value, row, index) {
                            return index + 1;
                        }},
                    {title: '名称', field: 'name', sortable: true, valign: 'middle'},
                    {title: '转发服务端口', field: 'port', sortable: true, valign: 'middle'},
                    {title: '创建时间', field: 'gmtCreate', sortable: true, valign: 'middle'},
                    {title: '修改时间', field: 'gmtModified', sortable: true, valign: 'middle'},
                    {title: '备注', field: 'comment', valign: 'middle'},
                    {title: '操作', width: '15%', valign: 'middle', events: operateEvents,
                        formatter: function(value, row, index) {
                            return [
                                '<button type="button" class="edit btn btn-success btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 编辑</button>'
                            ].join('');
                        }}
                ]);

        $("#btn_add").click(function() {
            formClear('#form');
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
                        var equipments = [];
                        for (var i = 0, length = selections.length; i < length; i++) {
                            var row = selections[i];
                            equipments.push(row);
                        }

                        $.ajax({
                            url: "/equipment/equipment.do",
                            type: "DELETE",
                            data: JSON.stringify(equipments),
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
            $.post("/equipment/equipment.do", params, function(result) {
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
    });

    window.operateEvents = {
        'click .edit': function (e, value, row, index) {
            formClear('#form');
            for (var v in row) {
                $('#' + v).val(row[v]);
            }

            $('#addModalLabel').text('编辑');
            $('#addModal').modal();
        }
    }

    function formClear(form) {
        $(form)[0].reset();
        $('#id').val('');
        $('#version').val('');
    }
</script>
</body>
</html>