package fpoly.edu.grocerymanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.edu.grocerymanager.R;
import fpoly.edu.grocerymanager.dao.HangDAO;
import fpoly.edu.grocerymanager.fragment.QuanLyHoaDonFragment;
import fpoly.edu.grocerymanager.model.Hang;
import fpoly.edu.grocerymanager.model.HoaDon;

public class HoaDonAdapter extends ArrayAdapter<HoaDon> {
    private Context context;
    QuanLyHoaDonFragment fragment;
    private ArrayList<HoaDon> lists;
    TextView tvMaHD, tvTenHang, tvTongTien, tvNgayLap, tvTrangThai;
    ImageView imgDel;
    HangDAO hangDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public HoaDonAdapter(@NonNull Context context, QuanLyHoaDonFragment fragment, ArrayList<HoaDon> lists) {
        super(context, 0,lists);
        this.context = context;
        this.lists = lists;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.hoa_don_item,null);
        }
        final HoaDon item = lists.get(position);
        if (item != null){
            try {
                tvMaHD = v.findViewById(R.id.tvMaHD);
                tvMaHD.setText("Mã hoá đơn: "+item.getMaHD());

                hangDAO = new HangDAO(context);
                Hang hang = hangDAO.getID(String.valueOf(item.getMaHang()));
                tvTenHang = v.findViewById(R.id.tvTenHang);
                tvTenHang.setText(""+hang.getTenHang());
                tvTongTien = v.findViewById(R.id.tvTongTien);
                tvTongTien.setText("Tổng tiền: "+item.getTongTien()+" VNĐ");
                tvNgayLap = v.findViewById(R.id.tvNgayHD);
                Log.i("//===============","Ngày lập:"+item.getNgayLap());
                tvNgayLap.setText("Ngày lập: "+sdf.format(item.getNgayLap()));
                tvTrangThai = v.findViewById(R.id.tvTrangThai);
                if (item.getTrangThai()==1){
                    tvTrangThai.setTextColor(Color.BLUE);
                    tvTrangThai.setText("Tiền Mặt");
                }else {
                    tvTrangThai.setTextColor(Color.RED);
                    tvTrangThai.setText("Chuyển Khoản");
                }
                imgDel = v.findViewById(R.id.imgDeleteLS);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoa(String.valueOf(item.getMaHD()));
            }
        });

        return v ;
    }
}
