package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAOImpl implements CourseDAO{
    @Override
    public ArrayList<Course> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Course> courses = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM cource");
        while (rst.next()){
          courses.add(new Course(rst.getString("courceId"),rst.getString("name"),rst.getString("duration"),rst.getDouble("cost")));
        }return courses;
    }

    @Override
    public boolean add(Course entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO cource (courceId,name,duration,cost)VALUES(?,?,?,?)",entity.getCourceId(),entity.getName(),entity.getDuration(),entity.getCost());
    }

    @Override
    public boolean update(Course entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE cource SET name=?,duration=?,cost=? WHERE courceId=?",entity.getName(),entity.getDuration(),entity.getCost(),entity.getCourceId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT courceId FROM cource ORDER BY courceId DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("courceId");
            int newStockId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newStockId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute( "DELETE FROM cource WHERE courceId=?",id);
    }

    @Override
    public Course search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM cource WHERE courceId = ?",id);
        rst.next();
        return new Course(id + "",rst.getString("name"),rst.getString("duration"),rst.getDouble("cost"));
    }

    @Override
    public Course getCourceByCourceName(String name) throws SQLException {
       ResultSet rst = SQLUtil.execute("SELECT * FROM cource WHERE name = ?",name);
       rst.next();
        return new Course(rst.getString("courceId") ,rst.getString("name"),rst.getString("duration"),rst.getDouble("cost"));
    }
}
