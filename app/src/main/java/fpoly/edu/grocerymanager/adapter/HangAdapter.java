package fpoly.edu.grocerymanager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fpoly.edu.grocerymanager.R;
import fpoly.edu.grocerymanager.dao.LoaiHangDAO;
import fpoly.edu.grocerymanager.fragment.HangFragment;
import fpoly.edu.grocerymanager.model.Hang;
import fpoly.edu.grocerymanager.model.LoaiHang;

public class HangAdapter extends ArrayAdapter<Hang> {

    Context context;
    HangFragment fragment;
    List<Hang> list;
    TextView tvMaHang, tvTenHang, tvGia, tvLoai;
    ImageView imgHinhAnh, imgDel;

    public HangAdapter(@NonNull Context context, HangFragment fragment, List<Hang> list) {
        super(context, 0,list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.hang_item,null);
        }

        final Hang item = list.get(position);
        if (item != null){
            LoaiHangDAO loaiHangDAO = new LoaiHangDAO(context);
            Log.i("//=============","=="+item.getMaLoai());
            LoaiHang loaiHang = loaiHangDAO.getID(String.valueOf(item.getMaLoai()));

            //tvMaHang = v.findViewById(R.id.tvMaHang);
            //tvMaHang.setText("Mã hàng: "+item.getMaHang());

            tvTenHang = v.findViewById(R.id.tvTenHang);
            tvTenHang.setText(""+item.getTenHang());
            tvGia = v.findViewById(R.id.tvGia);
            tvGia.setText("Giá mua: "+item.getGia()+" VNĐ");
            tvLoai = v.findViewById(R.id.tvLoai);
            tvLoai.setText("Loại hàng: "+loaiHang.getTenLoai());
            imgHinhAnh = v.findViewById(R.id.imgHinhAnh);
            //chuyen byte sang bitmap
            byte[] hinhAnh = item.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            imgHinhAnh.setImageBitmap(bitmap);
            v.setTag(convertView);

            imgDel=v.findViewById(R.id.imgDeleteLS);
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xóa
                fragment.xoa(String.valueOf(item.getMaHang()));


            }
        });

        return v;

    }
}
