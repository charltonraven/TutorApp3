package movingforward.tutorapp3.Entities.class_Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import movingforward.tutorapp3.R;

/**
 * Created by raven on 3/2/2017.
 */

public class CourseAdapter extends ArrayAdapter<String> implements AdapterView.OnItemClickListener {


    ArrayList<String> values;

    public CourseAdapter(Context context, int resource, ArrayList<String> values) {
        super(context, resource);
        this.values = values;
    }

    public CourseAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(String object) {
        super.add(object);
    }

    @Override
    public void addAll(Collection<? extends String> collection) {
        super.addAll(collection);
    }

    @Override
    public int getCount() {
        return values.size();
    }


    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView tvDepartCourse;



        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.btn_txt_template, null);
        } else {
            v = (View) convertView;
        }

        tvDepartCourse = (TextView) v.findViewById(R.id.tv_ClassItems);


        tvDepartCourse.setText(values.get(position));



        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}