# NotePad
This is an AndroidStudio rebuild of google SDK sample NotePad



关于时间戳：
原方案是:显示列表时同时从数据库读取Title与ModifyDate数据，但是发现会引起上下文bug
现方案：直接在数据库的Title列里，强制加上日期；
        在NotePadProvider(Insert,update 函数)以及TitleEditor(onResume 函数)中；
        主要在每次新加Note,生成新Note的默认Title(内容第一行)/编辑Title以及更新数据时，
        自动往预定数据库传输的Values中的Title(Key)加入当前时间
        即：每一次新建Note、编辑标题、更新内容都会触发改变的。
