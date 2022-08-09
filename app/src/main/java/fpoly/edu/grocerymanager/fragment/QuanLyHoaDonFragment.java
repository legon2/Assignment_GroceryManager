package fpoly.edu.grocerymanager.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpoly.edu.grocerymanager.R;
import fpoly.edu.grocerymanager.adapter.HangSpinnerAdapter;
import fpoly.edu.grocerymanager.adapter.HoaDonAdapter;
import fpoly.edu.grocerymanager.dao.HangDAO;
import fpoly.edu.grocerymanager.dao.HoaDonDAO;
import fpoly.edu.grocerymanager.model.Hang;
import fpoly.edu.grocerymanager.model.HoaDon;


public class QuanLyHoaDonFragment extends Fragment {
    ListView lvHoaDon;
    ArrayList<HoaDon> list;
    static HoaDonDAO dao;
    HoaDonAdapter adapter;
    HoaDon item;

    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaHD;
    Spinner spHang;
    TextView tvNgayLap, tvTongTien;
    CheckBox chkTrangThai;
    Button btnSave, btnCancel;


    HangSpinnerAdapter hangSpinnerAdapter;
    ArrayList<Hang> listHang;
    HangDAO hangDAO;
    Hang hang;
    int maHang, tongTien;
    int positionHang;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quan_ly_hoa_don, container, false);
        lvHoaDon = v.findViewById(R.id.lvHoaDon);
        fab = v.findViewById(R.id.fab);
        dao = new HoaDonDAO(getActivity());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);
            }
        });
        //nhấn và giữ
        lvHoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1); //update
                return false;
            }
        });
        capNhatLv();
        return v;
    }
    void capNhatLv(){
        list =(ArrayList<HoaDon>) dao.getAll();
        adapter=new HoaDonAdapter(getActivity(),this,list);
        lvHoaDon.setAdapter(adapter);

    }
    protected void openDialog(final Context context, final int type){
        //custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.hoa_don_dialog);
        edMaHD = dialog.findViewById(R.id.edMaHD);
        spHang = dialog.findViewById(R.id.spMaHang);
        tvNgayLap = dialog.findViewById(R.id.tvNgay);
        tvTongTien = dialog.findViewById(R.id.tvTongTien);
        chkTrangThai = dialog.findViewById(R.id.chkTrangThai);
        btnCancel = dialog.findViewById(R.id.btnCancelHD);
        btnSave = dialog.findViewById(R.id.btnSaveHD);
        //set ngay thue
        tvNgayLap.setText("Ngày lập: "+sdf.format(new Date()));
        edMaHD.setEnabled(false);

        hangDAO = new HangDAO(context);
        listHang = new ArrayList<Hang>();
        listHang = (ArrayList<Hang>) hangDAO.getAll();
        hangSpinnerAdapter = new HangSpinnerAdapter(context,listHang);
        spHang.setAdapter(hangSpinnerAdapter);
        //lấy mã loaisách
        spHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maHang = listHang.get(position).getMaHang();
                tongTien = listHang.get(position).getGia();
                tvTongTien.setText("Tổng tiền: "+tongTien+" VNĐ");
                       Toast.makeText(context,"Chọn: "+listHang.get(position).getTenHang(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //edit set data lên form
        if (type != 0){
            edMaHD.setText(String.valueOf(item.getMaHD()));

//            for (int i = 0; i < listThanhVien.size(); i++)
//                if (item.getMaTV() == (listThanhVien.get(i).getMaTV())){
//                    positionTV = i;
//                }
//            spTV.setSelection(positionTV);

            for (int i = 0 ; i < listHang.size(); i++)
                if (item.getMaHang() == (listHang.get(i).getMaHang())){
                    positionHang = i;
                }
            spHang.setSelection(positionHang);

            tvNgayLap.setText("Ngày lập: "+sdf.format(item.getNgayLap()));
            tvTongTien.setText("Tổng tiền: "+item.getTongTien()+" VNĐ");
            if (item.getTrangThai() == 1){
                chkTrangThai.setChecked(true);
            }else {
                chkTrangThai.setChecked(false);
            }
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new HoaDon();
                item.setMaHang(maHang);
                item.setNgayLap(new Date());
                item.setTongTien(tongTien);
                if (chkTrangThai.isChecked()){
                    item.setTrangThai(1);
                }else {
                    item.setTrangThai(0);
                }
                if (type == 0){
                    // type = 0 (insert)
                    if (dao.insert(item) > 0){
                        Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    //type = 1(update)
                    item.setMaHD(Integer.parseInt(edMaHD.getText().toString()));
                    if (dao.update(item) > 0){
                        Toast.makeText(context,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                    }

                }
                capNhatLv();
                dialog.dismiss();
            }

        });

        dialog.show();
    }
    public void xoa(String Id){
        //sử dụng Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xoá đơn hàng");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setCancelable(true);

        builder.setPositiveButton("Có",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //gọi function Delete
                        dao.delete(Id);
                        Toast.makeText(getActivity(), "Đã xoá thành công", Toast.LENGTH_SHORT).show();
                        //cập nhật listview
                        capNhatLv();
                        dialog.cancel();

                    }
                });
        builder.setNegativeButton("Không",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        builder.show();
    }
}