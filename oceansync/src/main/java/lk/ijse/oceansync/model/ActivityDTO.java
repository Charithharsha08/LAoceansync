package lk.ijse.oceansync.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class ActivityDTO {
    private String activityId;
    private String name;
    private String type;
    private String location;
    private double cost;
}
