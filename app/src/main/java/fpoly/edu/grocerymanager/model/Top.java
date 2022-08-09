package fpoly.edu.grocerymanager.model;

public class Top {
    private String tenHang;
    private int soLuong;

    public Top() {
    }

    public Top(String tenHang, int soLuong) {
        this.tenHang = tenHang;
        this.soLuong = soLuong;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
