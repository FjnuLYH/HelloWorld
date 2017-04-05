package com.example.administrator.launcheractivity;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ExpandableListActivityTest extends ExpandableListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

            int[] logos = new int[]
                    {
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher
                    };
            private String[] armTypes = new String[]
                    { "神族兵种","虫族兵种","人族兵种" };
            private String[][] arms = new String[][]
                    {
                            {"叉叉","龙骑","影刀","电兵"},
                            {"狗","刺蛇","飞龙","自爆机"},
                            {"机枪兵","护士","幽灵"}
                    };

            //获取指定组位置，指定子列表项数据
            @Override//获取指定组的组元素数量
            public int getGroupCount() {
                return armTypes.length;
            }

            @Override//返回子类数量(第二维数组数量)
            public int getChildrenCount(int groupPosition) {
                return arms[groupPosition].length;
            }

            @Override//获取指定组位置的组数据
            public Object getGroup(int groupPosition) {
                return armTypes[groupPosition];
            }

            @Override//返回子列表项数据
            public Object getChild(int groupPosition, int childPosition) {
                return arms[groupPosition][childPosition];
            }

            @Override//获取指定组位置的组ID
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override//返回子列表项ID
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override //
            public boolean hasStableIds() {
                return true;
            }

            @Override//决定每个组选项外观
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                //首先新建一个布局
                LinearLayout ll= new LinearLayout( ExpandableListActivityTest.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                //下面处理logo
                ImageView logo = new ImageView( ExpandableListActivityTest.this);
                logo.setImageResource(logos[groupPosition]);
                ll.addView(logo);
                //下面加入TextView
                TextView textview = getTextView();
                textview.setText( getGroup(groupPosition).toString() );
                ll.addView(textview);
                return ll;//完成，返回布局(一个group选项外观)
            }

            @Override //决定每个子选项的外观
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView textview =getTextView();
                textview.setText( getChild(groupPosition,childPosition).toString() );
                return textview;
            }

            @Override //确认
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }

            private TextView getTextView()
            {
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,64);
                TextView textview = new TextView(ExpandableListActivityTest.this);
                textview.setLayoutParams(lp);
                textview.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                textview.setPadding(36,0,0,0);
                textview.setTextSize(20);
                return textview;
            }
        };

        setListAdapter(adapter);//最后设置显示列表!
    }


}
