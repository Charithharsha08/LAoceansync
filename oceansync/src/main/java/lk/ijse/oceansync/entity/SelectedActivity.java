package lk.ijse.oceansync.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class SelectedActivity {
    private String activityId;
    private String customerId;
    private Date date;

}