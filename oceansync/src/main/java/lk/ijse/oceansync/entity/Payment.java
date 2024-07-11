package lk.ijse.oceansync.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Payment {
    private String paymentId;
    private String type;
    private double total;
    private Date date;
    private String customerId;
}
