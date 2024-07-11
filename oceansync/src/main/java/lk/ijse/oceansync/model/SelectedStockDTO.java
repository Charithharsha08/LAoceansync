package lk.ijse.oceansync.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class SelectedStockDTO {
    private  String itemId;
    private  int qty;
    private String customerId;
    private  String orderId;
    private Date date;
}
