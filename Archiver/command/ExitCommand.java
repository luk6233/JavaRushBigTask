package JavaRushBigTasks.Archiver.command;

import JavaRushBigTasks.Archiver.ConsoleHelper;

public class ExitCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
