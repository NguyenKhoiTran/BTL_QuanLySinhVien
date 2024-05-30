package Model;

import static Model.Diem.conn;
import static Model.Diem.pst;
import static Model.monHoc.conn;
import static Model.monHoc.pst;
import Controller.ConnectDAL;
import static View.jfmSinhVien.masv;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luong
 */
public class lopHocMon {
    public static Connection conn = ConnectDAL.getConnect(); // biến kết nối thông qua DAL
    public static PreparedStatement pst =null; // biến thực thi sql
    public static ResultSet rs = null; //kết quả trả về dạng 1 bảng hay 1 dòng dữ liệu
    
    public static void dangKiMonHoc(String MaSV, String MaMH) {
    String checkSql = "SELECT * FROM lopHoc WHERE MaSV = ? AND MaMH = ?";
    String insertSql = "INSERT INTO lopHoc (MaSV, MaMH) VALUES (?, ?)";
    try {
        // Kiểm tra xem dữ liệu đã tồn tại hay chưa
        pst = conn.prepareStatement(checkSql);
        pst.setString(1, MaSV);
        pst.setString(2, MaMH);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            // Nếu dữ liệu đã tồn tại, thông báo lỗi
            JOptionPane.showMessageDialog(null, "Mã môn học " + MaMH + " đã tồn tại cho sinh viên " + MaSV, "Thông Báo", JOptionPane.WARNING_MESSAGE);
        } else {
            // Nếu dữ liệu chưa tồn tại, thực hiện thêm dữ liệu mới
            pst = conn.prepareStatement(insertSql);
            pst.setString(1, MaSV);
            pst.setString(2, MaMH);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Đã thêm môn học thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm môn học", "Thông Báo", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (pst != null) pst.close();
            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


    public static void suaDangKi(String maSV, String maMH, String maMHMoi) {
    // SQL để kiểm tra xem mã sinh viên có tồn tại trong đăng ký môn học hay không
    String checkSql = "SELECT COUNT(*) FROM lopHoc WHERE MaSV='" + maSV + "' AND MaMH='" + maMH + "'";
    
    try {
        // Kiểm tra xem mã sinh viên và mã môn học có tồn tại hay không
        pst = conn.prepareStatement(checkSql);
        ResultSet rs = pst.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            // Kiểm tra xem mã môn học mới đã tồn tại trong đăng ký của sinh viên chưa
            String checkNewSql = "SELECT COUNT(*) FROM lopHoc WHERE MaSV='" + maSV + "' AND MaMH='" + maMHMoi + "'";
            pst = conn.prepareStatement(checkNewSql);
            ResultSet newRs = pst.executeQuery();
            if (newRs.next() && newRs.getInt(1) > 0) {
                // Nếu mã môn học mới đã tồn tại, thông báo lỗi
                JOptionPane.showMessageDialog(null, "Sinh viên đã đăng ký môn học này trước đó", "Thông Báo", JOptionPane.WARNING_MESSAGE);
            } else {
                // Nếu không tồn tại, thực hiện lệnh cập nhật
                String updateSql = "UPDATE lopHoc SET MaMH=N'" + maMHMoi + "' WHERE MaSV='" + maSV + "' AND MaMH='" + maMH + "'";
                pst = conn.prepareStatement(updateSql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Đã sửa thông tin đăng kí thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // Nếu không tồn tại, thông báo lỗi
            JOptionPane.showMessageDialog(null, "Không được sửa mã sinh viên đã đăng kí môn học", "Thông Báo", JOptionPane.WARNING_MESSAGE);
        }
    } catch (Exception e) {
        // Xử lý ngoại lệ và thông báo lỗi
        JOptionPane.showMessageDialog(null, "Lỗi truy cập SQL", "Thông Báo", JOptionPane.WARNING_MESSAGE);
    }
}

    public static void huyDangKi(String MaSV, String MaMH)
    {
        String sql="delete from lopHoc where MaSV='"+MaSV+"' and MaMH='"+MaMH+"'";
         if(JOptionPane.showConfirmDialog(null,"Bạn có muốn xóa sinh viên "+MaSV+" ra khỏi lớp học không ?","Thông Báo",2)==0)
            {
        try
        {
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Đã hủy đăng kí thành công","Thông Báo",1);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null," không thể hủy","Thông Báo",1);
        }
    }}
}
