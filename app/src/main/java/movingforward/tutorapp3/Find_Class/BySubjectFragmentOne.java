package movingforward.tutorapp3.Find_Class;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.getImageText;
import movingforward.tutorapp3.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BySubjectFragmentOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BySubjectFragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BySubjectFragmentOne extends Fragment {
    SearchView svClass;
    CustomAdapter adapter;
    User mUser;



    // references to our images
    private int[] mThumbIds = {
            R.drawable.accounting,
            R.drawable.art,
            R.drawable.biology,
            R.drawable.cs,
            R.drawable.profits,
            R.drawable.lit,
            R.drawable.french_polynesia,
            R.drawable.math,
            R.drawable.physics,
            R.drawable.spain
    };
    private String[] mThumbStrings = {
            "Accounting",
            "Art",
            "Biology",
            "Computer Science",
            "Economics",
            "English",
            "French",
            "Mathematics",
            "Physics",
            "Spanish"
    };


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BySubjectFragmentOne() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BySubjectFragmentOne newInstance(User user) {
        BySubjectFragmentOne fragment = new BySubjectFragmentOne();
        Bundle args = new Bundle();
        args.putSerializable("mUser",user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mUser= (User) getArguments().getSerializable("mUser");

        final View rootView = inflater.inflate(R.layout.fragment_by_subject_fragment_one, container, false);
        SearchView svClass = (SearchView) rootView.findViewById(R.id.svClass);
        final GridView gridview = (GridView) rootView.findViewById(R.id.bySubjectGrid1);
        adapter = new CustomAdapter(getActivity(), mThumbIds, mThumbStrings);

        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String role1=mUser.getPermission().name();
                    String ClassName = ((TextView) view.findViewById(R.id.grid_Text_label)).getText().toString();
                    Tutor_list tutor_list = new Tutor_list();
                    tutor_list.setClassName(ClassName);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();//cw
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //cw
                    Bundle bundle = new Bundle();//cw
                    bundle.putSerializable("mUser", mUser);//cw
                    tutor_list.setArguments(bundle);//cw
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, tutor_list, tutor_list.getTag()).commit();





            }
        });
        svClass.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("Check", "Entering OnQueryTextChanges");
                    adapter.getFilter().filter(s.toString().toLowerCase());

                return true;
            }
        });



        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//--------------------------------------------------------------------------------------------------------------------------------
    public static class CustomAdapter extends BaseAdapter implements Filterable {
        private Context mContext;
        private String[] mobileValues;
        private LayoutInflater layoutInflater;

        ArrayList<getImageText> ClassList = new ArrayList<>();
        ArrayList<getImageText> origClassList;


        @Override
        public Filter getFilter() {
            Log.d("getFilter()", "Entering Filtering");
           Filter filter=new Filter() {
               @Override
               protected FilterResults performFiltering(CharSequence constraint) {
                   FilterResults results = new FilterResults();
                   ArrayList<getImageText> FilteredClassList = new ArrayList<getImageText>();

                   if(origClassList==null){
                       origClassList=new ArrayList<>(ClassList);
                   }
                   if(constraint==null || constraint.length()==0){
                       results.count=origClassList.size();
                       results.values=origClassList;
                   }else{
                       constraint=constraint.toString().toLowerCase();
                       for(int i=0;i<origClassList.size();i++){
                           getImageText classNames=origClassList.get(i);
                           if(classNames.ClassName.toLowerCase().startsWith(constraint.toString())){
                               FilteredClassList.add(classNames);
                           }
                       }
                       results.count=FilteredClassList.size();
                       //System.out.println

                       results.values=FilteredClassList;
                     //  Log.e("VALUES",results.values.toString());
                   }


                   return results;
               }

               @Override
               protected void publishResults(CharSequence constraint, FilterResults results) {

                   ClassList=(ArrayList<getImageText>) results.values;
                   notifyDataSetChanged();

               }
           };
            return filter;
        }


        CustomAdapter() {

        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            View grid = convertView;
            ImageView imgClassImage;
            TextView txtClassName;


            if (convertView == null) {
                grid = layoutInflater.inflate(R.layout.find_class_grid, null);
                grid = layoutInflater.inflate(R.layout.image_text_template, null);
            } else

            {
                grid = (View) convertView;
            }

            imgClassImage = (ImageView) grid.findViewById(R.id.grid_item_image);
            txtClassName = (TextView) grid.findViewById(R.id.grid_Text_label);
           // ClassList.add(new getImageText(txtClassName.getText().toString(), imgClassImage.getId()));


            imgClassImage.setImageResource(ClassList.get(position).ClassImage);
            txtClassName.setText(ClassList.get(position).getClassName());



            return grid;

        }


        public CustomAdapter(Context c, int[] Thumbids, String[] strings) {
            mContext = c;
            layoutInflater = LayoutInflater.from(mContext);
            for (int i = 0; i < Thumbids.length; i++) {
                ClassList.add(new getImageText(strings[i], Thumbids[i]));
            }

        }

        @Override
        public int getCount() {
            return ClassList.size();
        }


        @Override
        public Object getItem(int position) {
            return ClassList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
}

