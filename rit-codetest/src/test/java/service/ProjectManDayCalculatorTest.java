package service;

import model.Assignment;
import model.Project;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import repository.AssignmentRepository;
import repository.ProjectRepository;
import repository.UserRepository;
import utils.DateUtil;

import static org.junit.jupiter.api.Assertions.*;


public class ProjectManDayCalculatorTest {

    public ProjectManDayCalculator projectManDayCalculator;

    @Test
    @DisplayName("プロジェクトを見つからない")
    public void test1() {
        projectManDayCalculator = new ProjectManDayCalculator(new ProjectRepository(),
                new UserRepository(), new AssignmentRepository());

        assertEquals(-1, projectManDayCalculator.calculate(1L));
    }

    @Test
    @DisplayName("プロジェクトには、アサインが全然ない")
    public void test2() {
        ProjectRepository projectRepository = new ProjectRepository();
        projectRepository.save(new Project(1L, "Gopal rescue",
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("30/07/2022", "dd/MM/yyyy"),
                101.1));

        projectManDayCalculator = new ProjectManDayCalculator(projectRepository,
                new UserRepository(), new AssignmentRepository());

        assertEquals(0, projectManDayCalculator.calculate(1L));
    }

    @Test
    @DisplayName("プロジェクトには、１つの人員、1つのアサイン")
    public void test3() {
        ProjectRepository projectRepository = new ProjectRepository();
        projectRepository.save(new Project(1L, "Gopal rescue",
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("30/07/2022", "dd/MM/yyyy"),
                101.1));

        UserRepository userRepository = new UserRepository();
        userRepository.save(new User(1L, "boboiboy@gmail.com", 10));

        AssignmentRepository assignmentRepository = new AssignmentRepository();
        assignmentRepository.save(new Assignment(1L, 1L, 1L,
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("23/07/2022", "dd/MM/yyyy"),
                0.5));

        // ... = num of days assigned * user.amount * rate
        double expected = (23 - 20 + 1) * 10 * 0.5;

        ProjectManDayCalculator projectManDayCalculator =
                new ProjectManDayCalculator(projectRepository, userRepository, assignmentRepository);

        assertEquals(expected, projectManDayCalculator.calculate(1L));
    }

    @Test
    @DisplayName("プロジェクトには、１つの人員、２つのアサイン")
    public void test4() {
        ProjectRepository projectRepository = new ProjectRepository();
        projectRepository.save(new Project(1L, "Gopal rescue",
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("30/07/2022", "dd/MM/yyyy"),
                101.1));

        UserRepository userRepository = new UserRepository();
        userRepository.save(new User(1L, "boboiboy@gmail.com", 10));

        AssignmentRepository assignmentRepository = new AssignmentRepository();
        assignmentRepository.save(new Assignment(1L, 1L, 1L,
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("23/07/2022", "dd/MM/yyyy"),
                0.5));
        assignmentRepository.save(new Assignment(2L, 1L, 1L,
                DateUtil.stringToDate("24/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("25/07/2022", "dd/MM/yyyy"),
                0.8));

        // ... = num of days assigned * user.amount * rate
        double expected = (23 - 20 + 1) * 10 * 0.5 + (25 - 24 + 1) * 10 * 0.8;

        ProjectManDayCalculator projectManDayCalculator =
                new ProjectManDayCalculator(projectRepository, userRepository, assignmentRepository);

        assertEquals(expected, projectManDayCalculator.calculate(1L));
    }

    @Test
    @DisplayName("プロジェクトには、２つの人員、３つのアサイン")
    public void test5() {
        ProjectRepository projectRepository = new ProjectRepository();
        projectRepository.save(new Project(1L, "Gopal rescue",
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("30/07/2022", "dd/MM/yyyy"),
                101.1));
        projectRepository.save(new Project(2L, "Party with Gopal",
                DateUtil.stringToDate("01/08/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("02/08/2022", "dd/MM/yyyy"),
                101.1));

        UserRepository userRepository = new UserRepository();
        userRepository.save(new User(1L, "boboiboy@gmail.com", 10));
        userRepository.save(new User(2L, "shelby@gmail.com", 5));

        AssignmentRepository assignmentRepository = new AssignmentRepository();
        assignmentRepository.save(new Assignment(1L, 1L, 1L,
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("23/07/2022", "dd/MM/yyyy"),
                0.5));
        assignmentRepository.save(new Assignment(2L, 1L, 1L,
                DateUtil.stringToDate("24/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("25/07/2022", "dd/MM/yyyy"),
                0.8));
        assignmentRepository.save(new Assignment(3L, 1L, 2L,
                DateUtil.stringToDate("24/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("25/07/2022", "dd/MM/yyyy"),
                0.5));

        // ... = num of days assigned * user.amount * rate
        double expected = (23 - 20 + 1) * 10 * 0.5 + (25 - 24 + 1) * 10 * 0.8 + (25 - 24 + 1) * 5 * 0.5;

        ProjectManDayCalculator projectManDayCalculator =
                new ProjectManDayCalculator(projectRepository, userRepository, assignmentRepository);

        assertEquals(expected, projectManDayCalculator.calculate(1L));
    }

    @Test
    @DisplayName("特別な場合１：プロジェクトはもう終わった")
    public void test6() {
        ProjectRepository projectRepository = new ProjectRepository();
        projectRepository.save(new Project(1L, "Gopal rescue",
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("25/07/2022", "dd/MM/yyyy"),
                101.1));

        UserRepository userRepository = new UserRepository();
        userRepository.save(new User(1L, "boboiboy@gmail.com", 10));

        AssignmentRepository assignmentRepository = new AssignmentRepository();
        assignmentRepository.save(new Assignment(1L, 1L, 1L,
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("23/07/2022", "dd/MM/yyyy"),
                0.5));

        // ... = num of days assigned * user.amount * rate
        double expected = (23 - 20 + 1) * 10 * 0.5;

        ProjectManDayCalculator projectManDayCalculator =
                new ProjectManDayCalculator(projectRepository, userRepository, assignmentRepository);

        assertEquals(expected, projectManDayCalculator.calculate(1L));
    }

    @Test
    @DisplayName("特別な場合２：プロジェクトには、ちょっと変なアサインがある")
    public void test7() {
        ProjectRepository projectRepository = new ProjectRepository();
        projectRepository.save(new Project(1L, "Gopal rescue",
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("30/07/2022", "dd/MM/yyyy"),
                101.1));

        UserRepository userRepository = new UserRepository();
        userRepository.save(new User(1L, "boboiboy@gmail.com", 10));

        AssignmentRepository assignmentRepository = new AssignmentRepository();
        assignmentRepository.save(new Assignment(1L, 1L, 1L,
                DateUtil.stringToDate("29/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("02/08/2022", "dd/MM/yyyy"),
                0.5));
        assignmentRepository.save(new Assignment(2L, 1L, 1L,
                DateUtil.stringToDate("19/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("21/07/2022", "dd/MM/yyyy"),
                0.5));

        // ... = num of days assigned * user.amount * rate
        double expected = (30 - 29 + 1) * 10 * 0.5 + (21 - 20 + 1) * 10 * 0.5;

        ProjectManDayCalculator projectManDayCalculator =
                new ProjectManDayCalculator(projectRepository, userRepository, assignmentRepository);

        assertEquals(expected, projectManDayCalculator.calculate(1L));
    }

    @Test
    @DisplayName("特別な場合３：プロジェクトには、誰も実行していないアサインがある")
    public void test8() {
        ProjectRepository projectRepository = new ProjectRepository();
        projectRepository.save(new Project(1L, "Gopal rescue",
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("30/07/2022", "dd/MM/yyyy"),
                101.1));

        UserRepository userRepository = new UserRepository();
        userRepository.save(new User(1L, "boboiboy@gmail.com", 10));

        AssignmentRepository assignmentRepository = new AssignmentRepository();
        assignmentRepository.save(new Assignment(1L, 1L, 1L,
                DateUtil.stringToDate("20/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("23/07/2022", "dd/MM/yyyy"),
                0.5));
        assignmentRepository.save(new Assignment(2L, 1L, null,
                DateUtil.stringToDate("24/07/2022", "dd/MM/yyyy"),
                DateUtil.stringToDate("25/07/2022", "dd/MM/yyyy"),
                0.8));

        // ... = num of days assigned * user.amount * rate
        double expected = (23 - 20 + 1) * 10 * 0.5;

        ProjectManDayCalculator projectManDayCalculator =
                new ProjectManDayCalculator(projectRepository, userRepository, assignmentRepository);

        assertEquals(expected, projectManDayCalculator.calculate(1L));
    }
}
