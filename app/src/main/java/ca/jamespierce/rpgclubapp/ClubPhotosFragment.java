package ca.jamespierce.rpgclubapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static ca.jamespierce.rpgclubapp.MainActivity.fab;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClubPhotosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClubPhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubPhotosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int CAMERA_INTENT = 1;
    private String imageLocation;

    private OnFragmentInteractionListener mListener;

    public ClubPhotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClubPhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClubPhotosFragment newInstance(String param1, String param2) {
        ClubPhotosFragment fragment = new ClubPhotosFragment();
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
        View view =  inflater.inflate(R.layout.fragment_club_photos, container, false);

        fab.show();

        fab.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File picture = null;
                try{
                    picture = createImage();
                }catch(IOException e){
                    e.printStackTrace();
                }
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                if(i.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(i, CAMERA_INTENT);
                }
            }
        });





        return view;
    }

    /**
     * description This is the adapter class I use for the messages and the RecyclerView.
     */
    public class PicturesAdapter extends
            RecyclerView.Adapter<PicturesAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Declare variables for the items to display in each row
            public CardView picture;

            // Constructor for the view
            public ViewHolder(View itemView) {
                super(itemView);

                picture = (CardView) itemView.findViewById(R.id.clubPhotoFrame);
            }
        }

        // Creates a list to store the all of the messages
        private List<Picture> mPictures;
        private Context mContext;

        // Pass the array of messages
        public PicturesAdapter(Context context, List<Picture> pictures) {
            mPictures = pictures;
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
        public PicturesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View contactView = inflater.inflate(R.layout.picture_view, parent, false);

            PicturesAdapter.ViewHolder viewHolder = new PicturesAdapter.ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PicturesAdapter.ViewHolder viewHolder, int position) {
            // Gets the item from the current position

            //TODO This may need to be redone later if it doesn't work as intended
//            Picture currentPicture = mPictures.get(position);
            DatabaseHandler db = new DatabaseHandler(getContext());
            Picture picture = db.getPicture(position);


            // This will set the id of the image in each cardview to be displayed
            CardView pictureHolder = viewHolder.picture;

            Bitmap image = BitmapFactory.decodeFile(picture.getResource());
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(image);
            imageView.setAdjustViewBounds(true);
            pictureHolder.addView(imageView);

        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return mPictures.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_INTENT && resultCode == RESULT_OK){
            Bitmap image = BitmapFactory.decodeFile(imageLocation);
            /*
             * This is the part of the code where it originally added the image to the screen.
             * Because I am adding mine to a Database, then to a list view which can be refreshed,
             * I should not need to do this here.
             */
            /**
             * Add the photo to the database
             */
            DatabaseHandler db = new DatabaseHandler(getContext());

            int picID = db.addPicture(new Picture(imageLocation));
            if(picID != -1){
                Toast.makeText(getActivity(), "Photo Added",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getActivity(), "Photo Not Added",
                        Toast.LENGTH_LONG).show();
            }

            /*
             * Refer to the Chat window to see how to refresh list content.
             * This needs to be done after the image has been added to the database.
             */
            db.closeDB();
        }
    }

    /**
     * This method is used to create a temp
     * file that the Camera will use to save a photo
     * We will generate a file with a collision free name
     * (rpg_club_app_photo_timestamp.jpg)
     * This method throws an IOException because we might not
     * be able to create the file
     * @return
     * @throws IOException
     */
    File createImage() throws IOException{
        //Create a timestamp to help create a collision free name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHss").format(new Date());
        //Create the name of the image
        String fileName = "rpg_club_app_photo_" + timeStamp;
        //Grab the directory we want to save the image
        File directory =
                Environment.
                        getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_PICTURES);
        //Create the image in that directory
        File picture = File.createTempFile(fileName, ".jpg", directory);
        //Save the location of the image
        imageLocation = picture.getAbsolutePath();
        return picture;
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
