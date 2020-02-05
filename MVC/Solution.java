package JavaRushBigTasks.MVC;


import JavaRushBigTasks.MVC.controller.Controller;
import JavaRushBigTasks.MVC.model.MainModel;
import JavaRushBigTasks.MVC.model.Model;
import JavaRushBigTasks.MVC.view.EditUserView;
import JavaRushBigTasks.MVC.view.UsersView;

public class Solution {
    public static void main(String[] args) {
        Model model = new MainModel();
        UsersView usersView = new UsersView();
        Controller controller = new Controller();

        usersView.setController(controller);
        controller.setModel(model);
        controller.setUsersView(usersView);

        usersView.fireEventShowAllUsers();
        EditUserView editUserView = new EditUserView();
        editUserView.setController(controller);
        controller.setEditUserView(editUserView);
        usersView.fireEventOpenUserEditForm(126L);
        editUserView.fireEventUserDeleted(124L);
        editUserView.fireEventUserChanged("Makarov", 125, 5);
        usersView.fireEventShowDeletedUsers();

    }
}