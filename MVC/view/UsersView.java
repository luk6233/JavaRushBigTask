package JavaRushBigTasks.MVC.view;


import JavaRushBigTasks.MVC.bean.User;
import JavaRushBigTasks.MVC.controller.Controller;
import JavaRushBigTasks.MVC.model.ModelData;

public class UsersView implements View {
    private Controller controller;


    @Override
    public void refresh(ModelData modelData) {
        if (modelData.isDisplayDeletedUserList()) {
            System.out.println("All deleted users:");
        }
        else System.out.println("All users:");
        for(User user : modelData.getUsers()) {
            System.out.println("\t" + user);
        }
        System.out.println("===================================================");

    }

    public void fireEventShowAllUsers() {
        controller.onShowAllUsers();
    }

    public void fireEventShowDeletedUsers() {
        controller.onShowAllDeletedUsers();
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void fireEventOpenUserEditForm(long id) {
        controller.onOpenUserEditForm(id);
    }
}
