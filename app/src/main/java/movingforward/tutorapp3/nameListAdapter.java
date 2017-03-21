package movingforward.tutorapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by raven on 3/2/2017.
 */

public class nameListAdapter extends ArrayAdapter<Teacher_list> implements AdapterView.OnItemClickListener {


    ArrayList<firstLastName> values;

    public nameListAdapter(Context context, int resource, ArrayList<firstLastName> values) {
        super(context, resource);
        this.values = values;
    }

    public nameListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Teacher_list object) {
        super.add(object);
    }

    @Override
    public void addAll(Collection<? extends Teacher_list> collection) {
        super.addAll(collection);
    }

    @Override
    public int getCount() {
        return values.size();
    }


    @Override
    public int getPosition(Teacher_list item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView tvFirstName;
        TextView tvLastName;


        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.txt_txt_template, null);
        } else {
            v = (View) convertView;
        }

        tvFirstName = (TextView) v.findViewById(R.id.tv_Class_Name);
        tvLastName = (TextView) v.findViewById(R.id.tv_TutorName);

        tvFirstName.setText(values.get(position).getfName());
        tvLastName.setText(values.get(position).getlName());


        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}