package lk.ijse.oceansync.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Stock {

  private String itemId;
  private String name;
  private double price;
  private int qty;
  private String userId;
}
