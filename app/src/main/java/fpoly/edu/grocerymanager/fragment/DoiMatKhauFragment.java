package fpoly.edu.grocerymanager.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.edu.grocerymanager.R;
import fpoly.edu.grocerymanager.dao.NguoiDungDAO;
import fpoly.edu.grocerymanager.model.NguoiDung;

public class DoiMatKhauFragment extends Fragment {

    TextInputEditText edPassOld , edPass , edRepass;
    Button btnSave, btnCancel;
    NguoiDungDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        edPassOld = v.findViewById(R.id.edPassOld);
        edPass = v.findViewById(R.id.edPassChange);
        edRepass = v.findViewById(R.id.edRePassChange);
        btnSave = v.findViewById(R.id.btnSaveUserChange);
        btnCancel = v.findViewById(R.id.btnCancelUserChange);
        dao = new NguoiDungDAO(getActivity());



        //set sự kiện nút cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassOld.setText("");
                edPass.setText("");
                edRepass.setText("");

            }
        });

        //set sự kiện nút save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("USERNAME","");
                if (validate()>0){
                    //khai báo đối tượng thủ thư để lấy ID từ ThuThuDAO
                    NguoiDung nguoiDung = dao.getID(user);

                    //lấy mật khẩu nhập vào
                    nguoiDung.setMatKhau(edPass.getText().toString());

                    //nếu khác mật khẩu hiện tại thì show thay đổi thành công
                    if (dao.updatePass(nguoiDung) >0 ){
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPass.setText("");
                        edRepass.setText("");
                    }else {
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thất bại",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return v;
    }
    public int validate(){
        int check = 1;
        if (edPassOld.getText().length()==0 || edPass.getText().length()==0 || edRepass.getText().length()==0){
            Toast.makeText(getContext(),"Bạn phải nhập đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("PASSWORD","");
            String pass = edPass.getText().toString();
            String rePass = edRepass.getText().toString();
            //nếu pass cũ mà không trùng với pass cũ nhập vào
            if (!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(),"Mật khẩu cũ sai",Toast.LENGTH_SHORT).show();
                check = -1;
            }
            //mật khẩu không trùng khớp với nhập lại mật khẩu
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                check = -1;
            }



        }
        return check;
    }
}