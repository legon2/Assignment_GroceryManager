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
import fpoly.edu.grocerymanager.model.LoaiHang;

public class LoaiHangSpinnerAdapter extends ArrayAdapter<LoaiHang> {
    Context context;
    ArrayList<LoaiHang> lists;
    TextView tvMaLoaiHang, tvTenLoaiHang;

    public LoaiHangSpinnerAdapter(@NonNull Context context, ArrayList<LoaiHang> lists) {
        super(context, 0,lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_hang_item_spinner,null);
        }

        final LoaiHang item = lists.get(position);
        if (item != null){
            //tvMaLoaiHang = v.findViewById(R.id.tvMaLoaiHangSp);
            //tvMaLoaiHang.setText(item.getMaLoai() + ". ");

            tvTenLoaiHang = v.findViewById(R.id.tvTenLoaiHangSp);
            tvTenLoaiHang.setText(item.getTenLoai());
        }
        return v ;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull  ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_hang_item_spinner,null);
        }

        final LoaiHang item = lists.get(position);
        if (item != null){
            //tvMaLoaiHang = v.findViewById(R.id.tvMaLoaiHangSp);
            //tvMaLoaiHang.setText(item.getMaLoai() + ". ");

            tvTenLoaiHang = v.findViewById(R.id.tvTenLoaiHangSp);
            tvTenLoaiHang.setText(item.getTenLoai());

        }
        return v;
    }
}
