package declatoration.simulatorial.prep;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class prepadapter extends FragmentStateAdapter {

    final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    public prepadapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void addFragment(Fragment fragment){
        fragmentArrayList.add(fragment);
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrayList.get(position);
    }

}

