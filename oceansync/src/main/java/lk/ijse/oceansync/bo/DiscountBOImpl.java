package lk.ijse.oceansync.bo;

import lk.ijse.oceansync.dao.DAOFactory;
import lk.ijse.oceansync.dao.DiscountDAO;
import lk.ijse.oceansync.entity.Discount;
import lk.ijse.oceansync.model.DiscountDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountBOImpl implements DiscountBO{
    DiscountDAO discountDAO = (DiscountDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DISCOUNT);

    @Override
    public ArrayList<DiscountDTO> getAllDiscount() throws SQLException, ClassNotFoundException {
        ArrayList<DiscountDTO> discountDTOS =new ArrayList<>();
       ArrayList<Discount> discounts = discountDAO.getAll();
       for (Discount discount : discounts){
           discountDTOS.add(new DiscountDTO(discount.getDiscountId(),discount.getType(),discount.getDiscount()));
       }
        return discountDTOS;
    }

    @Override
    public boolean addDiscount(DiscountDTO entity) throws SQLException, ClassNotFoundException {
        return discountDAO.add(new Discount(entity.getDiscountId(),entity.getType(),entity.getDiscount()));
    }

    @Override
    public String generateNewDiscountID() throws SQLException, ClassNotFoundException {
        return discountDAO.generateNewID();
    }

    @Override
    public boolean deleteDiscount(String id) throws SQLException, ClassNotFoundException {
        return discountDAO.delete(id);
    }

    @Override
    public DiscountDTO searchDiscount(String id) throws SQLException, ClassNotFoundException {
        Discount discount = discountDAO.search(id);
        return new DiscountDTO(discount.getDiscountId(),discount.getType(),discount.getDiscount());
    }
}
