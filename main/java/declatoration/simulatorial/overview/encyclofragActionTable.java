package declatoration.simulatorial.overview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

import java.util.ArrayList;

import declatoration.simulatorial.actionDatabase;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.charSkill;
import declatoration.simulatorial.databaseExec;
import declatoration.simulatorial.numDatabase;

public class encyclofragActionTable extends ScrollView {

    TableLayout tableLayout;

    boolean isSkillTable;
    String charName;

    actionDescRows[] actionRows = new actionDescRows[25];
    skillDescRows[] skillRows = new skillDescRows[15];

    ArrayList<charAction> charActionsArrayList = new ArrayList<>();
    ArrayList<charSkill> charSkillArrayList = new ArrayList<>();

    databaseExec dbExec;


    public encyclofragActionTable(Context context, AttributeSet attrs){
        super(context, attrs);

        dbExec = databaseExec.getInstance(getContext());

        tableLayout = new TableLayout(getContext());
        addView(tableLayout);
    }

    public encyclofragActionTable(Context context, String charName, boolean isSkillTable) {
        super(context);
        bestart(charName, isSkillTable);
    }

    public void bestart(String charName, boolean isSkillTable){

        tableLayout.setOrientation(LinearLayout.VERTICAL);
        tableLayout.setLayoutParams(new FrameLayout.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.MATCH_PARENT));
//        tableLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        this.isSkillTable = isSkillTable;
        this.charName = charName;

        if (isSkillTable){
            this.charSkillArrayList = dbExec.getCharSkills(charName, 200);
            initializeSkillTable();
        }
        else {
            this.charActionsArrayList = dbExec.getAllCharUniqueActions(charName, 200);
            this.charActionsArrayList.remove(0);
            initializeActionTable();
        }

    }
    public void initializeSkillTable(){
        for (int i = 0; i < charSkillArrayList.size(); i++) {
            skillRows[i] = new skillDescRows(getContext(), charSkillArrayList.get(i));
            tableLayout.addView(skillRows[i]);

            View linebreak = new View(getContext());
            linebreak.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            linebreak.setBackgroundColor(Color.parseColor("#000000"));
            tableLayout.addView(linebreak);
        }
    }

    public void initializeActionTable(){
        for (int i = 0; i < charActionsArrayList.size(); i++) {
            actionRows[i] = new actionDescRows(getContext(), charActionsArrayList.get(i));
            tableLayout.addView(actionRows[i]);

            View linebreak = new View(getContext());
            linebreak.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            linebreak.setBackgroundColor(Color.parseColor("#000000"));
            tableLayout.addView(linebreak);
        }
    }


}
