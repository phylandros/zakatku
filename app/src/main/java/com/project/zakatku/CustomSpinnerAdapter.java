package com.project.zakatku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> values;
    private int[] imageResIds;

    public CustomSpinnerAdapter(Context context, int resource, List<String> values, int[] imageResIds) {
        super(context, resource, values);
        this.context = context;
        this.values = values;
        this.imageResIds = imageResIds;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_spinner_item, parent, false);
        TextView label = (TextView) row.findViewById(R.id.spinnerItemText);
        ImageView icon = (ImageView) row.findViewById(R.id.spinnerItemIcon);

        label.setText(values.get(position));
        icon.setImageResource(imageResIds[position]);

        return row;
    }
}

