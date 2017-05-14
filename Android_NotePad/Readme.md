							android项目-笔记本应用NotePad


目录：
	项目简介
	代码文件描述
	基本功能
	扩展功能介绍
	运行示范及截图



文件描述：
NotePad	契约类
NotesList	程序入口
NotePadProvider	数据库访问类
TitleEditor	编辑标题
NoteEditor	Note内容编辑类
NotesLiveFolder	
GetTime	获取当前时间


基本功能：
	新建Note
	编辑Note内容
	编辑Note标题
	删除Note


功能扩展：
1.添加时间戳：
NotePadProvider修正：
		insert()函数中，每次新插入数据时，通过调用GetTime.Get_Now_Time_Long()函数获取当前时间；然后再待插入的Values中将CREATE_DATE，MODIFICATION_DATE两列的数值替换成当前时间。
		update()函数中，每次更新时，更新的Values中的MODIFICATION_DATE数据也会一并更新到当前时间。








2.依据条件查询标题的结果：
NotesList改进：
		修改方法：onOptionsItemSelected(MenuItem item)
		onOptionsItemSelected()：方法中，新增加了menu_Search选项，并且调用customView()方法导入布局文件


		新增方法：customView()：用于导入布局文件R.layout.search_by_title作为弹出对话框的样式；同时根据用户选择的取消/确认进行事件响应；如果选择确认，这使用Search()方法进行查询。
		Search()：用于偶去用户输入的查询关键字并且进行数据库的查询，返回mCursor，如果返回值非空，再调用refresh()刷新，重新加载当前页面List项。
		refresh()：根据查询的结果mCursor，重新加载当前页面List项。

		修改布局：list_options_menu.xml：新增了查询选项

		新增布局：search_by_title.xml：作为弹出的查询对话框的布局样式。



如果要查看所有的NOte，那么只要在查询中什么都能不要输入，直接点击确认查询，就会显示所有的项目。








过程截图：



程序初始界面
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/初始界面.PNG)

为新建Note输入内容：
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/新建Note-1.PNG)

新建Note完成
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/新建Note完成.PNG)


运行过程示范

标题编辑


选中装备编辑的标题内容：
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/编辑标题.PNG)

输入编辑的新标题
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/边疆标题-2.PNG)

编辑标题完成后结果
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/编辑标题-完成.PNG)


标题查询


查询入口
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/查询入口.PNG)

输入查询内容
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/输入查询内容.PNG)

显示查询结果
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/显示查询结果.PNG)



