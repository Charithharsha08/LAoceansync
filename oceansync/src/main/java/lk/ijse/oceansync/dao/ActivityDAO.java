package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.entity.Activity;
import lk.ijse.oceansync.model.ActivityDTO;

import java.sql.SQLException;
import java.util.List;

public interface ActivityDAO extends CrudDAO<Activity> {
public Activity selectActivityByName(String name) throws SQLException;
}
