package declatoration.simulatorial.overview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import declatoration.simulatorial.R;

public class drawerTV extends ConstraintLayout {

    String name, num;
    TextView tv, numTV;

    public drawerTV(Context c){
        super(c);
    }

    public drawerTV(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void init(Context context, String name, int num){

        this.name = name;
        this.num = String.valueOf(num);

        inflate(context, R.layout.drawer_name_tv, this);

        tv = findViewById(R.id.tv);
        numTV = findViewById(R.id.num_tv);

        tv.setText(name);
        numTV.setText(String.valueOf(num));
    }



}
