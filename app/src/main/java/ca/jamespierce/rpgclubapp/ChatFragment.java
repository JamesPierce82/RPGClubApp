package ca.jamespierce.rpgclubapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Declare the ListView
    ListView list;



    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        //*** THIS SHOULD BE A RECYCLER VIEW BASED ON THE PREMISE OF A CHAT WINDOW
        // Links the ListView programmatically to the list variable
        list = (ListView) view.findViewById(R.id.chatList);
        // Create an ArrayList that holds messages
        final ArrayList<Message> messageList = new ArrayList<Message>();

        // Add messages to the arraylist
        // Passes Name, Time set, Message, and the ID of the image to be used as an avatar
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset, I need this to say a lot more than it does currently. This really needs to run off the page so that I can see if it properly wraps each message.", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset, I need this to say a lot more than it does currently. This really needs to run off the page so that I can see if it properly wraps each message.", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset", R.drawable.dice));
        messageList.add(new Message("James", "2017-15-01 12:03pm", "Hello this is a tset, I need this to say a lot more than it does currently. This really needs to run off the page so that I can see if it properly wraps each message.", R.drawable.dice));

        final CustomAdapter adapter = new CustomAdapter(getContext(), messageList);

        // Set the adapter for the list displayed in the GUI
        list.setAdapter(adapter);
        // Unlike the other apps we have done, This one does not require a onclick listener currently.
        // It does not need to perform any actions when touching the message.
        // *** ADD IN SNACKBAR MESSAGE WHEN TAPPING TO INDICATE FUTURE FUNCTIONALITY

        return view;
    }

    // Create the CustomAdapter class
    public class CustomAdapter extends ArrayAdapter<Message> {

        public CustomAdapter(Context context, ArrayList<Message> items) {
            super(context, 0, items);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            Message item = getItem(position);

            if(convertView == null){
                convertView =
                        LayoutInflater.from(getContext()).inflate(
                                R.layout.item_view, parent, false);
            }

            // Set the text values for the message being displayed
            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(item.getName());
            TextView time = (TextView) convertView.findViewById(R.id.time);
            time.setText(item.getTimeSent());
            TextView message = (TextView) convertView.findViewById(R.id.message);
            message.setText(item.getContent());

            // Sets the avatar using the resource id of the drawable image stored in the message
            ImageView avatar = (ImageView)convertView.findViewById(R.id.avatar);
            avatar.setImageResource(item.getAvatar());

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
