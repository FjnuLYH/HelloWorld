							android��Ŀ-�ʼǱ�Ӧ��NotePad


Ŀ¼��
	��Ŀ���
	�����ļ�����
	��������
	��չ���ܽ���
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


������չ��
1.���ʱ�����
NotePadProvider������
		insert()�����У�ÿ���²�������ʱ��ͨ������GetTime.Get_Now_Time_Long()������ȡ��ǰʱ�䣻Ȼ���ٴ������Values�н�CREATE_DATE��MODIFICATION_DATE���е���ֵ�滻�ɵ�ǰʱ�䡣
		update()�����У�ÿ�θ���ʱ�����µ�Values�е�MODIFICATION_DATE����Ҳ��һ�����µ���ǰʱ�䡣








2.����������ѯ����Ľ����
NotesList�Ľ���
		�޸ķ�����onOptionsItemSelected(MenuItem item)
		onOptionsItemSelected()�������У���������menu_Searchѡ����ҵ���customView()�������벼���ļ�


		����������customView()�����ڵ��벼���ļ�R.layout.search_by_title��Ϊ�����Ի������ʽ��ͬʱ�����û�ѡ���ȡ��/ȷ�Ͻ����¼���Ӧ�����ѡ��ȷ�ϣ���ʹ��Search()�������в�ѯ��
		Search()������żȥ�û�����Ĳ�ѯ�ؼ��ֲ��ҽ������ݿ�Ĳ�ѯ������mCursor���������ֵ�ǿգ��ٵ���refresh()ˢ�£����¼��ص�ǰҳ��List�
		refresh()�����ݲ�ѯ�Ľ��mCursor�����¼��ص�ǰҳ��List�

		�޸Ĳ��֣�list_options_menu.xml�������˲�ѯѡ��

		�������֣�search_by_title.xml����Ϊ�����Ĳ�ѯ�Ի���Ĳ�����ʽ��



���Ҫ�鿴���е�NOte����ôֻҪ�ڲ�ѯ��ʲô���ܲ�Ҫ���룬ֱ�ӵ��ȷ�ϲ�ѯ���ͻ���ʾ���е���Ŀ��








���̽�ͼ��



�����ʼ����
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



