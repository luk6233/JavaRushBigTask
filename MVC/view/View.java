package JavaRushBigTasks.MVC.view;


import JavaRushBigTasks.MVC.controller.Controller;
import JavaRushBigTasks.MVC.model.ModelData;

public interface View {

    public void refresh(ModelData modelData);

    public void setController(Controller controller);


}
