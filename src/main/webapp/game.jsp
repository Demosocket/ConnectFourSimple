<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ConnectGame</title>
    <link rel="stylesheet" href="css/elements.css" >
    <style>
        img {
            width: 55px;
            height: 55px;
            margin-top: 4px;
        }
        <%=request.getAttribute("tableStyle")%>
    </style>
</head>
<body style="background: gainsboro">
<h2 style="text-align: center">Connect Four</h2>
<h2 style="text-align:center"> <%=request.getAttribute("winner")%> </h2>
<%--Board--%>
<div> <%= request.getAttribute("boardView")%> </div>
<br>
<%--button "Start Over"--%>
<form action="game" method="GET">
    <button class="button" name="play" value="-1">Start Over</button>
</form>
</body>
</html>