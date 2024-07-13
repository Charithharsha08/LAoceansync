package lk.ijse.oceansync.bo.custom;

import lk.ijse.oceansync.bo.SuperBO;
import lk.ijse.oceansync.model.ActivityDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ActivityBO extends SuperBO {
    public ArrayList<ActivityDTO> getAllActivity() throws SQLException, ClassNotFoundException;
    public boolean addActivity(ActivityDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateActivity(ActivityDTO dto) throws SQLException, ClassNotFoundException;
    public String generateNewActivityID() throws SQLException, ClassNotFoundException;
    public boolean deleteActivity(String id) throws SQLException, ClassNotFoundException;
    public ActivityDTO searchActivity(String id) throws SQLException, ClassNotFoundException;
    public ActivityDTO selectActivityByName(String name) throws SQLException;
    }
