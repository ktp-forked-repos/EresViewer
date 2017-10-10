package com.example.maciek.eresviewer;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adrian on 17.08.2017.
 */

public class MarkAdapter extends ArrayAdapter<Mark> {

    public MarkAdapter(@NonNull Context context, ArrayList<Mark> markArrayList) {
        super(context, 0, markArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_group, parent, false);
        }

        Mark currentMark = getItem(position);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.markName);
        nameTextView.setText(currentMark.getMarkTitle());
        TextView myMarkTextView = (TextView) listItemView.findViewById(R.id.myMark);
        myMarkTextView.setText(String.valueOf(currentMark.getMyMark()));
        TextView minMarkTextView = (TextView) listItemView.findViewById(R.id.lowerMark);
        minMarkTextView.setText(String.valueOf(currentMark.getLowerMark()));
        TextView avgMarkTextView = (TextView) listItemView.findViewById(R.id.averageMark);
        avgMarkTextView.setText(String.valueOf(currentMark.getAverageMark()));
        TextView maxMarkTextView = (TextView) listItemView.findViewById(R.id.higherMark);
        maxMarkTextView.setText(String.valueOf(currentMark.getHigherMark()));
        TextView amountOfMarksTextView = (TextView) listItemView.findViewById(R.id.amountOfMarks);
        amountOfMarksTextView.setText(String.valueOf(currentMark.getAmountOfMarks()));

        return listItemView;
    }

}
