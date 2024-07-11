package lk.ijse.oceansync.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CourseDTO {
    private String courceId;
    private String Name;
    private String Duration;
    private double Cost;
}
