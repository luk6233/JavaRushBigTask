package JavaRushBigTasks.Chat.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BotClient extends Client {
    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            super.processIncomingMessage(message);
            if (!(message.contains(":"))) return;
            String[] m = message.split(": ");
            String name = m[0];
//            System.out.println(name);
            String text = m[1];
//            System.out.println(text);
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            String inf = String.format("Информация для %s: ", name);
            if (text.equals("дата")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d.MM.YYYY");
                sendTextMessage(inf + sdf.format(date));
            }
            else if (text.equals("день")) {
                SimpleDateFormat sdf = new SimpleDateFormat("d");
                sendTextMessage(inf + sdf.format(date));
            }
            else if (text.equals("месяц")) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
                sendTextMessage(inf + sdf.format(date));
            }
            else if (text.equals("год")) {
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
                sendTextMessage(inf + sdf.format(date));
            }
            else if (text.equals("время")) {
                SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
                sendTextMessage(inf + sdf.format(date));
            }
            else if (text.equals("час")) {
                SimpleDateFormat sdf = new SimpleDateFormat("H");
                sendTextMessage(inf + sdf.format(date));
            }
            else if (text.equals("минуты")) {
                SimpleDateFormat sdf = new SimpleDateFormat("m");
                sendTextMessage(inf + sdf.format(date));
            }
            else if (text.equals("секунды")) {
                SimpleDateFormat sdf = new SimpleDateFormat("s");
                sendTextMessage(inf + sdf.format(date));
            }
        }
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        int x = (int) (Math.random() * 100);
        return "date_bot_" + x;
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
