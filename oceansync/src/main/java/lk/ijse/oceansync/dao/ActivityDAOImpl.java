package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.dao.custom.impl.ActivityDAO;
import lk.ijse.oceansync.entity.Activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActivityDAOImpl implements ActivityDAO {
    @Override
    public ArrayList<Activity> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Activity> activities = new ArrayList<>();
        ResultSet set = SQLUtil.execute("SELECT * FROM activity");
        while (set.next()){
            activities.add(new Activity(set.getString("activityId"),set.getString("name"),set.getString("type"),set.getString("location"),set.getDouble("cost")));
        }
        return activities;
    }

    @Override
    public boolean add(Activity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO activity (activityId,name,type,location,cost)VALUES (?,?,?,?,?)",entity.getActivityId(),entity.getName(),entity.getType(),entity.getLocation(),entity.getCost());
    }

    @Override
    public boolean update(Activity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE activity SET name=?, type=?, location=?, cost=? WHERE activityId=?",entity.getName(),entity.getType(),entity.getLocation(),entity.getCost(),entity.getActivityId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT activityId FROM activity ORDER BY activityId DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("activityId");
            int newStockId = Integer.parseInt(id.replace("A00-", "")) + 1;
            return String.format("A00-%03d", newStockId);
        } else {
            return "A00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM activity WHERE activityId = ?",id);
    }

    @Override
    public Activity search(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = SQLUtil.execute("SELECT * FROM activity WHERE activityId = ?",id);
        set.next();
        return new Activity(id +"",set.getString("name"),set.getString("type"),set.getString("location"),set.getDouble("cost"));
    }


    @Override
    public Activity selectActivityByName(String name) throws SQLException {
        ResultSet set = SQLUtil.execute("SELECT * FROM activity WHERE name = ?",name);
        set.next();
        return new Activity(set.getString("activityId"),set.getString("name"),set.getString("type"),set.getString("location"),set.getDouble("cost"));
    }
}
