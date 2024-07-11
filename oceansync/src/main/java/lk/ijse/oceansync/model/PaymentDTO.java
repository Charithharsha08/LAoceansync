package lk.ijse.oceansync.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentDTO {
    private String paymentId;
    private String type;
    private double total;
    private Date date;
    private String customerId;

    List <PaymentDetailDTO> paymentDetailDTOS ;
    StockDTO stockDTOS;
    List<SelectedActivityDTO> selectedActivityDTOS;
    List<SelectedCourceDTO> selectedCourceDTOS;
    List<SelectedStockDTO> selectedStocks =new ArrayList<>();

    public PaymentDTO(String paymentId, String type, double total, Date date, String customerId) {
        this.paymentId = paymentId;
        this.type = type;
        this.total = total;
        this.date = date;
        this.customerId = customerId;
    }
}
