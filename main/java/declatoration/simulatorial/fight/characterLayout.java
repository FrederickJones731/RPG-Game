package declatoration.simulatorial.fight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import declatoration.simulatorial.R;


// connect to the character database to detect image

public class characterLayout extends ConstraintLayout {

    charClickedBridger clickedInterface;

    boolean isTargetable, focused;
    int serialNumber;

    View imageFrame, root;
    ImageView image, affIcon, weaponIcon, statusIcon;
    smallBar hpbar, spbar;

//    Animation animation;


    public characterLayout(Context context){
        super(context);
    }
    public characterLayout(@NonNull Context context,
                           charClickedBridger charClickedBridger, int serialNumber, int backgroundResId) {
        super(context);

        init(charClickedBridger, serialNumber, backgroundResId);
    }

    public characterLayout(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(charClickedBridger charClickedBridger, int serialNumber, int backgroundResId) {
        clickedInterface = charClickedBridger;

        root = inflate(getContext(), R.layout.layout_character, this);

        this.hpbar = root.findViewById(R.id.pc_hp_bar);
        this.spbar = root.findViewById(R.id.pc_sp_bar);

        this.image = root.findViewById(R.id.pc_img);
        this.imageFrame = root.findViewById(R.id.pc_img_frame);
        affIcon = root.findViewById(R.id.pc_affinity_icon);
        weaponIcon = root.findViewById(R.id.pc_weapon_icon);
        statusIcon = root.findViewById(R.id.pc_status_icon);


        this.hpbar.initialize(true);
        this.spbar.initialize(false);


        this.isTargetable = false;
        this.focused = false;
        this.serialNumber = serialNumber;


        this.setOnClickListener(v -> {
            if (!focused) {
                focused = true;
                clickedInterface.clicked(this.serialNumber);
            } else {
                if(isTargetable) {
                    clickedInterface.clicked(-1);
                } else {
                    clickedInterface.clicked(this.serialNumber);
                }
            }

        });

        this.setOnLongClickListener(v -> {

            try{
                clickedInterface.inflateStatCard(serialNumber);

                return true;

            } catch (Exception e){
                e.printStackTrace();
            }

            return false;
        });

    }


    public ImageView getBackgroundImageView(){return this.image;}

//    public boolean isTargetable() { return isTargetable;}

    public void setFocused(boolean b) {
        this.focused = b;
    }
    public void setIsTargetable(boolean b) {this.isTargetable = b;}

}
