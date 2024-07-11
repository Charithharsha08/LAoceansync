package lk.ijse.oceansync.view.tdm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class ActivityTm {

    private String activityId;
    private String name;
    private String type;
    private String location;
    private double cost;
}
