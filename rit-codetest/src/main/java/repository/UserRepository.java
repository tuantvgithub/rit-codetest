package repository;

import model.User;

import java.util.LinkedList;
import java.util.List;

public class UserRepository {

    private final List<User> userList = new LinkedList<>();

    public void save(User user) {
        for (User u : this.userList)
            if (user.getEmail().equals(u.getEmail()) || user.getId().equals(u.getId()))
                return;

        this.userList.add(user);
    }

    public void saveAll(List<User> userList) {
        for (User u : userList)
            this.save(u);
    }

    public User findById(Long id) {
        for (User u : this.userList)
            if (u.getId().equals(id))
                return u;

        return null;
    }
}
