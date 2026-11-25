package declatoration.simulatorial.prep;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import declatoration.simulatorial.R;
import declatoration.simulatorial.databaseExec;
import declatoration.simulatorial.fight.sounds.soundmanDan2;


public class onset extends AppCompatActivity {

    ArrayList <onsetCharRow> rows = new ArrayList<>(5);

    LinearLayout rowHolder;
    ImageButton back, start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onset);

        rowHolder = findViewById(R.id.onset_char_row_holder);

        start = findViewById(R.id.onset_start_button);
        back = findViewById(R.id.onset_back_button);


        databaseExec dbExec = databaseExec.getInstance(getBaseContext());
        ArrayList<String> map = dbExec.getOnsetPickableCharNames();

        ArrayList<String> allPlayerNames = new ArrayList<>(map);


        charSelectionArrayAdapter rowAdapter = new charSelectionArrayAdapter(getBaseContext(), allPlayerNames);

        for (int i = 0; i < 5; i++) {
            rows.add(i, new onsetCharRow(getBaseContext()));
            rows.get(i).init(i + 1, rowAdapter);

            rowHolder.addView(rows.get(i));
        }


        rows.get(0).spinner.setSelection(rowAdapter.getPosition("Lukio"));
        ((BaseAdapter) rows.get(0).spinner.getAdapter()).notifyDataSetChanged();
        rows.get(1).spinner.setSelection(rowAdapter.getPosition("Lukia"));
        ((BaseAdapter) rows.get(1).spinner.getAdapter()).notifyDataSetChanged();
        rows.get(2).spinner.setSelection(rowAdapter.getPosition("Penselle"));
        ((BaseAdapter) rows.get(2).spinner.getAdapter()).notifyDataSetChanged();
        rows.get(3).spinner.setSelection(rowAdapter.getPosition("Reika"));
        ((BaseAdapter) rows.get(3).spinner.getAdapter()).notifyDataSetChanged();
        rows.get(4).spinner.setSelection(rowAdapter.getPosition("Saki"));
        ((BaseAdapter) rows.get(4).spinner.getAdapter()).notifyDataSetChanged();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> charNames = new ArrayList<>();
                for (int i = 0; i < rows.size(); i++) {
                    charNames.add(rows.get(i).getName());
                }

                if(!charNames.contains(null)){
                    if(!checkDupes(charNames)){

                        start.setClickable(false);

                        sonicJammer.playSound("big_select", getBaseContext());

                        soundmanDan2.fadeOutMusic faderr = new soundmanDan2.fadeOutMusic(false, false);
                        Thread thread = new Thread(faderr);
                        thread.start();


                        try {
                            synchronized (this) {
                                thread.join();
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        Intent intent = new Intent(onset.this, warRoom.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        intent.setFlags(Intent.AC);

                        intent.putStringArrayListExtra("initialCharNames", charNames);

                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getBaseContext(), "Please Select 5 UNIQUE characters!", Toast.LENGTH_SHORT).show();
                    }
                } else{

                    Toast.makeText(getBaseContext(), "Please Select 5 characters!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundmanDan2.fadeOutMusic fade = new soundmanDan2.fadeOutMusic(true, false);
                Thread thread = new Thread(fade);
                thread.start();

                try {
                    synchronized (this){
                        thread.join();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                onBackPressed();
            }
        });

        sonicJammer = new soundmanDan2(getBaseContext(), "onset", false);
    }

    protected boolean checkDupes(ArrayList<String> names) {
        boolean dupes = false;

        for (int i = 0; i < names.size(); i++) {
            for (int j = i + 1; j < names.size(); j++) {
                if(Objects.equals(names.get(i), names.get(j))){
                    dupes = true;
                    break;
                }
            }
        }

        return dupes;
    }

    @Override
    protected void onStop() {
        super.onStop();

//        soundmanDan2.fadeOutMusic fade = new soundmanDan2.fadeOutMusic(true, false);
//        Thread thread = new Thread(fade);
//        thread.start();
    }
}