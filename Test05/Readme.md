项目主要包括     MainActivity，NewWebActivity，SeconActivity

布局文件layout：activity_main.xml activity_new_web.xml second.xml

MainActivity 有两个按钮：跳转到SeconActivity（显式调用），一个先输入网址后，点击按钮跳转到NewWebActivity（隐式调用）

NewWebActivity 以隐式调用的方式调用Intent，但不是输入网址

SeconActivity 也是隐式的调用Intent，用Bundle获取MainActivity中输入的URL在定位网页



![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Test05/pict/Intent_Test1.PNG)

![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Test05/pict/Intent_Test03.PNG)


![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Test05/pict/Intent_Test02.PNG)

