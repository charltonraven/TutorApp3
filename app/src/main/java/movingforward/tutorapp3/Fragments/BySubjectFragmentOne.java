package movingforward.tutorapp3.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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

    // references to our images
    private Integer[] mThumbIds = {
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
            "Spanish"};


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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BySubjectFragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static BySubjectFragmentOne newInstance(String param1, String param2) {
        BySubjectFragmentOne fragment = new BySubjectFragmentOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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


        View rootView = inflater.inflate(R.layout.fragment_by_subject_fragment_one, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.bySubjectGrid1);
        CustomAdapter adapter = new CustomAdapter(getActivity());

        gridview.setAdapter(adapter);


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

    /**
     * Created by raven on 10/11/2016.
     */

    public static class CustomAdapter extends BaseAdapter {
        private Context mContext;
        private String[] mobileValues;
        private LayoutInflater layoutInflater;

        // references to our images
        private Integer[] mThumbIds = {
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




        CustomAdapter() {

        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            TextView textView;


            View grid;

            if (convertView == null) {
                grid = new View(mContext);
                grid = layoutInflater.inflate(R.layout.image_text_template, null);

            } else {
                grid = (View) convertView;

            }
            imageView = (ImageView) grid.findViewById(R.id.grid_item_image);
            imageView.setImageResource(mThumbIds[position]);
            textView = (TextView) grid.findViewById(R.id.grid_Text_label);
            textView.setText(mThumbStrings[position]);

            return grid;
        }


        public CustomAdapter(Context c) {
            mContext = c;
            layoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }



        String[] Department = {
                "African & African American Studies",
                "Accounting",
                "Anthropology",
                "Nursing-Graduate",
                "Art",
                "Astronomy",
                "Bachelor of General Studies",
                "Biology",
                "Business",
                "Chemistry",
                "Computer Information Systems Management",
                "Cooperative Education Program",
                "Computer Science",
                "Early Childhood Education",
                "Economics", "Education",
                "Elementary Education",
                "English",
                "Engineering,",
                "Industrial",
                "Environmental Science",
                "Exchange Program",
                "Finance",
                "French",
                "Geography",
                "German",
                "Gender Studies",
                "History"
                , "Health"
                , "Honors",
                "International Studies",
                "Inter-professional Healthcare",
                "Mathematics", "MBA-School Of Business",
                "Mass Communication",
                "Management",
                "Management Information Systems",
                "Marketing", "Middle Level Education",
                "Military Science"
                , "Music",
                "Nonprofit Management",
                "Nursing(NRN)Rn-BSN",
                "Nursing",
                "Physician Assistant",
                "Physical Education",
                "Physics",
                "Political Science",
                "Philosophy & Religious Studies",
                "Physical Science",
                "Psychology",
                "Sociology",
                "Spanish",
                "Speech",
                "Statistics",
                "Theatre",
                "University Life 100"};
    }
}
