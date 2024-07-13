package lk.ijse.oceansync.bo.custom.impl;

import lk.ijse.oceansync.bo.custom.CourseBO;
import lk.ijse.oceansync.dao.custom.impl.CourseDAO;
import lk.ijse.oceansync.dao.DAOFactory;
import lk.ijse.oceansync.entity.Course;
import lk.ijse.oceansync.model.CourseDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CourseBOImpl implements CourseBO {
    CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COURSE);
    @Override
    public CourseDTO searchCourse(String id) throws SQLException, ClassNotFoundException {
    Course course = courseDAO.search(id);
    return new CourseDTO(course.getCourceId(),course.getName(),course.getDuration(),course.getCost());
    }

    @Override
    public boolean deleteCourse(String id) throws SQLException, ClassNotFoundException {
        return courseDAO.delete(id);
    }

    @Override
    public String generateNewCourseID() throws SQLException, ClassNotFoundException {
        return courseDAO.generateNewID();
    }

    @Override
    public boolean updateCourse(CourseDTO dto) throws SQLException, ClassNotFoundException {
               return courseDAO.update(new Course(dto.getCourceId(),dto.getName(),dto.getDuration(),dto.getCost()));

    }

    @Override
    public boolean addCourse(CourseDTO dto) throws SQLException, ClassNotFoundException {
        return courseDAO.add(new Course(dto.getCourceId(),dto.getName(),dto.getDuration(),dto.getCost()));
    }

    @Override
    public ArrayList<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException {
        ArrayList<CourseDTO> dtos = new ArrayList<>();
        ArrayList<Course> courses =courseDAO.getAll();
        for (Course course : courses){
            dtos.add(new CourseDTO(course.getCourceId(),course.getName(),course.getDuration(),course.getCost()));
        }
        return dtos;
    }

    @Override
    public CourseDTO getCourceByCourceName(String name) throws SQLException {
       Course course = courseDAO.getCourceByCourceName(name);
       return new CourseDTO(course.getCourceId(),course.getName(),course.getDuration(),course.getCost());
    }
}
