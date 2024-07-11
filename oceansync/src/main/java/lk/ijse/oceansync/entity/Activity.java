package lk.ijse.oceansync.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class Activity {
    private String activityId;
    private String name;
    private String type;
    private String location;
    private double cost;
}
