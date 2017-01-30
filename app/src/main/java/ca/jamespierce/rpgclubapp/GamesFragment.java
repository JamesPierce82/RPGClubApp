package ca.jamespierce.rpgclubapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GamesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Declare variables for the views and variables used
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    CircleIndicator indicator;
    private GamesSectionPagerAdapter sectionPagerAdapter;

    private OnFragmentInteractionListener mListener;

    public GamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamesFragment newInstance(String param1, String param2) {
        GamesFragment fragment = new GamesFragment();
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
     * if the floating action button is showing, hide it. It connects the variables created above
     * to the actual views, and sets adapters as necessary.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_games, container, false);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab.isShown()) {
            fab.hide();
        }
        sectionPagerAdapter = new GamesSectionPagerAdapter((getChildFragmentManager()));
        viewPager = (ViewPager) view.findViewById(R.id.gamecontent);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(sectionPagerAdapter);
        indicator.setViewPager(viewPager);
        
        return view;
    }


    /**
     * This is the ZoomOutPageTransormer. It will modify how the pages in the ViewPager will appear and disappear
     * on the screen as the user swipes and preses the left and right buttons.
     */
    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * This is the GamesSectionPagerAdapter for the ViewPager. It selects which content to display when the user swipes.
     */
    public class GamesSectionPagerAdapter extends FragmentPagerAdapter {

        public GamesSectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         *
         * @param position
         * @return GameFragment.newInstance - This will return a new GameFragment with the information that is passed through the newInstance method
         * description This will return a fragment based on which position is currently selected by the ViewPager.
         */
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                /* Using the Looping ViewPager from https://github.com/ongakuer/CircleIndicator
                 * This won't go to case 0. It starts at case 1 and rotates from there.
                 * case 0 is the same as case 5 so that when it loops it properly pulls up the case 5 content without
                 * having a weird loading glitch.
                 * Default case should be the same as case 1, as the reset first loads the default and THEN loads case 1.
                 * Change this to fix weird loading issues.*/
                case 0:
                case 5:
                    return GameFragment.newInstance("DarkWalk", "A stealth based game where you take missions from contractors and attempt to complete them. This plays very different from the smash-em up games at the top of this list.", R.drawable.stealth, "7pm - 10pm Sunday");
                case 1:
                case 6:
                    return GameFragment.newInstance("Orbs and Orcs", "This is a game that revolves around party play, dungeon diving, and ruining the lives of all the non-fairy like creatures in the world of Karrova.", R.drawable.notebook, "4pm - 10pm Tuesday");
                case 2:
                    return GameFragment.newInstance("Trailspotter", "Similar in genre to Orbs and Orcs, this game allows players to run around the world of Rokavva and do lots and lots of fetch quests. It's like an MMORPG, but with 5 people in person!", R.drawable.trail, "6pm - 12pm Wednesday");
                case 3:
                    return GameFragment.newInstance("Call to ChooChoo", "This is a thriller/horror style game where the players band together against insanity, and battle the monsters of the night. All games must take place on a train.", R.drawable.train, "6pm - 9pm Friday");
                case 4:
                    return GameFragment.newInstance("DESTINY", "This is a game that centers around beating things up. Every character has a predetermined destiny, its up to the players to fulfill it... or die in the trying!", R.drawable.dice2, "6pm - 2am Saturday");
                default:
                    return GameFragment.newInstance("This should never show up", "Or this", R.drawable.dice, "Never");
            }
        }

        @Override
        public int getCount() {
            return 5;
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
