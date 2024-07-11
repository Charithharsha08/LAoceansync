package lk.ijse.oceansync.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class Discount {
    private String discountId;
    private String type;
    private double discount;
}
