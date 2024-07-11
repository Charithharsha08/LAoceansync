package lk.ijse.oceansync.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentDetailDTO {
    private String paymentId;
    private String customerId;
    private String name;
    private String id;
    private String description;
    private double unitPrice;
    private int qty;
    private double discount;
    private double total;

}
