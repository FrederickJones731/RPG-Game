package declatoration.simulatorial.overview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import declatoration.simulatorial.R;
import declatoration.simulatorial.charSkill;

public class skillDescRows extends TableRow {


    charSkill curSkill;

    ImageView icon;
    TextView skillName, skillDesc;

    //ToDo - icons here as well?
    public skillDescRows(Context context, charSkill skill) {
        super(context);
        curSkill = skill;

        init();
    }


    public void init(){
        inflate(getContext(), R.layout.skill_desc_row, this);


        icon = findViewById(R.id.skill_icon);
        skillName = findViewById(R.id.skill_name);
        skillDesc = findViewById(R.id.skill_desc);


        icon.setBackgroundColor(curSkill.getIconResource());

        skillName.setText(curSkill.getName());
        skillDesc.setText(curSkill.getSkillDesc());

    }




}
