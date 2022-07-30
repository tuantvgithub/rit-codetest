package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private Long id;
    private String name;
    private Date startAt;
    private Date endAt;
    private double amount; // 予算
}
