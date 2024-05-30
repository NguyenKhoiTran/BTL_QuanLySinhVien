/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ConnectDAL;
import java.sql.*;
import javax.swing.*;

public class sinhVien {
    
    public static Connection conn = ConnectDAL.getConnect(); // biến kết nối thông qua DAL
    public static PreparedStatement pst =null; // biến thực thi sql
    public static ResultSet rs = null; //kết quả trả về dạng 1 bảng hay 1 dòng dữ liệu
    
    // Thêm mới 1 Sinh Viên
    
    
    public static void themSV(String MaSV, String HoSV, String TenSV, String NgaySinh, String GioiTinh, String Email)
    {
        String sql ="insert into SINHVIEN values (?,?,?,?,?,?)";
        try
        {
            pst = conn.prepareStatement(sql);
            pst.setString(1, MaSV);
            pst.setString(2, HoSV);
            pst.setString(3, TenSV);
            pst.setString(4, NgaySinh);
            pst.setString(5, GioiTinh);
            pst.setString(6, Email);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Đã thêm sinh viên ''"+HoSV+" "+TenSV+"'' thành công","Thông Báo",1);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Mã sinh viên "+MaSV+" đã tồn tại","Thông Báo",1);
        }
     
    }
    
    // sửa Sinh viên
    public static void suaSV(String MaSV1, String MaSV, String HoSV, String TenSV, String NgaySinh, String GioiTinh, String Email) {
    // Kiểm tra xem mã sinh viên có bị thay đổi hay không
    if (!MaSV1.equals(MaSV)) {
        JOptionPane.showMessageDialog(null, "Không được phép thay đổi mã sinh viên", "Thông Báo", JOptionPane.ERROR_MESSAGE);
        return; // Dừng thực hiện nếu mã sinh viên bị thay đổi
    }

    // Kiểm tra xem thông tin sửa trùng
    String checkDuplicateSql = "SELECT COUNT(*) FROM SINHVIEN WHERE HoSV=N'" + HoSV + "' AND TenSV=N'" + TenSV + "' AND NgaySinh='" + NgaySinh + "' AND GioiTinh='" + GioiTinh + "' AND Email='" + Email + "'";
    try {
        pst = conn.prepareStatement(checkDuplicateSql);
        ResultSet rs = pst.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            JOptionPane.showMessageDialog(null, "Hãy nhập thông tin cần sửa. Vui lòng kiểm tra lại", "Thông Báo", JOptionPane.ERROR_MESSAGE);
            return; // Dừng thực hiện nếu thông tin sửa trùng
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi kiểm tra thông tin sửa trùng", "Thông Báo", JOptionPane.ERROR_MESSAGE);
        return; // Dừng thực hiện nếu có lỗi
    }

    // Chuỗi SQL không còn chứa việc cập nhật MaSV
    String sql = "update SINHVIEN set HoSV=N'" + HoSV + "', TenSV=N'" + TenSV + "', NgaySinh='" + NgaySinh + "', GioiTinh='" + GioiTinh + "', Email='" + Email + "' where MaSV='" + MaSV1 + "'";
    try {
        pst = conn.prepareStatement(sql);
        pst.execute();
        JOptionPane.showMessageDialog(null, "Đã sửa thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi cập nhật thông tin", "Thông Báo", JOptionPane.ERROR_MESSAGE);
    }
}

    // Xóa sinh viên
    public static void xoaSV(String MaSV)
    {
        String sql="delete from SINHVIEN where MaSV='"+MaSV+"'";
        try
        {
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Đã xóa sinh viên "+MaSV+" thành công","Thông Báo",1);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Sinh viên "+MaSV+" không thể xóa","Thông Báo",1);
        }
    }
}
