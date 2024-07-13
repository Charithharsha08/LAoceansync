package lk.ijse.oceansync.bo.custom.impl;

import lk.ijse.oceansync.bo.custom.PaymentBO;
import lk.ijse.oceansync.dao.*;
import lk.ijse.oceansync.dao.custom.impl.*;
import lk.ijse.oceansync.db.DbConnection;
import lk.ijse.oceansync.entity.*;
import lk.ijse.oceansync.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    PaymentDetailDAO paymentDetailDAO  = (PaymentDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENTDETAIL);
    StockDAO stockDAO = (StockDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCK);
    SelectedActivityDAO selectedActivityDAO = (SelectedActivityDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SELECTEDACTIVITY);
    SelectedCourceDAO selectedCourceDAO = (SelectedCourceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SELECTEDCOURCE);


    @Override
    public String generatePaymentNewID() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNewID();
    }

    @Override
    public boolean placePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        System.out.println("place Payment ekt awa");
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean b = paymentDAO.add(new Payment(dto.getPaymentId(), dto.getType(), dto.getTotal(), dto.getDate(), dto.getCustomerId()));
            if (b) {
                List<PaymentDetailDTO> paymentDetailDTOS = dto.getPaymentDetailDTOS();
                boolean paymentDetaiSaved = false;
                for (PaymentDetailDTO paymentDetailDto : paymentDetailDTOS) {
                    paymentDetaiSaved = paymentDetailDAO.add(new PaymentDetail(paymentDetailDto.getPaymentId(), paymentDetailDto.getCustomerId(), paymentDetailDto.getName(), paymentDetailDto.getId(), paymentDetailDto.getDescription(), paymentDetailDto.getUnitPrice(), paymentDetailDto.getQty(), paymentDetailDto.getDiscount(), paymentDetailDto.getTotal()));
                }
                        if (paymentDetaiSaved) {
                            connection.commit();
                            connection.setAutoCommit(true);
                            updateSelectedCourseAndActivity(dto);
                            return true;
                        }

                }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public void updateSelectedCourseAndActivity(PaymentDTO dto){
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean selectedCourseSaved = false;
            boolean selectedSctivitySaved = false;

                if (dto.getStockDTOS() != null) {
                Stock stock = stockDAO.search(dto.getStockDTOS().getItemId());
                    System.out.println(stock.getQty());
                    System.out.println(dto.getStockDTOS().getQty());
                boolean stockUpdated = stockDAO.update(new Stock(stock.getItemId(), stock.getName(), stock.getPrice(), (stock.getQty() - dto.getStockDTOS().getQty()), stock.getUserId()));
                }
                if (dto.getSelectedCourceDTOS() != null){
                List<SelectedCourceDTO> selectedCourceDTOS = dto.getSelectedCourceDTOS();
                for (SelectedCourceDTO selectedCourceDTO : selectedCourceDTOS){
                    selectedCourseSaved = selectedCourceDAO.add(new SelectedCource(selectedCourceDTO.getCustomerId(),selectedCourceDTO.getCourceId(),selectedCourceDTO.getDate()));
                }

                if (dto.getSelectedActivityDTOS() != null){
                    List<SelectedActivityDTO> selectedActivityDTOS = dto.getSelectedActivityDTOS();

                    for (SelectedActivityDTO selectedCActivityDTO : selectedActivityDTOS){
                        selectedSctivitySaved = selectedActivityDAO.add(new SelectedActivity(selectedCActivityDTO.getActivityId(),selectedCActivityDTO.getCustomerId(),selectedCActivityDTO.getDate()));
                    }
                }
                if (! selectedCourseSaved && selectedSctivitySaved){
                    connection.rollback();
                    connection.setAutoCommit(true);
                }

                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
