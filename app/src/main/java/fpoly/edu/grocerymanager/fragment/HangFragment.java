package fpoly.edu.grocerymanager.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fpoly.edu.grocerymanager.R;
import fpoly.edu.grocerymanager.adapter.HangAdapter;
import fpoly.edu.grocerymanager.adapter.LoaiHangSpinnerAdapter;
import fpoly.edu.grocerymanager.dao.HangDAO;
import fpoly.edu.grocerymanager.dao.LoaiHangDAO;
import fpoly.edu.grocerymanager.database.DbHelper;
import fpoly.edu.grocerymanager.model.Hang;
import fpoly.edu.grocerymanager.model.LoaiHang;

public class HangFragment extends Fragment {
    ListView lvHang;
    HangDAO hangDAO;

    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaHang, edTenHang, edGiaThue, edTimKiem;
    Spinner spinner;
    Button btnSave, btnCancel;
    ImageButton imgCamera, imgUpload, imgTimKiem;
    ImageView imgSanPham;
    TextInputLayout tilTenHang, tilGiaHang;

    DbHelper db;
    HangAdapter adapter;
    Hang item;
    List<Hang> list;
    List<Hang> listTimKiem;

    LoaiHangSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiHang> listLoaiHang;
    LoaiHangDAO loaiHangDAO;
    LoaiHang loaiHang;
    int maLoaiHang, position;

    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_UPLOAD = 456;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_hang, container, false);
        lvHang = v.findViewById(R.id.lvHang);
        edTimKiem = v.findViewById(R.id.edTimKiem);
        imgTimKiem = v.findViewById(R.id.imgTimKiem);
        registerForContextMenu(lvHang);

        hangDAO = new HangDAO(getActivity());
        capNhatLv();
        fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);

            }
        });
        lvHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);
                return false;
            }
        });//nh???n v?? gi???


        imgTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hangDAO = new HangDAO(getContext());
                item = new Hang();
                list = hangDAO.getTimKiem(edTimKiem.getText().toString());

                if (list.size() > 0) {
                    TimKiem();
                    Toast.makeText(getContext(), "T??m ki???m th??nh c??ng", Toast.LENGTH_SHORT).show();
                } else {
                    capNhatLv();
                    Toast.makeText(getContext(), "T??n h??ng kh??ng t???n t???i", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }
    void TimKiem(){
        adapter = new HangAdapter(getActivity(), HangFragment.this, list);
        lvHang.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void capNhatLv(){
        list = (List<Hang>) hangDAO.getAll();
        adapter = new HangAdapter(getActivity(),this, list);
        lvHang.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }





    public void xoa(final String Id){
        //s?? d???ng alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xo?? h??ng ho??");
        builder.setMessage("B???n mu???n x??a kh??ng");
        builder.setCancelable(true);

        builder.setPositiveButton("C??",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //g???i function Delete
                        hangDAO.delete(Id);
                        Toast.makeText(getActivity(), "???? xo?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                        //c???p nh??t listview
                        capNhatLv();
                        dialog.cancel();

                    }
                });
        builder.setNegativeButton("Kh??ng",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        builder.show();
    }
    protected void openDialog(final Context context,final int type){
        //custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.hang_dialog);
        edMaHang = dialog.findViewById(R.id.edMaHang);
        edTenHang = dialog.findViewById(R.id.edTenHang);
        edGiaThue = dialog.findViewById(R.id.edGiaHang);
        spinner = dialog.findViewById(R.id.spLoaiHang);
        btnCancel = dialog.findViewById(R.id.btnCancelHang);
        btnSave = dialog.findViewById(R.id.btnSaveHang);
        imgCamera = dialog.findViewById(R.id.imgCamera);
        imgUpload = dialog.findViewById(R.id.imgUpload);
        imgSanPham = dialog.findViewById(R.id.imgSanPham);
        tilTenHang = dialog.findViewById(R.id.tilTenHang);
        tilGiaHang = dialog.findViewById(R.id.tilGiaHang);

        listLoaiHang = new ArrayList<LoaiHang>();
        loaiHangDAO = new LoaiHangDAO(context);
        listLoaiHang = (ArrayList<LoaiHang>) loaiHangDAO.getAll();

        spinnerAdapter = new LoaiHangSpinnerAdapter(context,listLoaiHang);

        spinner.setAdapter(spinnerAdapter);
        // lay maloaihang
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiHang = Integer.parseInt(String.valueOf(listLoaiHang.get(position).getMaLoai()));
                Toast.makeText(context,"Ch???n "+listLoaiHang.get(position).getTenLoai(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // th??m ???nh
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            }
        });

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_UPLOAD);
                //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_UPLOAD);
            }
        });

        //ki???m tra type insert 0 hay update 1
        edMaHang.setEnabled(false);
        if (type != 0){
            edMaHang.setText(String.valueOf(item.getMaHang()));
            edTenHang.setText(item.getTenHang());
            edGiaThue.setText(String.valueOf(item.getGia()));
            for (int i = 0; i < listLoaiHang.size(); i++)
                if (item.getMaLoai() == (listLoaiHang.get(i).getMaLoai())){
                    position = i;
                }
            Log.i("demo","posHang "+position);
            spinner.setSelection(position);
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
                //chuyen data imgView sang byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgSanPham.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] hinhAnh = byteArray.toByteArray();

                if (validate() > 0){
                    if (type == 0) { //type = 0 l?? th??m
                        hangDAO.INSERT_SP(edTenHang.getText().toString().trim(),
                                Integer.parseInt(edGiaThue.getText().toString().trim()),
                                Integer.valueOf(String.valueOf(maLoaiHang)), hinhAnh);
                        Toast.makeText(context,"Th??m th??nh c??ng",Toast.LENGTH_SHORT).show();
                    } else { //type = 1 l?? c???p nh???t
                        hangDAO.UPDATE_SP(Integer.valueOf(edMaHang.getText().toString().trim()),edTenHang.getText().toString().trim(),
                                Integer.parseInt(edGiaThue.getText().toString().trim()),
                                Integer.valueOf(String.valueOf(maLoaiHang)), hinhAnh);
                        Toast.makeText(context,"C???p nh???t th??nh c??ng",Toast.LENGTH_SHORT).show();
                    }
                    capNhatLv();
                    dialog.dismiss();
                }


//                hangDAO.INSERT_SP(edTenHang.getText().toString().trim(),
//                        Integer.parseInt(edGiaThue.getText().toString().trim()),
//                        Integer.valueOf(String.valueOf(maLoaiHang)), hinhAnh);

//                Toast.makeText(context,"Th??m th??nh c??ng",Toast.LENGTH_SHORT).show();
//                capNhatLv();
//                dialog.dismiss();

//                item = new Hang();
//                item.setTenHang(edTenHang.getText().toString());
//                item.setGia(Integer.parseInt(edGiaThue.getText().toString()));
//                item.setMaLoai(Integer.valueOf(String.valueOf(maLoaiHang)));
//                item.setHinhAnh(hinhAnh);
//                if (validate()>0){
//                    if (type==0){
//                        //type = 0 (insert)
//                        if (hangDAO.insert(item)>0){
//                            Toast.makeText(context,"Th??m th??nh c??ng",Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(context,"Th??m th???t b???i",Toast.LENGTH_SHORT).show();
//                        }
//
//                    }else {
//                        //type = 1(update)
//                        item.setMaHang(Integer.valueOf(edMaHang.getText().toString()));
//                        if (hangDAO.update(item)>0){
//                            Toast.makeText(context,"C???p nh???t th??nh c??ng",Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(context,"C???p nh???t th???t b???i",Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                    capNhatLv();
//                    dialog.dismiss();
//                }
            }
        });
        dialog.show();
    }
    public int validate(){
        int check = 1;
        if (edTenHang.getText().length()==0 || edGiaThue.getText().length()==0) {
            tilTenHang.setError("T??n h??ng tr???ng!");
            tilGiaHang.setError("Gi?? h??ng tr???ng!");
            Toast.makeText(getContext(), "B???n ph???i nh???p ????? th??ng tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgSanPham.setImageBitmap(bitmap);
        }

        if (requestCode == REQUEST_CODE_UPLOAD && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSanPham.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if(grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(getContext(), "B???n ???? t??? ch???i quy???n truy c???p m??y ???nh", Toast.LENGTH_SHORT).show();
                }


                break;
            case REQUEST_CODE_UPLOAD:
                if(grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    Intent intent =  new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_UPLOAD);
                } else {
                    Toast.makeText(getContext(), "B???n ???? t??? ch???i quy???n truy c???p th?? vi???n ???nh", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}