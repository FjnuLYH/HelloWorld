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
	
	update()�����У�ÿ�θ���ʱ�����µ�Values�е�MODIFICATION_DATE����Ҳ��һ�����µ���ǰʱ�䡣




	2.����������ѯ����Ľ����
	NotesList�ļ��Ľ���
	
	
	�޸ķ�����onOptionsItemSelected(MenuItem item)
	onOptionsItemSelected()�������У���������menu_Searchѡ����ҵ���customView()�������벼���ļ�
	

	����������customView()�����ڵ��벼���ļ�R.layout.search_by_title��Ϊ�����Ի������ʽ��ͬʱ�����û�ѡ���ȡ��/ȷ�Ͻ����¼���Ӧ�����ѡ��ȷ�ϣ���ʹ��Search()�������в�ѯ��
	Search()������żȥ�û�����Ĳ�ѯ�ؼ��ֲ��ҽ������ݿ�Ĳ�ѯ������mCursor���������ֵ�ǿգ��ٵ���refresh()ˢ�£����¼��ص�ǰҳ��List�
	refresh()�����ݲ�ѯ�Ľ��mCursor�����¼��ص�ǰҳ��List�
	
	�޸Ĳ����ļ���list_options_menu.xml�������˲�ѯѡ��
		
	���������ļ���search_by_title.xml����Ϊ�����Ĳ�ѯ�Ի���Ĳ�����ʽ��
	
	
		���Ҫ�鿴���е�Note����ôֻҪ�ڲ�ѯ��ʲô���ܲ�Ҫ���룬ֱ�ӵ��ȷ�ϲ�ѯ���ͻ���ʾ���е���Ŀ��





��չ������ϸ���ܣ�

	1.�ı�༭Note�ı���ɫ
	NoteEditor�ļ���
			�޸ķ���onOptionsItemSelected(MenuItem item)������ѡ��
	����������void selectColors()  ����Dialog ѡ�񲢸ı���ɫ  ���һ�����Preference���б�����汳������

	���������ļ���select_background_colors.xml








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




