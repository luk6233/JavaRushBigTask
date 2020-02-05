package JavaRushBigTasks.MVC.model;


import JavaRushBigTasks.MVC.bean.User;

import java.util.ArrayList;
import java.util.List;

public class ModelData {
    private List<User> users = new ArrayList<>();
    private User activeUser;
    private boolean displayDeletedUserList;

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isDisplayDeletedUserList() {
        return displayDeletedUserList;
    }

    public void setDisplayDeletedUserList(boolean displayDeletedUserList) {
        this.displayDeletedUserList = displayDeletedUserList;
    }
}
