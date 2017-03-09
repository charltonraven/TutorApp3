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

import movingforward.tutorapp3.Entities.ListItemHolder;
import movingforward.tutorapp3.Find_Class.Tutor_list;
import movingforward.tutorapp3.R;

/**
 * Created by raven on 3/2/2017.
 */

public class TutorListAdapter extends ArrayAdapter<Tutor_list> implements AdapterView.OnItemClickListener {


    ArrayList<ListItemHolder> values;

    public TutorListAdapter(Context context, int resource, ArrayList<ListItemHolder> values) {
        super(context, resource);
        this.values = values;
    }

    public TutorListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Tutor_list object) {
        super.add(object);
    }

    @Override
    public void addAll(Collection<? extends Tutor_list> collection) {
        super.addAll(collection);
    }

    @Override
    public int getCount() {
        return values.size();
    }


    @Override
    public int getPosition(Tutor_list item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView tvClassName;
        TextView tvTutorName;


        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.txt_txt_template, null);
        } else {
            v = (View) convertView;
        }

        tvClassName = (TextView) v.findViewById(R.id.tv_Class_Name);
        tvTutorName = (TextView) v.findViewById(R.id.tv_TutorName);

        tvClassName.setText(values.get(position).ClassTutored);
        tvTutorName.setText(values.get(position).getfName() + " " + values.get(position).getlName());


        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}