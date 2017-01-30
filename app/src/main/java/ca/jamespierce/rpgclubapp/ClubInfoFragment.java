package ca.jamespierce.rpgclubapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClubInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClubInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private MemberSectionPagerAdapter mSectionPagerAdapter;

    private OnFragmentInteractionListener mListener;

    public ClubInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClubInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClubInfoFragment newInstance(String param1, String param2) {
        ClubInfoFragment fragment = new ClubInfoFragment();
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
     * @return
     *
     * description This will be used to interact with all of the elements in this fragment upon their creation
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_info, container, false);

        // This will set the PagerAdapter to the adapter built for the Member information
        mSectionPagerAdapter = new MemberSectionPagerAdapter((getChildFragmentManager()));
        viewPager = (ViewPager) view.findViewById(R.id.membercontent);
        viewPager.setAdapter(mSectionPagerAdapter);

        return view;
    }

    public class MemberSectionPagerAdapter extends FragmentPagerAdapter {
        public MemberSectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return MemberFragment.newInstance("James Pierce", "President", R.drawable.james);
                case 1:
                    return MemberFragment.newInstance("Charlie Tonka", "Vice-President", R.drawable.charlie);
                case 2:
                    return MemberFragment.newInstance("Emily Willis", "Member", R.drawable.emily);
                case 3:
                    return MemberFragment.newInstance("Bob Robert", "Member", R.drawable.bob);
                case 4:
                    return MemberFragment.newInstance("George Shrub", "Member", R.drawable.george);
                case 5:
                    return MemberFragment.newInstance("Sally Cobert", "Member", R.drawable.sally);
                default:
                    return MemberFragment.newInstance("Charlie Tonka", "Vice-President", R.drawable.charlie);
            }
        }

        @Override
        public int getCount() {
            return 6;
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
