package lk.ijse.oceansync.bo;

import lk.ijse.oceansync.bo.custom.impl.*;

public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        if (boFactory == null){
            boFactory = new BOFactory();
        }return boFactory;
    }
    public enum BOTypes{
        CUSTOMER,ACTIVITY,COURCE,EMPLOYEE,DISCOUNT,STOCK,USER,PAYMENT
    }
    public SuperBO getBO(BOTypes boTypes ){
        switch (boTypes){
            case USER:
                return new UserBOImpl();
            case STOCK:
                return new StockBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case COURCE:
                return new CourseBOImpl();
            case ACTIVITY:
                return new ActivityBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case DISCOUNT:
                return new DiscountBOImpl();
            default:
                return null;
        }
    }
}
