package lk.ijse.oceansync.bo;

import lk.ijse.oceansync.dao.ActivityDAO;
import lk.ijse.oceansync.dao.DAOFactory;
import lk.ijse.oceansync.entity.Activity;
import lk.ijse.oceansync.model.ActivityDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ActivityBOImpl implements ActivityBO{
    ActivityDAO activityDAO = (ActivityDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ACTIVITY);
    @Override
    public ArrayList<ActivityDTO> getAllActivity() throws SQLException, ClassNotFoundException {
        ArrayList<ActivityDTO> activityDTOS = new ArrayList<>();
        ArrayList<Activity> activities = activityDAO.getAll();
        for (Activity activity : activities){
            activityDTOS.add(new ActivityDTO(activity.getActivityId(),activity.getName(),activity.getType(),activity.getLocation(),activity.getCost()));
        }
        return activityDTOS;
    }

    @Override
    public boolean addActivity(ActivityDTO dto) throws SQLException, ClassNotFoundException {
         return activityDAO.add(new Activity(dto.getActivityId(),dto.getName(),dto.getType(),dto.getLocation(),dto.getCost()));
    }

    @Override
    public boolean updateActivity(ActivityDTO dto) throws SQLException, ClassNotFoundException {
        return activityDAO.update(new Activity(dto.getActivityId(),dto.getName(),dto.getType(),dto.getLocation(),dto.getCost()));
    }

    @Override
    public String generateNewActivityID() throws SQLException, ClassNotFoundException {
        return activityDAO.generateNewID();
    }

    @Override
    public boolean deleteActivity(String id) throws SQLException, ClassNotFoundException {
        return activityDAO.delete(id);
    }

    @Override
    public ActivityDTO searchActivity(String id) throws SQLException, ClassNotFoundException {
        Activity activity = activityDAO.search(id);
        return new ActivityDTO(activity.getActivityId(),activity.getName(),activity.getType(),activity.getLocation(),activity.getCost());
    }

    @Override
    public ActivityDTO selectActivityByName(String name) throws SQLException {
        Activity activity = activityDAO.selectActivityByName(name);
        return new ActivityDTO(activity.getActivityId(),activity.getName(),activity.getType(),activity.getLocation(),activity.getCost());
    }
}
