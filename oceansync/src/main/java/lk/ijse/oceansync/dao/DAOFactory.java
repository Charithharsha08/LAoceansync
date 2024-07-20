package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.entity.SelectedStock;
import lk.ijse.oceansync.model.SelectedStockDTO;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public static DAOFactory getDaoFactory(){
        if (daoFactory == null){
            daoFactory =new DAOFactory();
        }
        return daoFactory;
    }
    private DAOFactory(){
    }
    public enum DAOTypes{
      ACTIVITY, COURSE,CUSTOMER,DISCOUNT,EMPLOYEE,PAYMENT,PAYMENTDETAIL,SELECTEDACTIVITY,SELECTEDCOURCE,STOCK,USER
    }
    public static CrudDAO getDAO(DAOTypes daoTypes ) {
       /* switch (daoTypes) {
            case USER:
                return new UserDAOImpl();
            case STOCK:
                return new StockDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case COURSE:
                return new CourseDAOImpl();
            case ACTIVITY:
                return new ActivityDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case SELECTEDACTIVITY:
                return new SelectedActivityDAOImpl();
            case SELECTEDCOURCE:
                return new SelectedCourceDAOImpl();
            case PAYMENTDETAIL:
                return new PaymentDetailDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case DISCOUNT:
                return new DiscountDAOImpl();
            default:
                return null;
        }*/
        return switch (daoTypes) {
            case DISCOUNT -> new DiscountDAOImpl();
            case EMPLOYEE -> new EmployeeDAOImpl();
            case PAYMENTDETAIL -> new PaymentDAOImpl();
            case SELECTEDCOURCE -> new SelectedCourceDAOImpl();
            case PAYMENT -> new PaymentDAOImpl();
            case ACTIVITY -> new ActivityDAOImpl();
            case COURSE -> new CourseDAOImpl();
            case CUSTOMER -> new CustomerDAOImpl();
            case STOCK -> new StockDAOImpl();
            case USER -> new UserDAOImpl();
            case SELECTEDACTIVITY -> new SelectedActivityDAOImpl();
        };
    }

}
