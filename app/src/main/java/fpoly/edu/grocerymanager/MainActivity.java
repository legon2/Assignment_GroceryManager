package fpoly.edu.grocerymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import fpoly.edu.grocerymanager.dao.NguoiDungDAO;
import fpoly.edu.grocerymanager.fragment.AddUserFragment;
import fpoly.edu.grocerymanager.fragment.DoanhThuFragment;
import fpoly.edu.grocerymanager.fragment.DoiMatKhauFragment;
import fpoly.edu.grocerymanager.fragment.HangFragment;
import fpoly.edu.grocerymanager.fragment.LoaiHangFragment;
import fpoly.edu.grocerymanager.fragment.QuanLyHoaDonFragment;
import fpoly.edu.grocerymanager.fragment.TopFragment;
import fpoly.edu.grocerymanager.model.NguoiDung;

public class MainActivity extends AppCompatActivity {
        DrawerLayout drawer;
        Toolbar toolbar;
        View mHeaderView;
        TextView edUser;
        NguoiDungDAO nguoiDungDAO;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            drawer = findViewById(R.id.drawer_layout);

            toolbar = findViewById(R.id.toolbar1);

            //set toolbar thay thế cho actionbar
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();

            ab.setHomeAsUpIndicator(R.drawable.menu);
            ab.setDisplayHomeAsUpEnabled(true);



            FragmentManager manager = getSupportFragmentManager();
            HangFragment hangFragment = new HangFragment();
            manager.beginTransaction()
                    .replace(R.id.flContent, hangFragment).commit();

            NavigationView nv = findViewById(R.id.nvView);
            //show user in header
            mHeaderView = nv.getHeaderView(0);
            edUser = mHeaderView.findViewById(R.id.tvtUser);

            Intent i = getIntent();
            String user = i.getStringExtra("user");
            nguoiDungDAO = new NguoiDungDAO(this);
            NguoiDung nguoiDung = nguoiDungDAO.getID(user);
            String username = nguoiDung.getHoTen();
                       edUser.setText("Xin chào "+username+" !");

            if (user.equalsIgnoreCase("admin")){
               nv.getMenu().findItem(R.id.sub_Adduser).setVisible(true);
           }

            nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentManager manager = getSupportFragmentManager();

                    switch (item.getItemId()) {
                        case R.id.nav_HoaDon:
                            setTitle("Quản lý hoá đơn");
                            QuanLyHoaDonFragment quanLyHoaDonFragment = new QuanLyHoaDonFragment();
                            manager.beginTransaction()
                                    .replace(R.id.flContent, quanLyHoaDonFragment).commit();
                            break;
                        case R.id.nav_LoaiHang:
                            setTitle("Quản lý loại hàng");
                            LoaiHangFragment loaiHangFragment = new LoaiHangFragment();
                            manager.beginTransaction()
                                    .replace(R.id.flContent, loaiHangFragment).commit();

                            break;
                        case R.id.nav_Hang:
                            setTitle("Quản lý hàng hoá");
                            HangFragment hangFragment = new HangFragment();
                            manager.beginTransaction()
                                    .replace(R.id.flContent, hangFragment).commit();

                            break;
//                        case R.id.nav_TimKiem:
//                            setTitle("Tìm kiếm");
//                            TimKiemFragment timKiemFragment = new TimKiemFragment();
//                            manager.beginTransaction().replace(R.id.flContent, timKiemFragment)
//                                    .commit();
//
//                            break;
                        case R.id.sub_Top:
                            setTitle("Top 10 hàng hoá được mua nhiều nhất");
                            TopFragment topFragment = new TopFragment();
                            manager.beginTransaction()
                                    .replace(R.id.flContent, topFragment).commit();
                            break;
                        case R.id.sub_DoanhThu:
                            setTitle("Thống kê Doanh thu");
                            DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                            manager.beginTransaction()
                                    .replace(R.id.flContent, doanhThuFragment).commit();
                            break;
                        case R.id.sub_Adduser:
                            setTitle("Thêm người dùng");
                            AddUserFragment addUserFragment = new AddUserFragment();
                            manager.beginTransaction().replace(R.id.flContent, addUserFragment)
                                    .commit();

                            break;
                        case R.id.sub_Pass:
                            setTitle("Thay đổi mật khẩu");
                            DoiMatKhauFragment doiMatKhauFragment = new DoiMatKhauFragment();
                            manager.beginTransaction().replace(R.id.flContent, doiMatKhauFragment)
                                    .commit();
                            break;
                        case R.id.sub_Logout:
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();

                            break;

                    }
                    drawer.closeDrawers();

                    return false;
                }
            });

        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home)
                drawer.openDrawer(GravityCompat.START);
            return super.onOptionsItemSelected(item);
        }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_view,menu);
//        MenuItem menuItem = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                adapter.getFilter().filter(query);
//                menuItem.collapseActionView();
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String s) {
//                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
//                return false;
//            }
//        });
//        return true;
//    }
}