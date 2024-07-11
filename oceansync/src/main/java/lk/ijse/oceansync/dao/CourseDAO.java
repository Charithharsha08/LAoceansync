package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.entity.Course;

import java.sql.SQLException;

public interface CourseDAO extends CrudDAO<Course> {
    public Course getCourceByCourceName(String name) throws SQLException;
}
