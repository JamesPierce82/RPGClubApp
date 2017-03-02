package ca.jamespierce.rpgclubapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    MainFragment.OnFragmentInteractionListener,
                    ChatFragment.OnFragmentInteractionListener,
                    ClubInfoFragment.OnFragmentInteractionListener,
                    GameFragment.OnFragmentInteractionListener,
                    GamesFragment.OnFragmentInteractionListener,
                    MemberFragment.OnFragmentInteractionListener,
                    EquipmentFragment.OnFragmentInteractionListener{

    FragmentManager fm = getSupportFragmentManager();
    public static FloatingActionButton fab;
    public String phoneNumber = "5195195199";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            FragmentTransaction tran = fm.beginTransaction();
            tran.replace(R.id.content_main, new MainFragment());
            tran.commit();
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param item
     * @return
     * This will allow the user to select an item from the nav menu based on the id selected.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction tran = fm.beginTransaction();
        tran.setCustomAnimations(R.anim.zoominanimation, R.anim.zoomoutanimation);
        if (id == R.id.nav_home) {
            tran.replace(R.id.content_main, new MainFragment());
            tran.commit();
        } else if (id == R.id.nav_games) {
            tran.replace(R.id.content_main, new GamesFragment());
            tran.commit();
        } else if (id == R.id.nav_equipment) {
            tran.replace(R.id.content_main, new EquipmentFragment());
            tran.commit();
        } else if (id == R.id.nav_chat) {
            tran.replace(R.id.content_main, new ChatFragment());
            tran.commit();
        } else if (id == R.id.nav_about) {
            tran.replace(R.id.content_main, new ClubInfoFragment());
            tran.commit();
        } else if (id == R.id.nav_call) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else if (id == R.id.nav_email) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "james.pierce82@stclairconnect.ca");
            intent.putExtra(Intent.EXTRA_SUBJECT, "RPG Club App");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else if (id == R.id.nav_databaseSetup) {
            // Add all entries to the database.
            DatabaseHandler db = new DatabaseHandler(getBaseContext());

            // Add all users required for the chat client to the Users table
            db.addUser(new User("Bob", R.drawable.bob));
            db.addUser(new User("James", R.drawable.james));
            db.addUser(new User("Charlie", R.drawable.charlie));
            db.addUser(new User("Emily", R.drawable.emily));
            db.addUser(new User("George", R.drawable.george));
            db.addUser(new User("Sally", R.drawable.sally));

            // Add all the messages required for the chat client to the Messages table
            db.addMessage(new Message("Jan 10, 2017 1:03pm", "Hey guys, welcome to the app! We can communicate in here about what we're doing each week, or just whatever.", 2));
            db.addMessage(new Message("Jan 12, 2017 2:22pm", "This is pretty cool, can we talk about other things besides club stuff?", 1));
            db.addMessage(new Message("Jan 12, 2017 2:23pm", "Sure, why not! Just keep it appropriate.", 2));
            db.addMessage(new Message("Jan 13, 2017 9:54am", "When is the next Orbs and Orcs night?", 3));
            db.addMessage(new Message("Jan 13, 2017 10:27pm", "Thats a week from tuesday, starting at 7pm. We usually go until 1 or 2 am so I hope you're up for a late night and early morning! I know you have class at 8am the next day.", 1));
            db.addMessage(new Message("Jan 13, 2017 10:33pm", "Awesome. Put me down as being there, I'll bring some nacho dip.", 3));
            db.addMessage(new Message("Jan 15, 2017 3:33am", "Does anyone know when the android homework is due? I thought it was due thursday", 1));
            db.addMessage(new Message("Jan 15, 2017 9:44am", "It was supposed to be due thursday, but only 'tentatively'. He hasn't said for sure yet.", 3));
            db.addMessage(new Message("Jan 15, 2017 10:15am", "Oh, alright. I guess I should get started then eh?", 1));
            db.addMessage(new Message("Jan 16, 2017 12:03pm", "Do you guys still play Trailspotter?", 4));
            db.addMessage(new Message("Jan 16, 2017 12:07pm", "Yes we do, If you open the navigation menu on the left and select the 'RPG Games' tab you can see the different games that we play.", 2));
            db.addMessage(new Message("Jan 16, 2017 12:11pm", "Okay cool. When do you guys play that?", 4));
            db.addMessage(new Message("Jan 17, 2017 1:14am", "We actually have that information listed in the About the Club", 2));
            db.addMessage(new Message("Jan 22, 2017 4:14pm", "Where are the meeting times for Call to ChooChoo? I thought somebody said they were in the About The Club section of that app...", 5));
            db.addMessage(new Message("Jan 22, 2017 4:23pm", "Those were recently moved to the RPG Games section. We figured it would make more sense to have the times listed there instead.", 2));
            db.addMessage(new Message("Jan 22, 2017 4:25pm", "Oh, okay. That does make a lot more sense.", 5));
            db.addMessage(new Message("Jan 22, 2017 4:33pm", "We will be adding information about all of our members to the About The Club section instead soon!", 2));
            db.addMessage(new Message("Jan 24, 2017 5:12pm", "Oh cool! I love the update, it looks like we're all listed now, and it even shows our roles in the club!", 6));
            db.addMessage(new Message("Jan 24, 2017 9:44pm", "Yeah, we worked really hard to get all the information together. We actually draw all that information from our chat system so that it changes dynamically if somebody changes role(if I were to leave the VP role, I would automatically update to become a member)...", 3));
            db.addMessage(new Message("Jan 25, 2017 8:12am", "Very awesome! Looking forward to what you guys add next!", 6));
            db.closeDB();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onFragmentInteraction(Uri uri){


    }
}
