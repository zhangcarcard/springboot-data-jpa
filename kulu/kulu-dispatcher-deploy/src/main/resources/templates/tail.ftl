<script type="text/javascript" src="/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/bootstrap-table/1.12.1/dist/bootstrap-table.js"></script>
<script type="text/javascript" src="/bootstrap-table/1.12.1/dist/locale/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="/bootstrap-fileinput/js/fileinput.min.js"></script>
<script type="text/javascript" src="/bootstrap-fileinput/js/locales/zh.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/bootstrap-treeview/1.2.0/bootstrap-treeview.min.js"></script>
<script type="text/javascript" src="/toastr/toastr.min.js"></script>
<script type="text/javascript" src="/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="/main.js"></script>
<script type="text/javascript">
    toastr.options = {
        closeButton: false,
        debug: false,
        progressBar: true,
        positionClass: "toast-center-center",
        onclick: null,
        showDuration: "300",
        hideDuration: "1000",
        timeOut: "1000",
        extendedTimeOut: "1000",
        showEasing: "swing",
        hideEasing: "linear",
        showMethod: "fadeIn",
        hideMethod: "fadeOut"
    };

    $(function() {
        var websocket = null;
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://" + window.location.host + "/statistics/connections");
        } else {
            console.log("当前浏览器不支持 websocket!");
        }

        //连接发生错误的回调方法
        websocket.onerror = function() {
            console.log("连接发生错误");
        }

        //连接成功建立的回调方法
        websocket.onopen = function() {
            console.log("连接建立成功")
        }

        //接收到消息的回调方法
        websocket.onmessage = function(event) {
            var result = JSON.parse(event.data);
            if (result.success) {
                $('#treeview1').treeview({data: result.data});
            } else {
                toastr.error(result.message);
            }
        }

        //连接关闭的回调方法
        websocket.onclose = function() {
            console.log("连接已关闭")
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function() {
            websocket.close();
        }
    });
</script>