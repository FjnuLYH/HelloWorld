package com.example.administrator.listview;

/**
 * Created by Administrator on 2017/4/5.
 */

import java.util.ArrayList;
import com.example.administrator.R;
import com.example.administrator.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyContactAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Contact> contacts;
    private ViewHolder mViewHolder;
    private ArrayList<Contact> selected_contacts = new ArrayList<Contact>();

    private boolean itemMultiCheckable;

    public MyContactAdapter(Context mContext, ArrayList<Contact> contacts) {
        this.mContext = mContext;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_listview_item, null);

            mViewHolder = new ViewHolder();

            mViewHolder.user_head = (ImageView) convertView.findViewById(R.id.user_head);
            mViewHolder.user_name_text = (TextView) convertView.findViewById(R.id.user_name);
            mViewHolder.phone_number_text = (TextView) convertView.findViewById(R.id.phone_number);
            mViewHolder.item_seleted = (CheckBox) convertView.findViewById(R.id.contact_selected_checkbox);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        // ************对于控件的具体处理****************

        // 设置checkbox是否可见
        if (itemMultiCheckable) {
            mViewHolder.item_seleted.setVisibility(View.VISIBLE);
            // 如果checkbox可见，证明当前处于可多选操作情况下,则根据用户选择情况设置checkbox被选中状态
            if (selected_contacts.contains(contacts.get(position))) {
                mViewHolder.item_seleted.setChecked(true);
            } else {
                mViewHolder.item_seleted.setChecked(false);
            }

        } else {
            mViewHolder.item_seleted.setVisibility(View.GONE);
        }

        // 控件赋值
        Contact contact = contacts.get(position);
        mViewHolder.user_name_text.setText(contact.getUserName());
        mViewHolder.phone_number_text.setText(contact.getPhoneNumber());

        return convertView;
    }

    public void setItemMultiCheckable(boolean flag) {
        itemMultiCheckable = flag;
    }

    public void addSelectedContact(int position) {
        selected_contacts.add(contacts.get(position));
    }

    public void cancelSeletedContact(int position) {
        selected_contacts.remove(contacts.get(position));
    }

    public void clearSeletedContacts() {
        selected_contacts = new ArrayList<Contact>();
    }

    public void deleteSeletedContacts() {
        for (Contact contact : selected_contacts) {
            contacts.remove(contact);
        }
    }

    static class ViewHolder {
        ImageView user_head;
        TextView user_name_text, phone_number_text;
        CheckBox item_seleted;
    }
}