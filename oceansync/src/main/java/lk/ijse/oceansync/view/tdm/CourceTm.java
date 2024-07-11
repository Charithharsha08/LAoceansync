package lk.ijse.oceansync.view.tdm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data


public class CourceTm {
    private String courceId;
    private String name;
    private String duration;
    private double cost;
}
