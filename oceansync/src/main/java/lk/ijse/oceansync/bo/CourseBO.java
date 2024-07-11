package lk.ijse.oceansync.bo;

import lk.ijse.oceansync.model.CourseDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CourseBO extends SuperBO{
    public CourseDTO searchCourse(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteCourse(String id) throws SQLException, ClassNotFoundException;
    public String generateNewCourseID() throws SQLException, ClassNotFoundException;
    public boolean updateCourse(CourseDTO dto) throws SQLException, ClassNotFoundException;
    public boolean addCourse(CourseDTO dto) throws SQLException, ClassNotFoundException;
    public ArrayList<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException;
    public CourseDTO getCourceByCourceName(String name) throws SQLException;
}
