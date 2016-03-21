<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>统计图</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<!-- Step1 引入EChart.js -->
	<script type="text/javascript" src="js/echarts.min.js"></script>
  </head>
  
  <body>
  <!-- step2 建立一个图表容器 -->
    <div id="main" style="width: auto;height:500px;"></div>
    <!-- step3 编写js代码 -->
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

		var x_data = new Array();//x轴数据
		var y_data = new Array();//y轴数据
		<%
		//从后台读取数据，复制给x_data和有y_data
	    Map<String,Integer> data_map = (Map<String,Integer>)request.getSession().getAttribute("data");
		Set set = data_map.entrySet();        
		Iterator i = set.iterator(); 
		int index = 0;       
		while(i.hasNext()){     
		     Map.Entry<String, Integer> entry1=(Map.Entry<String, Integer>)i.next();       
		%>
			x_data[<%=index%>] = '<%=entry1.getKey()%>';
			y_data[<%=index%>] = <%=entry1.getValue().intValue()%>;
		<%
			index++;
		}
		%>
		
        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '词频统计图'
            },
            tooltip: {},
            legend: {
                data:['词频']
            },
            xAxis: {
                data: x_data
            },
            yAxis: {},
            series: [{
                name: '词频',
                type: 'bar',
                data: y_data
            }]
        };
        myChart.setOption(option);
    </script>
  </body>
</html>
