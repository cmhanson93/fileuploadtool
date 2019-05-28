package com.cmh.uploadfiletool.dao;

import com.cmh.uploadfiletool.vo.FileVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chanson
 */
public class FilesDbDAO {

    private ConnectionFactory connectionFactory;
    private Connection conn;

    public FileVO getFile(int id) {
        FileVO file = new FileVO();

        try {
            conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM files WHERE id=?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            file.setName(rs.getString("name"));
            file.setSize(rs.getInt("size"));
            file.setType(rs.getString("type"));
            file.setUploadedBy(rs.getString("uploaded_by"));
            file.setUploadedTime(new java.util.Date((rs.getDate("uploaded_time")).getTime()));
            file.setProvider("database");

            conn.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    public Set<FileVO> getAllFiles() {
        Set<FileVO> files = new HashSet<FileVO>();

        try {
            conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM files");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FileVO file = new FileVO();

                file.setId(rs.getInt("id"));
                file.setName(rs.getString("name"));
                file.setSize(rs.getInt("size"));
                file.setType(rs.getString("type"));
                file.setUploadedBy(rs.getString("uploaded_by"));
                // converting time from db to java
                file.setUploadedTime(new java.util.Date((rs.getDate("uploaded_time")).getTime()));
                file.setProvider("database");

                files.add(file);
            }

            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return files;

    }

    public boolean insertFile(FileVO file) {
        boolean insertSuccess = false;

        try {
            conn = connectionFactory.getConnection();
            String sql = "INSERT INTO files (name, size, type, uploaded_by, uploaded_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, file.getName());
            ps.setInt(2, file.getSize());
            ps.setString(3, file.getType());
            ps.setString(4, file.getUploadedBy());
            // sets timestamp to current time in DB
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            ps.setTimestamp(5, date);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                insertSuccess = true;
            }

            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return insertSuccess;
    }

    public boolean updateFile(FileVO file) {
        boolean updateSuccess = false;
        try {
            conn = connectionFactory.getConnection();
            String sql = "UPDATE files SET name=?, size=?, type=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, file.getName());
            ps.setInt(2, file.getSize());
            ps.setString(3, file.getType());
            ps.setInt(4, file.getId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                updateSuccess = true;
            }

            conn.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updateSuccess;
    }

    public boolean deleteFile(FileVO file) {
        boolean deleteSuccess = false;
        try {
            conn = connectionFactory.getConnection();
            String sql = "DELETE FROM files WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, file.getId());

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                deleteSuccess = true;
            }

            conn.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilesDbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return deleteSuccess;

    }

}
