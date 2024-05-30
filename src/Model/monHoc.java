/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ConnectDAL;
import java.sql.*;
import javax.swing.*;

public class monHoc {
    public static Connection conn = ConnectDAL.getConnect(); // biến kết nối thông qua DAL
    public static PreparedStatement pst =null; // biến thực thi sql
    public static ResultSet rs = null; //kết quả trả về dạng 1 bảng hay 1 dòng dữ liệu
    
    // Thêm mới 1 môn học
    public static void themMH(String MaMH, String TenMH, int SoTiet)
{
    if (SoTiet < 0) {
        JOptionPane.showMessageDialog(null,"Số tiết học không được âm","Thông Báo",1);
        return;
    }

    // Kiểm tra mã môn học
    String checkMaMHSql = "SELECT * FROM MONHOC WHERE MaMH = ?";
    try {
        pst = conn.prepareStatement(checkMaMHSql);
        pst.setString(1, MaMH);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            JOptionPane.showMessageDialog(null,"Mã môn học "+MaMH+" đã tồn tại","Thông Báo",1);
            return;
        }
    } catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Lỗi khi kiểm tra mã môn học","Thông Báo",1);
        return;
    }

    // Kiểm tra tên môn học
    String checkTenMHSql = "SELECT * FROM MONHOC WHERE TenMH = ?";
    try {
        pst = conn.prepareStatement(checkTenMHSql);
        pst.setString(1, TenMH);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            JOptionPane.showMessageDialog(null,"Tên môn học "+TenMH+" đã tồn tại","Thông Báo",1);
            return;
        }
    } catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Lỗi khi kiểm tra tên môn học","Thông Báo",1);
        return;
    }

    // Thêm môn học
    String sql ="insert into MONHOC values (?,?,?)";
    try
    {
        pst = conn.prepareStatement(sql);
        pst.setString(1, MaMH);
        pst.setString(2, TenMH);
        pst.setInt(3, SoTiet);

        pst.execute();
        JOptionPane.showMessageDialog(null,"Đã thêm môn học "+TenMH+" thành công","Thông Báo",1);
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,"Lỗi khi thêm môn học","Thông Báo",1);
    }
}


    
    public static void suaMH(String MaMH1, String MaMH, String TenMH, String SoTiet) throws SQLException {
        if (!MaMH1.equals(MaMH)) {
        JOptionPane.showMessageDialog(null, "Không thể sửa mã môn học", "Thông Báo", JOptionPane.ERROR_MESSAGE);
        return;
    }
        // Kiểm tra xem số tiết học có phải là số âm không
    int soTiet = Integer.parseInt(SoTiet);
    if (soTiet < 0) {
        JOptionPane.showMessageDialog(null, "Số tiết học không thể là số âm", "Thông Báo", JOptionPane.ERROR_MESSAGE);
        return;
    }
        Statement stmt = conn.createStatement();
    // Kiểm tra xem thông tin mới có giống với thông tin hiện tại không
    String sqlCheck = "select * from MONHOC where MaMH='" + MaMH1 + "'";
    ResultSet rs = stmt.executeQuery(sqlCheck);
    if (rs.next()) {
        String currentTenMH = rs.getString("TenMH");
        String currentSoTiet = rs.getString("SoTiet");
        if (MaMH1.equals(MaMH) && currentTenMH.equals(TenMH) && currentSoTiet.equals(SoTiet)) {
            JOptionPane.showMessageDialog(null, "Thông tin sửa chưa được nhập", "Thông Báo", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // Kiểm tra xem tên môn học mới có trùng với bất kỳ môn học nào đã tồn tại chưa
    String sqlCheck2 = "select * from MONHOC where TenMH=N'" + TenMH + "' and MaMH<>N'" + MaMH1 + "'";
    ResultSet rs2 = stmt.executeQuery(sqlCheck2);
    if (rs2.next()) {
        JOptionPane.showMessageDialog(null, "Tên môn học đã tồn tại", "Thông Báo", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Nếu tất cả các điều kiện đều thỏa mãn, tiếp tục cập nhật thông tin môn học
    String sql = "update MONHOC set MaMH=N'" + MaMH + "', TenMH=N'" + TenMH + "', SoTiet='" + SoTiet + "' where MaMH='" + MaMH1 + "'";
    try {
        pst = conn.prepareStatement(sql);
        pst.execute();
        JOptionPane.showMessageDialog(null, "Đã sửa môn học thành công", "Thông Báo", 1);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Mã môn học " + MaMH + " đã tồn tại hoặc có lỗi khác: " + e.getMessage(), "Thông Báo", JOptionPane.ERROR_MESSAGE);
        throw e; // Rethrow the exception to be handled by the calling method
    }
}



    public static void xoaMH(String MaMH)
    {
        String sql="delete from MONHOC where MaMH='"+MaMH+"'";
        try
        {
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Đã xóa môn học "+MaMH+" thành công","Thông Báo",1);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Môn học "+MaMH+" không thể xóa","Thông Báo",1);
        }
    }
}
