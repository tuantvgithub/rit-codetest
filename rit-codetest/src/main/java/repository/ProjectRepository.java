package repository;

import model.Project;
import utils.DateUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ProjectRepository {

    private final List<Project> projectList = new LinkedList<>();

    public void save(Project project) {
        if (project.getId() == null) return;
        for (Project p : this.projectList)
            if (p.getId().equals(project.getId()))
                return;

        this.projectList.add(project);
    }

    public void saveAll(List<Project> projectList) {
        for (Project p : projectList)
            this.save(p);
    }

    public Project findById(Long id) {
        for (Project p : this.projectList)
            if (p.getId().equals(id))
                return p;

        return null;
    }
}
