package declatoration.simulatorial.fight;

import static declatoration.simulatorial.enums.stat.ATK;
import static declatoration.simulatorial.enums.stat.BLOCK;
import static declatoration.simulatorial.enums.stat.CRIT;
import static declatoration.simulatorial.enums.stat.DEF;
import static declatoration.simulatorial.enums.stat.HIT;
import static declatoration.simulatorial.enums.stat.INT;
import static declatoration.simulatorial.enums.stat.LCK;
import static declatoration.simulatorial.enums.stat.MAG;
import static declatoration.simulatorial.enums.stat.MAGAVO;
import static declatoration.simulatorial.enums.stat.PHYSAVO;
import static declatoration.simulatorial.enums.stat.RES;
import static declatoration.simulatorial.enums.stat.SPD;
import static declatoration.simulatorial.enums.stat.SPREGEN;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

import declatoration.simulatorial.enums.stat;

public class battleTokenParcelable implements Parcelable {

    String name;

    int hpVal;
    int maxHPVal;
    int spVal;
    int maxSPVal;

    ArrayList<String> actionsPicked;
    float[] statArray = new float[16];



    public battleTokenParcelable(battleToken bt){
        name = bt.getName();

        hpVal = bt.getHP();
        maxHPVal = bt.getHPMax();

        spVal = bt.getSP();
        maxSPVal = bt.getSPMax();

        actionsPicked = bt.getChars().selectedActionNames;

        createStatArray(bt.getChars().getStatBoard());
    }

    private void createStatArray(HashMap<stat, Float> statBoard) {
        for (int i = 0; i < statBoard.size(); i++) {
            statArray[i] = statBoard.get(stat.statArray[i]);
        }
    }


    protected battleTokenParcelable(Parcel in) {
        name = in.readString();
        hpVal = in.readInt();
        maxHPVal = in.readInt();
        spVal = in.readInt();
        maxSPVal = in.readInt();
        actionsPicked = in.createStringArrayList();
        statArray = in.createFloatArray();
    }

    public static final Creator<battleTokenParcelable> CREATOR = new Creator<battleTokenParcelable>() {
        @Override
        public battleTokenParcelable createFromParcel(Parcel in) {
            return new battleTokenParcelable(in);
        }

        @Override
        public battleTokenParcelable[] newArray(int size) {
            return new battleTokenParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(hpVal);
        dest.writeInt(maxHPVal);
        dest.writeInt(spVal);
        dest.writeInt(maxSPVal);
        dest.writeStringList(actionsPicked);
        dest.writeFloatArray(statArray);
    }

    public boolean getIsAlive(){
        return hpVal > 0;
    }

    public String getName(){return name;}

    public String getDBString(){

        String finalName = name;
        if(name.contains("'")){
            finalName = name.substring(0, name.indexOf("'"));
            finalName += "'";
            finalName += name.substring(name.indexOf("'"));
        }
        return "('" + finalName + "', '"+ hpVal + "', '" + maxHPVal + "', '"+ spVal+ "', '" + maxSPVal +
                "', '"+ statArray[12] + "', '"+ statArray[1] + "', '"+ statArray[2] +
                "', '"+ statArray[3] +"', '"+ statArray[4] +"', '"+ statArray[5] +"', '"+ statArray[6] +
                "', '"+ statArray[7] +"', '"+ statArray[8] +"', '"+ statArray[9] +
                "', '"+ statArray[10] +"', '"+ statArray[13] + "', '"+ statArray[14] + "')";
    }

}
