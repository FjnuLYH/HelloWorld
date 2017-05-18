							android项目-笔记本应用NotePad


目录：
	项目简介
	代码文件描述
	基本功能
	扩展功能
	基本功能(新增)详细介绍：
	扩展功能详细介绍：
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
	添加时间戳
	按照标题查询


扩展功能：


基本功能(新增)详细介绍：
	1.添加时间戳：
	NotePadProvider文件修改进：
	
	
	insert()函数中，每次新插入数据时，通过调用GetTime.Get_Now_Time_Long()函数获取当前时间；然后再待插入的Values中将CREATE_DATE，MODIFICATION_DATE两列的数值替换成当前时间。主要修改代码如下：
		// Gets the current system time in milliseconds
		Long now = GetTime.Get_Now_Time_Long();
		
		// If the values map doesn't contain the creation date, sets the value to the current time.
		if (values.containsKey(NotePad.Notes.COLUMN_NAME_CREATE_DATE) == false) {
		values.put(NotePad.Notes.COLUMN_NAME_CREATE_DATE, now);
		}
		
		// If the values map doesn't contain the modification date, sets the value to the current time.
		if (values.containsKey(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE) == false) {
		values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, now);
		}
	
	update()函数中，每次更新时，更新的Values中的MODIFICATION_DATE数据也会一并更新到当前时间。




	2.依据条件查询标题的结果：
	NotesList文件改进：
	
	
	修改方法：onOptionsItemSelected(MenuItem item)
	onOptionsItemSelected()：方法中，新增加了menu_Search选项，并且调用customView()方法导入布局文件
	

	新增方法：customView()：用于导入布局文件R.layout.search_by_title作为弹出对话框的样式；同时根据用户选择的取消/确认进行事件响应；如果选择确认，这使用Search()方法进行查询。
	Search()：用于偶去用户输入的查询关键字并且进行数据库的查询，返回mCursor，如果返回值非空，再调用refresh()刷新，重新加载当前页面List项。
	refresh()：根据查询的结果mCursor，重新加载当前页面List项。
	
	修改布局文件：list_options_menu.xml：新增了查询选项
		
	新增布局文件：search_by_title.xml：作为弹出的查询对话框的布局样式。
	
	
		如果要查看所有的Note，那么只要在查询中什么都能不要输入，直接点击确认查询，就会显示所有的项目。





扩展功能详细介绍：

	1.改变编辑Note的背景色
	NoteEditor文件：
			修改方法onOptionsItemSelected(MenuItem item)：新增选项
	新增方法：void selectColors()  弹出Dialog 选择并改变颜色  并且还引用Preference进行保存界面背景参数

	新增布局文件：select_background_colors.xml








过程截图：



程序初始界面  即进入应用后的第一个界面
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



选择背景色

进入Note
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/选择背景色1.PNG)

点击选择颜色
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/选择背景色2.PNG)

点击所属颜色。
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/选择背景色3.PNG)




