package service;

import model.Assignment;
import model.Project;
import model.User;
import repository.AssignmentRepository;
import repository.ProjectRepository;
import repository.UserRepository;
import utils.DateUtil;

import java.util.Date;
import java.util.List;

public class ProjectManDayCalculator {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    public ProjectManDayCalculator(ProjectRepository projectRepository,
                                   UserRepository userRepository,
                                   AssignmentRepository assignmentRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public double calculate(Long projectId) {
        // Find project by id
        Project project = this.projectRepository.findById(projectId);
        if (project == null)
            return -1;

        // Get all project assignments
        List<Assignment> assignmentList = this.assignmentRepository.findAllByProjectId(project.getId());

        // Calculate the cost for each assignment and add up
        double result = .0;
        for (Assignment assignment : assignmentList) {
            User user = this.userRepository.findById(assignment.getUserId());
            if (user == null) continue;
            Date start = assignment.getStartAt().after(project.getStartAt()) ?
                    assignment.getStartAt() : project.getStartAt();
            Date end = assignment.getEndAt().before(project.getEndAt()) ? assignment.getEndAt() : project.getEndAt();
            int numberOfDays = DateUtil.countNumberOfDays(start, end);
            double cost = numberOfDays * user.getAmount() * assignment.getRate();

            result += cost;
        }

        return result;
    }
}
