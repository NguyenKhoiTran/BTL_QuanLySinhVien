/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ConnectDAL;
import java.sql.*;
import javax.swing.*;

public class Diem {
    public static Connection conn = ConnectDAL.getConnect(); // biến kết nối thông qua DAL
    public static PreparedStatement pst =null; // biến thực thi sql
    public static ResultSet rs = null; //kết quả trả về dạng 1 bảng hay 1 dòng dữ liệu
    
    // Thêm mới
    public static void themDiemSV(String MaSV, String MaMH, String DiemCC, String DiemTX, String DiemGK, String DiemThi)
    {
        String sql ="insert into DIEM values (?,?,?,?,?,?)";
        try
        {
            pst = conn.prepareStatement(sql);
            pst.setString(1, MaSV);
            pst.setString(2, MaMH);
            pst.setString(3, DiemCC);
            pst.setString(4, DiemTX);
            pst.setString(5, DiemGK);
            pst.setString(6, DiemThi);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Đã thêm thành công","Thông Báo",1);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Mã sinh viên ''"+MaSV+"'' đã nhập điểm cho mã môn học ''"+MaMH+"''","Thông Báo",1);
        }
    }
    // Sửa điểm sv    
    // Thêm tham số MaSV vào phương thức
public static void suaDiemSV(String MaSV1, String MaMH1, String MaSV, String MaMH, String DiemCC, String DiemTX, String DiemGK, String DiemThi) {
    // Kiểm tra xem mã sinh viên có bị thay đổi không
    if (!MaSV1.equals(MaSV)) {
        JOptionPane.showMessageDialog(null, "Không được sửa mã sinh viên đã đăng kí môn học", "Thông Báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Truy vấn kiểm tra sự tồn tại của MaMH mới với cùng MaSV
//    String checkSql = "SELECT COUNT(*) FROM DIEM WHERE MaSV=? AND MaMH=?";
//    try {
//        pst = conn.prepareStatement(checkSql);
//        pst.setString(1, MaSV1);
//        pst.setString(2, MaMH);
//        ResultSet rs = pst.executeQuery();
//        if (rs.next() && rs.getInt(1) > 0) {
//            JOptionPane.showMessageDialog(null, "Mã môn học '" + MaMH + "' đã tồn tại cho sinh viên này", "Thông Báo", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//    } catch (SQLException e) {
//        JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra dữ liệu: " + e.getMessage(), "Thông Báo", JOptionPane.ERROR_MESSAGE);
//        return;
//    }
    
    // Nếu không tồn tại, tiến hành cập nhật thông tin (trừ MaSV)
    String sql = "UPDATE DIEM SET MaMH=?, DiemCC=?, DiemTX=?, DiemGK=?, DiemThi=? WHERE MaSV=? AND MaMH=?";
    try {
        pst = conn.prepareStatement(sql);
        pst.setString(1, MaMH);
        pst.setString(2, DiemCC);
        pst.setString(3, DiemTX);
        pst.setString(4, DiemGK);
        pst.setString(5, DiemThi);
        pst.setString(6, MaSV1);
        pst.setString(7, MaMH1);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Đã sửa thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật dữ liệu: " + e.getMessage(), "Thông Báo", JOptionPane.ERROR_MESSAGE);
    }
}


    
    // Xóa điểm sv
    public static void xoaDiemSV(String MaSV, String MaMH)
    {
        String sql="delete from DIEM where MaSV='"+MaSV+"' and MaMH='"+MaMH+"'";
        try
        {
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Đã xóa thành công","Thông Báo",1);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null," không thể xóa","Thông Báo",1);
        }
    }
    
}
