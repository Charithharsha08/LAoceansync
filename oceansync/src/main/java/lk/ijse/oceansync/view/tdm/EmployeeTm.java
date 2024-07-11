package lk.ijse.oceansync.view.tdm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class EmployeeTm {
    private String id;
    private String employeeId;
    private String name;
    private String activity;
    private double salary;
    private Date date;
    private String userId;

}
