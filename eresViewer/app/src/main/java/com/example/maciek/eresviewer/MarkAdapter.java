package com.example.maciek.eresviewer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Maciek on 09.07.2017.
 */

public class MarkAdapter extends ArrayAdapter<Mark> {

    public MarkAdapter(Activity context, ArrayList<Mark> marksList) {
        super(context, 0, marksList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mark currentMark = getItem(position);

        View listItemView = convertView;
        if (listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        TextView TitleTextView = (TextView) listItemView.findViewById(R.id.markName);
        TitleTextView.setText(currentMark.getMarkTitle());

        TextView myMarkTextView = (TextView) listItemView.findViewById(R.id.myMark);
        myMarkTextView.setText(Float.toString(currentMark.getMyMark()));

        TextView lowerMarkTextView = (TextView) listItemView.findViewById(R.id.lowerMark);
        lowerMarkTextView.setText(Float.toString(currentMark.getLowerMark()));

        TextView averageMarkTextView = (TextView) listItemView.findViewById(R.id.averageMark);
        averageMarkTextView.setText(Float.toString(currentMark.getAverageMark()));

        TextView higherMarkTextView = (TextView) listItemView.findViewById(R.id.higherMark);
        higherMarkTextView.setText((Float.toString(currentMark.getHigherMark())));

        TextView amountOfMarksTextView = (TextView) listItemView.findViewById(R.id.amountOfMarks);
        amountOfMarksTextView.setText(Float.toString(currentMark.getAmountOfMarks()));

        return listItemView;
    }

}
