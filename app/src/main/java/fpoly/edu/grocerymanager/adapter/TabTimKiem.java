package fpoly.edu.grocerymanager.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fpoly.edu.grocerymanager.fragment.TimKiemLoaiHangFragment;
import fpoly.edu.grocerymanager.fragment.TimKiemMaHangFragment;

public class TabTimKiem extends FragmentStateAdapter {
    public TabTimKiem(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if(position == 0){
            fragment = TimKiemLoaiHangFragment.newInstance();
        } else {
            fragment= TimKiemMaHangFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
