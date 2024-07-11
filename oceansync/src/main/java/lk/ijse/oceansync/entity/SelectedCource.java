package lk.ijse.oceansync.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SelectedCource {
    private String customerId;
    private String courceId;
    private Date date;

}
