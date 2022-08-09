package fpoly.edu.grocerymanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import fpoly.edu.grocerymanager.R;


public class TimKiemLoaiHangFragment extends Fragment {
    private ActionBar toolbar;

    public TimKiemLoaiHangFragment() {
        // Required empty public constructor
    }
    public static TimKiemLoaiHangFragment newInstance(){
        return new TimKiemLoaiHangFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tim_kiem_loai_hang, container, false);
    }
}