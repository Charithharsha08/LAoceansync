package lk.ijse.oceansync.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class DiscountDTO {
    private String discountId;
    private String type;
    private double discount;
}
