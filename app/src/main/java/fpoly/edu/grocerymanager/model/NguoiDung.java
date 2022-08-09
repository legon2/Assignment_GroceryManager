package fpoly.edu.grocerymanager.model;

public class NguoiDung {
    private String maND;
    private String hoTen;
    private String matKhau;
    private int soDienThoai;

    public NguoiDung() {
    }

    public NguoiDung(String maND, String hoTen, String matKhau, int soDienThoai) {
        this.maND = maND;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
    }

    public String getMaND() {
        return maND;
    }

    public void setMaND(String maND) {
        this.maND = maND;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(int soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    @Override
    public String toString() {
        return "NguoiDung{" +
                "maND='" + maND + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", soDienThoai'" + soDienThoai + '\'' +
                '}';
    }
}
