<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<input type="button" id="btn" value="获取数据"/>
<table width="80%" align="center">
    <tr>
        <td>姓名</td>
        <td>年龄</td>
        <td>性别</td>
    </tr>
    <tbody id="content">
    </tbody>
</table>

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<%--<script src="${pageContext.request.contextPath}/statics/js/jquery-3.1.1.min.js"></script>--%>
<script>

    $(function () {
        $("#btn").click(function () {
            console.log("111111");
            $.post("${pageContext.request.contextPath}/a2", function (data) {
                console.log(data)
                 var html = "";
                data = JSON.parse(data); //需要加上这一步，否则显示的是undefined,因为上面返回的data是一个字符串，需要转换为json对象
                 for (var i = 0; i < data.length; i++) {
                     html += "<tr>" +
                         "<td>" + data[i].name + "</td>" +
                         "<td>" + data[i].age + "</td>" +
                         "<td>" + data[i].sex + "</td>" +
                         "</tr>"
                 }
                 $("#content").html(html);
            });
        })
    })
</script>
</body>
</html>