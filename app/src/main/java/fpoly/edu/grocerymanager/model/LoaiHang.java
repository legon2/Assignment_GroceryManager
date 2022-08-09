package fpoly.edu.grocerymanager.model;

public class LoaiHang {
    private int maLoai;
    private String tenLoai;

    public LoaiHang() {
    }

    public LoaiHang(Integer maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public Integer getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(Integer maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
