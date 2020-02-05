package JavaRushBigTasks.MVC.model;


import JavaRushBigTasks.MVC.bean.User;
import JavaRushBigTasks.MVC.model.service.UserService;
import JavaRushBigTasks.MVC.model.service.UserServiceImpl;

import java.util.List;

public class MainModel implements Model {
    private ModelData modelData = new ModelData();
    private UserService userService = new UserServiceImpl();

    private List<User> getAllUsers() {
        return userService.filterOnlyActiveUsers(userService.getUsersBetweenLevels(1, 100));
    }

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers() {
        modelData.setUsers(getAllUsers());
        modelData.setDisplayDeletedUserList(false);
    }

    @Override
    public void loadDeletedUsers() {
        modelData.setUsers(userService.getAllDeletedUsers());
        modelData.setDisplayDeletedUserList(true);
    }

    public void loadUserById(long userId) {
        modelData.setActiveUser(userService.getUsersById(userId));
    }

    public void deleteUserById(long id) {
        modelData.setActiveUser(userService.deleteUser(id));
        loadUsers();
    }

    public void changeUserData(String name, long id, int level) {
        userService.createOrUpdateUser(name, id, level);
        loadUsers();
    }


}
