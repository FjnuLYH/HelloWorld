							android��Ŀ-�ʼǱ�Ӧ��NotePad


Ŀ¼��
	��Ŀ���
	�����ļ�����
	��������
	��չ����
	��������(����)��ϸ���ܣ�
	��չ������ϸ���ܣ�
	����ʾ������ͼ



�ļ�������
NotePad	��Լ��
NotesList	�������
NotePadProvider	���ݿ������
TitleEditor	�༭����
NoteEditor	Note���ݱ༭��
NotesLiveFolder	
GetTime	��ȡ��ǰʱ��


�������ܣ�
	�½�Note
	�༭Note����
	�༭Note����
	ɾ��Note
	���ʱ���
	���ձ����ѯ


��չ���ܣ�


��������(����)��ϸ���ܣ�
1.���ʱ�����
NotePadProvider�ļ��޸Ľ���
	
	
insert()�����У�ÿ���²�������ʱ��ͨ������GetTime.Get_Now_Time_Long()������ȡ��ǰʱ�䣻Ȼ���ٴ������Values�н�CREATE_DATE��MODIFICATION_DATE���е���ֵ�滻�ɵ�ǰʱ�䡣��Ҫ�޸Ĵ������£�


	// �õ���ǰʱ��
	Long now = GetTime.Get_Now_Time_Long();
	
	//��ʼ��һ���¼�¼ʱ����Ҫ�ѵ�ǰʱ�����
	if (values.containsKey(NotePad.Notes.COLUMN_NAME_CREATE_DATE) == false) {
	values.put(NotePad.Notes.COLUMN_NAME_CREATE_DATE, now);
	}
	
	//��ʼ��һ���¼�¼ʱ����Ҫ�ѵ�ǰʱ�����
	if (values.containsKey(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE) == false) {
	values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, now);
	}
	//֮����벻��


update()�����У�ÿ�θ���ʱ�����µ�Values�е�MODIFICATION_DATE����Ҳ��һ�����µ���ǰʱ�䡣���´�������

	//��ȡ��ǰʱ��
	Long now = GetTime.Get_Now_Time_Long();
	//������µ�Value��
	values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,now);
	//������벻��-update���½����ݿ�
	


2.����������ѯ����Ľ����
NotesList�ļ��Ľ���
	
	
�޸ķ�����onOptionsItemSelected(MenuItem item)
onOptionsItemSelected()�������У���������menu_Searchѡ����ҵ���customView()�������벼���ļ�  ��������

	@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.menu_add:
	           startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));
		   return true;
		case R.id.menu_paste:
	          startActivity(new Intent(Intent.ACTION_PASTE, getIntent().getData()));
	          return true;
	        case R.id.menu_Search://���ѡ����ǲ���
	                //����ֱ��ʹ��Dialog�����Ի�����в�ѯ����
	                System.out.println("NotesList�������Ĳ˵����ѡ�menu_Search");
	
  	              customView();//��������-���ò����ļ�
	                return true;
	        default:
	            return super.onOptionsItemSelected(item);
 	       }
	    }
		

����������customView()�����ڵ��벼���ļ�R.layout.search_by_title��Ϊ�����Ի������ʽ��ͬʱ�����û�ѡ���ȡ��/ȷ�Ͻ����¼���Ӧ�����ѡ��ȷ�ϣ���ʹ��Search()�������в�ѯ��    �������£�


	    public void customView( )
	    {
	        TableLayout search_view = (TableLayout)getLayoutInflater().inflate(
	                R.layout.search_by_title,null
 	       );
	        //����һ��Ҫ���������д���� search_view.findViewById
	        // ������this.findViewById��Ϊû�м��� search_by_title.xml �Ҳ���R.id.SearchTitle��
	        //���֮���mText����NUll
 	       //����Ҳ������setContentView(R.layout.search_by_title) ���ǻᱨ��(xml)
 	       mText = (EditText) search_view.findViewById(R.id.SearchTitle);
	
		        new AlertDialog.Builder(this)
 	               .setTitle("��ѯ�����")
 	               .setView(search_view)
  	              .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {//����ȡ����ť
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        //����һ����Toast��Ϣ
	                        Toast.makeText(NotesList.this,"��ѡ����ȷ�ϲ�ѯ����",
	                                //������ʾ��ʱ��
	                                Toast.LENGTH_SHORT).show();
	                        Search();
 	                   }
	                })
 	               .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {//����ȡ����ť
 		                   @Override
 	                   public void onClick(DialogInterface dialog, int which) {
 	                       //����һ����Toast��Ϣ
		                        Toast.makeText(NotesList.this,"��ѡ����ȡ����ѯ����",
		                 //������ʾ��ʱ��
	                                Toast.LENGTH_SHORT).show();

     	               }
    	            })
    	            .create()
   	             .show();
  	  }


Search()������żȥ�û�����Ĳ�ѯ�ؼ��ֲ��ҽ������ݿ�Ĳ�ѯ������mCursor���������ֵ�ǿգ��ٵ���refresh()ˢ�£����¼��ص�ǰҳ��List�
�������£�

    protected void Search() {

        //Ĭ��URI��Ϊ������
        mUri = NotePad.Notes.CONTENT_URI;//����������notes��URI



        String Title="";//TitleĬ�ϳ�ʼ��
        //�����ǵ��EditTitleʱ
        //�����������Ҫ���ҵ�����

        if(  mText!=null )//�Է���һ���ܵ�NULLPointException
            if(mText.getText() != null)
                Title = mText.getText().toString();//��ȡ


        if( mText==null )System.out.println("mText �� null����");


        System.out.println("Title"+Title);





        //��ѯ�������
        String selection =  NotePad.Notes.COLUMN_NAME_TITLE + " like ? ";
        //��ѯ������������ֵ
        String[]   selectionArgs = {"%" + Title + "%"};
        System.out.println("AA!!!==" + selectionArgs[0]);
        System.out.println("MURI==" + mUri);

        mCursor=getContentResolver().query(
                mUri,    // The URI for the note to update.
                PROJECTION,  // The values map containing the columns to update and the values to use.
                selection,    // ��ѯ���� -where titile = ??
                selectionArgs,     // ��ѯ������ֵ  ??��ֵ
                null       //����
        );//��ѯ�������



        if (mCursor != null) {//��Ϊ�ղ�����ˢ��

            System.out.println("ˢ��ҳ��");
            refresh();
        }
        else
            System.out.println("ˢ��ҳ��ʧ�� mCursor==NULL");

    }




refresh()�����ݲ�ѯ�Ľ��mCursor�����¼��ص�ǰҳ��List�
�������£�



	
�޸Ĳ����ļ���list_options_menu.xml�������˲�ѯѡ��
�������£�



		
���������ļ���search_by_title.xml����Ϊ�����Ĳ�ѯ�Ի���Ĳ�����ʽ��
�������£�



	
	
���Ҫ�鿴���е�Note����ôֻҪ�ڲ�ѯ��ʲô���ܲ�Ҫ���룬ֱ�ӵ��ȷ�ϲ�ѯ���ͻ���ʾ���е���Ŀ��





��չ������ϸ���ܣ�

1.�ı�༭Note�ı���ɫ
NoteEditor�ļ���
�޸ķ���onOptionsItemSelected(MenuItem item)������ѡ��
�������£�






����������void selectColors()  ����Dialog ѡ�񲢸ı���ɫ  ���һ�����Preference���б�����汳������
�������£�




���������ļ���select_background_colors.xml
�������£�











���̽�ͼ��



�����ʼ����  ������Ӧ�ú�ĵ�һ������
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/��ʼ����.PNG)

Ϊ�½�Note�������ݣ�
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/�½�Note-1.PNG)

�½�Note���
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/�½�Note���.PNG)


���й���ʾ��

����༭


ѡ��װ���༭�ı������ݣ�
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/�༭����.PNG)

����༭���±���
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/�߽�����-2.PNG)

�༭������ɺ���
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/�༭����-���.PNG)


�����ѯ


��ѯ���
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/��ѯ���.PNG)

�����ѯ����
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/�����ѯ����.PNG)

��ʾ��ѯ���
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/��ʾ��ѯ���.PNG)



ѡ�񱳾�ɫ

����Note
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/ѡ�񱳾�ɫ1.PNG)

���ѡ����ɫ
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/ѡ�񱳾�ɫ2.PNG)

���������ɫ��
![image](https://github.com/FjnuLYH/HelloWorld/blob/master/Android_NotePad/pict/ѡ�񱳾�ɫ3.PNG)




