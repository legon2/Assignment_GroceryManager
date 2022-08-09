package fpoly.edu.grocerymanager.model;

import java.util.Date;

public class HoaDon {
    private int maHD;
    private String maND;
    private int maHang;
    private Date ngayLap;
    private int tongTien;
    private int trangThai;

    public HoaDon() {
    }

    public HoaDon(int maHD, String maND, int maHang, Date ngayLap, int tongTien, int trangThai) {
        this.maHD = maHD;
        this.maND = maND;
        this.maHang = maHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getMaND() {
        return maND;
    }

    public void setMaND(String maND) {
        this.maND = maND;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
