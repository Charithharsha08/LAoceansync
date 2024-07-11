package lk.ijse.oceansync.dao;

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
      ACTIVITY, COURSE,CUSTOMER,DISCOUNT,EMPLOYEE,PAYMENT,PAYMENTDETAIL,SELECTEDACTIVITY,SELECTEDCOURCE,SELECTEDSTOCK,STOCK,USER
    }
    public static CrudDAO getDAO(DAOTypes daoTypes ){
        switch (daoTypes){
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
        }
    }
}
