这是第三个Android项目：UI组件

lion对应自定义AlertDialog 
MainActivity负责按键时间，填充组件的内容
login.xml是菜单的布局文件



![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Test03/pict/lion.PNG)



ListView 对应创建上下文操作模式(ActionMode)的上下文菜单

	在array_item.xml 设定列表的样式。
	在activity_main.xml中需要设定一个ListView，之后改组件使用array_item.xml布局文件
	MainActivity中需要设定SimpleList以及HashMap对象充填；还要重写OnCreate航速和设计点击事件、长按时事件

项目：ListView-2为ListView新增长按时事件处理


![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Test03/pict/listView01.PNG)
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Test03/pict/listView02.PNG)


XMLMean2 使用XML定义菜单

context.xml，activity_menu_main.xml分别对应一级菜单，耳机菜单

![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Test03/pict/XMLMean01.PNG)
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Test03/pict/XMLMean02.PNG)




SimpleAdapter 利用SimpleAdapter实现复杂布局

MainActivity负责充填元素和点击事件
simple_item.xml是没一个item布局文件


![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Test03/pict/SimpeViewPNG.PNG)