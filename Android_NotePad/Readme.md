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


	// 得到当前时间
	Long now = GetTime.Get_Now_Time_Long();
	
	//初始化一条新纪录时，需要把当前时间插入
	if (values.containsKey(NotePad.Notes.COLUMN_NAME_CREATE_DATE) == false) {
	values.put(NotePad.Notes.COLUMN_NAME_CREATE_DATE, now);
	}
	
	//初始化一条新纪录时，需要把当前时间插入
	if (values.containsKey(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE) == false) {
	values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, now);
	}
	//之后代码不变


update()函数中，每次更新时，更新的Values中的MODIFICATION_DATE数据也会一并更新到当前时间。更新代码如下

	//获取当前时间
	Long now = GetTime.Get_Now_Time_Long();
	//放入更新的Value中
	values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,now);
	//下面代码不变-update更新进数据库
	


2.依据条件查询标题的结果：
NotesList文件改进：
	
	
修改方法：onOptionsItemSelected(MenuItem item)
onOptionsItemSelected()：方法中，新增加了menu_Search选项，并且调用customView()方法导入布局文件  代码如下

	@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.menu_add:
	           startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));
		   return true;
		case R.id.menu_paste:
	          startActivity(new Intent(Intent.ACTION_PASTE, getIntent().getData()));
	          return true;
	        case R.id.menu_Search://如果选择的是查找
	                //这里直接使用Dialog弹出对话框进行查询输入
	                System.out.println("NotesList—上下文菜单点击选项—menu_Search");
	
  	              customView();//新增方法-引用布局文件
	                return true;
	        default:
	            return super.onOptionsItemSelected(item);
 	       }
	    }
		

新增方法：customView()：用于导入布局文件R.layout.search_by_title作为弹出对话框的样式；同时根据用户选择的取消/确认进行事件响应；如果选择确认，这使用Search()方法进行查询。    代码如下：


	    public void customView( )
	    {
	        TableLayout search_view = (TableLayout)getLayoutInflater().inflate(
	                R.layout.search_by_title,null
 	       );
	        //这里一定要用上面哪行代码的 search_view.findViewById
	        // 不能用this.findViewById因为没有加载 search_by_title.xml 找不到R.id.SearchTitle，
	        //结果之后的mText回事NUll
 	       //但是也不能用setContentView(R.layout.search_by_title) 还是会报错(xml)
 	       mText = (EditText) search_view.findViewById(R.id.SearchTitle);
	
		        new AlertDialog.Builder(this)
 	               .setTitle("查询框标题")
 	               .setView(search_view)
  	              .setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置取消按钮
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        //创建一个·Toast信息
	                        Toast.makeText(NotesList.this,"你选择了确认查询！！",
	                                //设置显示的时间
	                                Toast.LENGTH_SHORT).show();
	                        Search();
 	                   }
	                })
 	               .setNegativeButton("取消", new DialogInterface.OnClickListener() {//设置取消按钮
 		                   @Override
 	                   public void onClick(DialogInterface dialog, int which) {
 	                       //创建一个·Toast信息
		                        Toast.makeText(NotesList.this,"你选择了取消查询！！",
		                 //设置显示的时间
	                                Toast.LENGTH_SHORT).show();

     	               }
    	            })
    	            .create()
   	             .show();
  	  }


Search()：用于偶去用户输入的查询关键字并且进行数据库的查询，返回mCursor，如果返回值非空，再调用refresh()刷新，重新加载当前页面List项。
代码如下：

    protected void Search() {

        //默认URI即为整个表
        mUri = NotePad.Notes.CONTENT_URI;//这里先试试notes的URI



        String Title="";//Title默认初始化
        //这里是点击EditTitle时
        //把输入框中需要查找的数据

        if(  mText!=null )//以防万一可能的NULLPointException
            if(mText.getText() != null)
                Title = mText.getText().toString();//读取


        if( mText==null )System.out.println("mText 是 null！！");


        System.out.println("Title"+Title);





        //查询条件语句
        String selection =  NotePad.Notes.COLUMN_NAME_TITLE + " like ? ";
        //查询条件语句的条件值
        String[]   selectionArgs = {"%" + Title + "%"};
        System.out.println("AA!!!==" + selectionArgs[0]);
        System.out.println("MURI==" + mUri);

        mCursor=getContentResolver().query(
                mUri,    // The URI for the note to update.
                PROJECTION,  // The values map containing the columns to update and the values to use.
                selection,    // 查询条件 -where titile = ??
                selectionArgs,     // 查询条件的值  ??的值
                null       //排序
        );//查询数据完成



        if (mCursor != null) {//不为空才星星刷新

            System.out.println("刷新页面");
            refresh();
        }
        else
            System.out.println("刷新页面失败 mCursor==NULL");

    }




refresh()：根据查询的结果mCursor，重新加载当前页面List项。
代码如下：


    /**
     * 本方法模仿onCreate()方法的一部分代码，
     * 刷新页面，加载查询结果
     */
    public void refresh() {
        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE,
                NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;

        int[] viewIDs = { android.R.id.text1 ,android.R.id.text2};//显示的组件数量要等于dataColumns   一对一关系


        // 用SimpleCursorAdapter加载查询结果

        SimpleCursorAdapter adapter
                = new SimpleCursorAdapter(
                this,                             // The Context for the ListView
                R.layout.noteslist_item,          //表明列表项的布局
                mCursor,                           // 表示从查询得到的光标中的记录作为适配器的数据
                dataColumns,                      //显示的内容
                viewIDs                           //显示用的视图
        );
        // Sets the ListView's adapter to be the cursor adapter that was just created.
        setListAdapter(adapter);
    }


	
修改布局文件：list_options_menu.xml：新增了查询选项
代码如下：

	新增代码
	<item android:id="@+id/menu_Search"
 	       android:icon="@drawable/ic_menu_compose"
 	       android:title="查询"
	        android:alphabeticShortcut='p' />
	</menu>

		
新增布局文件：search_by_title.xml：作为弹出的查询对话框的布局样式。
代码如下：


	<?xml version="1.0" encoding="utf-8"?>
	<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="wrap_content"
	    android:layout_height="wrap_content"
	    android:id="@+id/Tablelayout"
	    android:paddingLeft="6dip"
	    android:paddingRight="6dip"
	    android:paddingBottom="3dip">

	<!--本文件是按标题查询的弹出窗口布局文件-->
	
	    <TableRow android:layout_width="match_parent"
	        android:layout_height="wrap_content">
	
        <!--用户名输入框-->
        <EditText android:id="@+id/SearchTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="请输入查询111"
            android:selectAllOnFocus="true"/>

 	   </TableRow>
	</TableLayout>
	
	
如果要查看所有的Note，那么只要在查询中什么都能不要输入，直接点击确认查询，就会显示所有的项目。





扩展功能详细介绍：

1.改变编辑Note的背景色
NoteEditor文件：
修改方法onOptionsItemSelected(MenuItem item)：新增选项
代码如下：

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle all of the possible menu actions.
        switch (item.getItemId()) {
        case R.id.menu_save:
            String text = mText.getText().toString();
            updateNote(text, null);
            finish();
            break;
        case R.id.menu_delete:
            deleteNote();
            finish();
            break;
        case R.id.menu_revert:
            cancelNote();
            break;
            case R.id.select_color:
                selectColors();
                break;
        }
        return super.onOptionsItemSelected(item);
    }





新增方法：void selectColors()  弹出Dialog 选择并改变颜色  并且还引用Preference进行保存界面背景参数
代码如下：


    //新增函数--弹出Dialog 选择并改变颜色
    private final void selectColors()
    {


        preferences = getSharedPreferences("blackground",MODE_PRIVATE);
        Them = preferences.getInt("Them",0);//config不存在时返回0

        //editor = preferences.edit();



        System.out.println("启动 选择颜色");

        //setContentView(R.layout.note_editor);


        RelativeLayout relative_view = (RelativeLayout)getLayoutInflater().inflate(
                R.layout.select_background_colors,null
        );


        //这里如果没有setContentView(R.layout.select_background_colors);
        //Button回事null；但是不能用setContentView
        final Button But_white = (Button)relative_view.findViewById(R.id.color_white);
        final Button But_blueviolet = (Button)relative_view.findViewById(R.id.color_aqua);
        final Button But_greenyellow= (Button)relative_view.findViewById(R.id.color_greenyellow);





        new AlertDialog.Builder(this)
                .setTitle("选择背景颜色")
                .setView(relative_view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();



        But_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mText.setBackgroundColor( getResources().getColor(R.color.white) );//这里设置背景
                Toast.makeText(NoteEditor.this,"你选择了白色！！",
                        //设置显示的时间
                        Toast.LENGTH_SHORT).show();

                Them=0;

            }
        });

        But_blueviolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mText.setBackgroundColor( getResources().getColor(R.color.aqua) );//这里设置背景
                Toast.makeText(NoteEditor.this,"你选择了浅蓝色！！",
                        //设置显示的时间
                        Toast.LENGTH_SHORT).show();

                Them=1;

            }
        });

        But_greenyellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mText.setBackgroundColor( getResources().getColor(R.color.greenyellow) );//这里设置背景
                Toast.makeText(NoteEditor.this,"你选择了黄绿色！！",
                        //设置显示的时间
                        Toast.LENGTH_SHORT).show();

                Them=2;

            }
        });




    }


新增布局文件：select_background_colors.xml
代码如下：



	<?xml version="1.0" encoding="utf-8"?>
	<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:padding="30dp"
    android:layout_margin="25dp"

    android:weightSum="1">


    <!--中间
    -->


    <Button
        android:id="@+id/color_aqua"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:background="@color/aqua"
        android:layout_centerInParent="true"
         />

    <Button
        android:id="@+id/color_greenyellow"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:background="@color/greenyellow"
        android:layout_toLeftOf="@id/color_aqua"
        android:layout_alignBottom="@+id/color_aqua"
        android:layout_marginLeft="30dp"
        android:layout_marginRight="30dp"/>

    <Button
        android:id="@+id/color_white"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:background="@color/white"
        android:layout_centerVertical="true"
        android:layout_toRightOf="@+id/color_aqua"
        android:layout_alignBottom="@id/color_aqua"
        android:layout_marginLeft="30dp"
        android:layout_marginRight="30dp"/>

	</RelativeLayout>








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






