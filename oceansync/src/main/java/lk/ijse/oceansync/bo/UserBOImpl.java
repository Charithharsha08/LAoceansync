package lk.ijse.oceansync.bo;

import lk.ijse.oceansync.dao.DAOFactory;
import lk.ijse.oceansync.dao.UserDAO;
import lk.ijse.oceansync.entity.User;
import lk.ijse.oceansync.model.UserDTO;

import java.sql.SQLException;

public class UserBOImpl implements UserBO{
    UserDAO userDao = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDao.add(new User(dto.getUserId(),dto.getUserName(),dto.getPassword()));
    }

    @Override
    public UserDTO checkUser(String id) throws SQLException, ClassNotFoundException {
       User user = userDao.searchUser(id);
       return new UserDTO(user.getUserId(),user.getUserName(),user.getPassword());
    }
}
