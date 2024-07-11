package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.entity.SelectedCource;

import java.sql.SQLException;
import java.util.ArrayList;

public class SelectedCourceDAOImpl implements SelectedCourceDAO{
    @Override
    public ArrayList<SelectedCource> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(SelectedCource entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO selectedCource (customerId,courceId,date) VALUES(?,?,?)",entity.getCustomerId(),entity.getCourceId(),entity.getDate());
    }

    @Override
    public boolean update(SelectedCource dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public SelectedCource search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
