package fpoly.edu.grocerymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fpoly.edu.grocerymanager.R;
import fpoly.edu.grocerymanager.model.Hang;

public class HangSpinnerAdapter extends ArrayAdapter<Hang> {

    private Context context;
    private ArrayList<Hang> lists;
    TextView tvMaHang, tvTenHang;

    public HangSpinnerAdapter(@NonNull Context context, ArrayList<Hang> lists) {
        super(context, 0,lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v  =convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.hang_item_spinner,null);
        }
        final Hang item = lists.get(position);
        if (item != null){
            tvMaHang = v.findViewById(R.id.tvMaHangSp);
            tvMaHang.setText(item.getMaHang() + ". ");
            tvTenHang = v.findViewById(R.id.tvTenHangSp);
            tvTenHang.setText(item.getTenHang());
        }

        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable  View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.hang_item_spinner,null);
        }
        final Hang item = lists.get(position);
        if (item != null){
            tvMaHang = v.findViewById(R.id.tvMaHangSp);
            tvMaHang.setText(item.getMaHang() + ". ");
            tvTenHang = v.findViewById(R.id.tvTenHangSp);
            tvTenHang.setText(item.getTenHang());
        }
        return v;
    }
}
