package com.example.maciek.eresviewer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Adrian on 2017-07-13.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Mark> listDataHeader;
    private HashMap<String,List<String>> listHashMap;

    public ExpandableListAdapter(Context context, List<Mark> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
       // return listHashMap.get(listDataHeader.get(groupPosition).getMarkTitle()).size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //return listHashMap.get(listDataHeader.get(groupPosition).getMarkTitle()).get(childPosition);
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup parent) {

        Mark currentMark = (Mark)getGroup(position);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group,null);

        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.markName);
        lblListHeader.setText(currentMark.getMarkTitle());

        TextView myMarkTextView = (TextView) convertView.findViewById(R.id.myMark);
        myMarkTextView.setText(Float.toString(currentMark.getMyMark()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //final String childText = (String)getChild(groupPosition,childPosition);
        Mark currentMark = (Mark)getGroup(groupPosition);
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,null);
        }

        TextView lowerMarkTextView = (TextView) convertView.findViewById(R.id.lowerMark);
        lowerMarkTextView.setText(Float.toString(currentMark.getLowerMark()));

        TextView averageMarkTextView = (TextView) convertView.findViewById(R.id.averageMark);
        averageMarkTextView.setText(Float.toString(currentMark.getAverageMark()));

        TextView higherMarkTextView = (TextView) convertView.findViewById(R.id.higherMark);
        higherMarkTextView.setText((Float.toString(currentMark.getHigherMark())));

        TextView amountOfMarksTextView = (TextView) convertView.findViewById(R.id.amountOfMarks);
        amountOfMarksTextView.setText(Float.toString(currentMark.getAmountOfMarks()));


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
