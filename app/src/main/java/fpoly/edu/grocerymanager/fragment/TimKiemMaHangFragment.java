package fpoly.edu.grocerymanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import fpoly.edu.grocerymanager.R;

public class TimKiemMaHangFragment extends Fragment {

    public TimKiemMaHangFragment() {
        // Required empty public constructor
    }
    public static TimKiemMaHangFragment newInstance() {
        TimKiemMaHangFragment fragment = new TimKiemMaHangFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tim_kiem_ma_hang, container, false);
    }
}