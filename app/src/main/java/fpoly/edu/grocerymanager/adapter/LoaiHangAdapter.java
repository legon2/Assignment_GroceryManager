package fpoly.edu.grocerymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fpoly.edu.grocerymanager.R;
import fpoly.edu.grocerymanager.fragment.LoaiHangFragment;
import fpoly.edu.grocerymanager.model.LoaiHang;

public class LoaiHangAdapter extends ArrayAdapter<LoaiHang> {
    private Context context;
    LoaiHangFragment fragment;
    private ArrayList<LoaiHang> lists;
    TextView tvMaLoaiHang, tvTenLoaiHang;
    ImageView imgDel;

    public LoaiHangAdapter(@NonNull Context context, LoaiHangFragment fragment, ArrayList<LoaiHang> lists) {
        super(context, 0,lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_hang_item,null);
        }
        final LoaiHang item = lists.get(position);
        if (item != null){
            //tvMaLoaiHang = v.findViewById(R.id.tvMaLoaiHang);
           // tvMaLoaiHang.setText("Mã loại: "+item.getMaLoai());
            tvTenLoaiHang = v.findViewById(R.id.tvTenLoaiHang);
            tvTenLoaiHang.setText("Tên loại: "+item.getTenLoai());
            imgDel = v.findViewById(R.id.imgDeleteLS);

        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.getMaLoai()));

            }
        });

        return v;

    }
}
