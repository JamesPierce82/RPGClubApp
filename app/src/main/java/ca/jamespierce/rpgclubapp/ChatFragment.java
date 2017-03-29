package ca.jamespierce.rpgclubapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    // Declare variables for views being displayed
    RecyclerView rvMessages;
    Button sendButton;

    // Declare variables for the message content to be transferred
    EditText messageContent;
    String newMessage;
    String currentDateTimeString;

    // Declare variables to store the user's information
    // This will later be used to connect to the settings
    String userName = "John";
    int userAvatar = R.drawable.bob;

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

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     * description This method is what is used to create the views in the fragment. It will hide the
     * floating action button, create a list of messages to place in a Recycler View, add messages
     * to the list and sets the adapters for the list
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();

        // Attempt to move content up when opening the EditText
        // Figured out that this IS WORKING. Proof is that the edit text moves with the keyboard. The issue is that ONLY the editText is moving.
        // May need to figure out how to lock the RecyclerView to the top of the EditText to force it to move, or look into moving to specific
        // items upon opening hte keyboard(This is bad practice).
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        messageContent = (EditText) view.findViewById(R.id.editMessage);
        sendButton = (Button) view.findViewById(R.id.sendButton);
        rvMessages = (RecyclerView) view.findViewById(R.id.chatList);

        DatabaseHandler db = new DatabaseHandler(getContext());
        final ArrayList<Message> messageList = db.getAllMessages();

        db.closeDB();

        final MessagesAdapter adapter = new MessagesAdapter(this.getContext(), messageList);

        // Attach the adapter to the recyclerview to populate items
        rvMessages.setAdapter(adapter);

        // Set layout manager to position the items
        rvMessages.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // This will be used to send a message once the user is ready to send it.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMessage =  messageContent.getText().toString();

                // This will get the current time.
                currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                // This will add the message to the messageList.

                DatabaseHandler db = new DatabaseHandler(getContext());
                db.addMessage(new Message(currentDateTimeString, newMessage, 2));
                messageList.add(new Message(currentDateTimeString, newMessage, 2));

                // This will update the adapter so that the new message will be displayed on the screen
                // This will update the view adapter
                adapter.notifyDataSetChanged();

                // This will clear the editText
                messageContent.setText("");
                // THis forces the app to skip to the newest message after sending a message.
                rvMessages.scrollToPosition(adapter.getItemCount() -1);
            }
        });

        // This should force the app to skip to the bottom of the chat upon loading.
        rvMessages.scrollToPosition(adapter.getItemCount() -1);
        return view;
    }

// Utilizing a guide found on https://guides.codepath.com/android/using-the-recyclerview for RecyclerView
// Need a reycler view because of the content in the list

    /**
     * description This is the adapter class I use for the messages and the RecyclerView.
     * This was put in a separate class file because of the static inner class having issues when in the ChatFragment.
     */
    public class MessagesAdapter extends
            RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Declare variables for the items to display in each row
            public TextView name;
            public TextView time;
            public TextView message;
            public ImageView avatar;

            // Constructor for the view
            public ViewHolder(View itemView) {
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.name);
                time = (TextView) itemView.findViewById(R.id.time);
                message = (TextView) itemView.findViewById(R.id.message);
                avatar = (ImageView) itemView.findViewById(R.id.avatar);
            }
        }

        // Creates a list to store the all of the messages
        private List<Message> mMessages;
        private Context mContext;

        // Pass the array of messages
        public MessagesAdapter(Context context, List<Message> messages) {
            mMessages = messages;
            mContext = context;
        }

        /**
         *
         * @return mContext - Returns the context when needed
         */
        private Context getContext() {
            return mContext;
        }


        @Override
        public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View contactView = inflater.inflate(R.layout.message_view, parent, false);

            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MessagesAdapter.ViewHolder viewHolder, int position) {
            // Gets the item from the current position
            Message message = mMessages.get(position);
            DatabaseHandler db = new DatabaseHandler(getContext());

            // Set the Name for each message based on the userID of the current message
            TextView name = viewHolder.name;

            // This sets it to a string of the user id.
            name.setText((db.getUser(message.getUser_id()).getName()));

            // Set the time sent of the message
            TextView time = viewHolder.time;
            time.setText(message.getTimeSent());

            // Set the content of the message
            TextView messageContent = viewHolder.message;
            messageContent.setText(message.getContent());

            // Sets the avatar using the resource id of the drawable image stored in the message
            ImageView avatar = viewHolder.avatar;
            avatar.setImageResource(db.getUser(message.getUser_id()).getAvatar());
        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return mMessages.size();
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
