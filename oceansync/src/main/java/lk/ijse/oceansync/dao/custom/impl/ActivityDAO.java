package lk.ijse.oceansync.dao.custom.impl;

import lk.ijse.oceansync.dao.CrudDAO;
import lk.ijse.oceansync.entity.Activity;

import java.sql.SQLException;

public interface ActivityDAO extends CrudDAO<Activity> {
public Activity selectActivityByName(String name) throws SQLException;
}
