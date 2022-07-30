package repository;

import model.Assignment;

import java.util.LinkedList;
import java.util.List;

public class AssignmentRepository {

    private final List<Assignment> assignmentList = new LinkedList<>();

    public void save(Assignment assignment) {
        if (assignment.getId() == null) return;
        for (Assignment a : this.assignmentList)
            if (a.getId().equals(assignment.getId()))
                return;

        this.assignmentList.add(assignment);
    }

    public void saveAll(List<Assignment> assignmentList) {
        for (Assignment a : assignmentList)
            this.save(a);
    }

    public List<Assignment> findAllByProjectId(Long projectID) {
        List<Assignment> result = new LinkedList<>();

        for (Assignment a : this.assignmentList)
            if (a.getProjectId().equals(projectID))
                result.add(a);

        return result;
    }
}
