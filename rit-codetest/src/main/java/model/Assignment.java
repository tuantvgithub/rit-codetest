package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    private Long id;
    private Long projectId;
    private Long userId;
    private Date startAt;
    private Date endAt;
    private double rate; // 稼働率
}
