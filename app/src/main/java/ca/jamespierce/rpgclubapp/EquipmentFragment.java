package ca.jamespierce.rpgclubapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EquipmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EquipmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquipmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView list;

    // Declare variables for items that will appear in the CardView. These will be used in the Adapter
    TextView equipmentDescriptionTextview;

    private OnFragmentInteractionListener mListener;

    public EquipmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EquipmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EquipmentFragment newInstance(String param1, String param2) {
        EquipmentFragment fragment = new EquipmentFragment();
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

    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equipment, container, false);

        equipmentDescriptionTextview = (TextView) view.findViewById(R.id.descriptionText);

        final ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();

        // Add items to the arraylist
        equipmentList.add(new Equipment("Pen or Pencil", "This is a writing utensil that you will need in order to write down and help keeptrack of your stats in your game of choice. You should bring a few spares in case you run out of ink or lead."));
        equipmentList.add(new Equipment("Character Sheets", "These are usually game specific. Bring your character sheet(If you have already made one), or bring an empty one if you still need to create a character."));
        equipmentList.add(new Equipment("Dice", "You should bring your dice sets. These range from sets of D6 to a full range of D2 - D20 or even D100(typically we use two D10's instead as a D100 is more like a golf ball and can take a while to stop."));
        equipmentList.add(new Equipment("Character Model", "If you are playing a game that uses character models, make sure you bring one for your character. This is so that you can customize your character as you want."));
        equipmentList.add(new Equipment("test1", "hello1"));
        equipmentList.add(new Equipment("test2", "hello2"));
        equipmentList.add(new Equipment("test3", "hello3"));
        equipmentList.add(new Equipment("test4", "hello4"));

        // This assigns an adapter to the ArrayList above
        final EquipmentAdapter adapter = new EquipmentAdapter(getContext(), equipmentList);
        // This links the adapter to the List in the xml
        list.setAdapter(adapter);

        return view;
    }

    //EquipmentAdapter for the listview
    public class EquipmentAdapter extends ArrayAdapter<Equipment> {

        public EquipmentAdapter(Context context, ArrayList<Equipment> items) {
            super(context, 0, items);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            final Equipment item = getItem(position);

            if(convertView == null){
                convertView =
                        LayoutInflater.from(getContext()).inflate(
                                R.layout.item_view, parent, false);
            }
            // This is where we set the name textview value
            TextView itemName = (TextView) convertView.findViewById(R.id.equipmentName);
            itemName.setText(item.getName());
            // This will be the image that opens a new page when tapped(Globe icon)
//            ImageView image = (ImageView) convertView.findViewById(R.id.location);
//            image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Uri geoLocation = Uri.parse(item.getLocation());
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(geoLocation);
//                    if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
//                        startActivity(intent);
//                    }
//                }
//            });

            return  convertView;
        }
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
