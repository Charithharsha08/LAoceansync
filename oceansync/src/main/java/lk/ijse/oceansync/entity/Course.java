package lk.ijse.oceansync.entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Course {
    public String courceId;
    public String Name;
    private String Duration;
    private double Cost;
}
