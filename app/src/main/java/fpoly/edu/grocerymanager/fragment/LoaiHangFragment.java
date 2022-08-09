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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import fpoly.edu.grocerymanager.R;
import fpoly.edu.grocerymanager.adapter.LoaiHangAdapter;
import fpoly.edu.grocerymanager.dao.LoaiHangDAO;
import fpoly.edu.grocerymanager.model.LoaiHang;

public class LoaiHangFragment extends Fragment {

    ListView lvLoaiHang;
    ArrayList<LoaiHang> list;
    LoaiHangAdapter adapter;
    Dialog dialog;
    EditText edMaLoaiHang, edTenLoaiHang;
    Button btnSave, btnCancel;
    static LoaiHangDAO dao;
    LoaiHang item;
    FloatingActionButton fab;
    TextInputLayout tilTenLoaiHang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_loai_hang, container, false);
        lvLoaiHang = v.findViewById(R.id.lvLoaiHang);
        fab = v.findViewById(R.id.fab);
        dao = new LoaiHangDAO(getActivity());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);

            }
        });
        lvLoaiHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);
                return false;
            }
        });
        capNhapLv();
        return v;
    }
    void capNhapLv(){
        list =(ArrayList<LoaiHang>) dao.getAll();
        adapter = new LoaiHangAdapter(getActivity(),this,list);
        lvLoaiHang.setAdapter(adapter);

    }
    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xoá loại hàng");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setCancelable(true);
        builder.setPositiveButton("Có",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dao.delete(Id);
                        Toast.makeText(getActivity(), "Đã xoá thành công", Toast.LENGTH_SHORT).show();
                        capNhapLv();
                        dialog.cancel();

                    }
                });
        builder.setNegativeButton("Không",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        builder.show();
    }
    protected void openDialog(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loai_hang_dialog);
        edMaLoaiHang = dialog.findViewById(R.id.edLoaiHang);
        edTenLoaiHang = dialog.findViewById(R.id.edTenLoaiHang);
        btnCancel = dialog.findViewById(R.id.btnCancelLoaiHang);
        btnSave = dialog.findViewById(R.id.btnSaveLoaiHang);
        tilTenLoaiHang = dialog.findViewById(R.id.tilTenLoaiHang);

        if (type != 0){
            edMaLoaiHang.setText(String.valueOf(item.getMaLoai()));
            edTenLoaiHang.setText(item.getTenLoai());
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
                item = new LoaiHang();
                item.setTenLoai(edTenLoaiHang.getText().toString());
                if (validate()>0) {
                    if (type == 0) {
                        //type = 0 (insert)
                        if (dao.insert(item)>0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //type = 1(update)
                        item.setMaLoai(Integer.parseInt(edMaLoaiHang.getText().toString()));
                        if (dao.update(item)>0) {
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhapLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }
    public int validate(){
        int check = 1;
        if (edTenLoaiHang.getText().length() ==0 ){
            tilTenLoaiHang.setError("Tên loại hàng trống!");
            Toast.makeText(getContext(),"Bạn phải nhập đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}