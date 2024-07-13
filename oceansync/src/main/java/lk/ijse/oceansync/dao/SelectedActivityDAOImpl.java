package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.dao.custom.impl.SelectedActivityDAO;
import lk.ijse.oceansync.entity.SelectedActivity;

import java.sql.SQLException;
import java.util.ArrayList;

public class SelectedActivityDAOImpl implements SelectedActivityDAO {
    @Override
    public ArrayList<SelectedActivity> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(SelectedActivity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO selectedActivity (activityId,customerId,date)VALUES(?,?,?)",entity.getActivityId(),entity.getCustomerId(),entity.getDate());
    }

    @Override
    public boolean update(SelectedActivity dto) throws SQLException, ClassNotFoundException {
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
    public SelectedActivity search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
