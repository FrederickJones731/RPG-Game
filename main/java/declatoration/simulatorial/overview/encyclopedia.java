package declatoration.simulatorial.overview;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

import declatoration.simulatorial.R;
import declatoration.simulatorial.numDatabase;

public class encyclopedia extends AppCompatActivity {



    ImageButton back;
//    LinearLayout drawerPlayerCharacterList, drawerEnemyList;
//    SlidingDrawer slidingLayer;

    ViewPager2 viewPager2;
    ViewPager2.OnPageChangeCallback pageChangeCallback;
    encyclodapter fragmentAdapter;

    FloatingActionButton floatingActionButton;
    boolean drawerOpen = false;

    numDatabase database;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);

        viewPager2 = findViewById(R.id.pager_of_views);

        floatingActionButton = findViewById(R.id.fab);
        back = findViewById(R.id.back_button);

        final HorizontalScrollView scrollView = findViewById(R.id.background_sv);
        final ImageView imageView = findViewById(R.id.background);

//        slidingLayer = findViewById(R.id.drawer_menu);
//        drawerPlayerCharacterList = findViewById(R.id.drawer_player_chars_ll);
//        drawerEnemyList = findViewById(R.id.drawer_enemy_chars_ll);


        database = new numDatabase(getBaseContext());

        HashMap<Integer, String> charNames = database.getAllPlayerCharNames();
        HashMap<Integer, String> enemyNames = database.getAllEnemyNames();

//        for (int i = 0; i < charNames.size(); i++) {
//            final drawerTV tv = new drawerTV(getBaseContext());
//            tv.init(getBaseContext(), charNames.get(i), i);
//            tv.setOnClickListener(v -> initializeFrags(tv.name));
//
//            drawerPlayerCharacterList.addView(tv);
//        }
//        for (int i = 0; i < enemyNames.size(); i++) {
//            final drawerTV tv = new drawerTV(getBaseContext());
//            tv.init(getBaseContext(), enemyNames.get(i), i);
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    initializeFrags(tv.name);
//                }
//            });
//
//            drawerEnemyList.addView(tv);
//        }



        pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageSelected(position);

                int x = (int) ((viewPager2.getWidth() * position + positionOffsetPixels) * computeFactor());
                scrollView.scrollTo(x, 0);
            }


            private float computeFactor() {
                return (imageView.getWidth() - viewPager2.getWidth()) /
                        (float)(viewPager2.getWidth() * (viewPager2.getAdapter().getItemCount() - 1));
            }
        };
        viewPager2.registerOnPageChangeCallback(pageChangeCallback);


        back.setOnClickListener(v -> onBackPressed());

        floatingActionButton.setOnClickListener(v -> {
//            if(drawerOpen) {
////                slidingLayer.animateToggle();
//                drawerOpen = !drawerOpen;
//                viewPager2.setClickable(true);
//            } else{
////                slidingLayer.animateToggle();
//                drawerOpen = !drawerOpen;
//                viewPager2.setClickable(false);
//            }

        });



        initializeFrags(charNames.get(0));
    }

    public void initializeFrags(String name){

        fragmentAdapter = new encyclodapter(getSupportFragmentManager(), getLifecycle());

        Bundle b = new Bundle();
        b.putString("charName", name);

        encyclofrag_stats statFrag = new encyclofrag_stats();
        encyclofrag_bases basesFrag = new encyclofrag_bases();
        encyclofrag_actions actionFrag = new encyclofrag_actions();
        encyclofrag_skills skillFrag = new encyclofrag_skills();

        statFrag.setArguments(b);
        basesFrag.setArguments(b);
        actionFrag.setArguments(b);
        skillFrag.setArguments(b);

        fragmentAdapter.addFragment(statFrag);
        fragmentAdapter.addFragment(basesFrag);
        fragmentAdapter.addFragment(actionFrag);
        fragmentAdapter.addFragment(skillFrag);

        viewPager2.setAdapter(fragmentAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setPageTransformer(new MarginPageTransformer(300));
    }


    @Override
    protected void onDestroy() {
        viewPager2.unregisterOnPageChangeCallback(pageChangeCallback);

        super.onDestroy();
    }
}