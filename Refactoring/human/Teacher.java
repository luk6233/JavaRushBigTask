package JavaRushBigTasks.Refactoring.human;

public class Teacher extends UniversityPerson {
    private int numberOfStudents;

    public Teacher(String name, int age, int numberOfStudents) {
        super(name, age);
        this.numberOfStudents = numberOfStudents;
    }

    @Override
    public String getPosition() {
        return "Преподаватель";
    }

    public void live() {
        teach();
    }

    public void teach() {
    }

}