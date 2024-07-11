package lk.ijse.oceansync.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StockDTO {

  private String itemId;
  private String name;
  private double price;
  private int qty;
  private String userId;

}
