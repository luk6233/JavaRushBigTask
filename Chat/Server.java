package JavaRushBigTasks.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main (String[] args) throws IOException {
        int port = ConsoleHelper.readInt();
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server is running");
        while (true) {
            try {
                Socket socket = server.accept();
                new Handler(socket).start();
            } catch (Exception e) {
                e.printStackTrace();
                server.close();
                break;
            }
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for(Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
            try {
                pair.getValue().send(message);
            } catch (IOException e) {
                System.out.println("Message didn't send");;
            }
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage("We have connection with " + socket.getRemoteSocketAddress());
            String userName = "";
            try (Connection connection = new Connection(socket)) {
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection, userName);
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Exchange message error");
            }
            if(connectionMap.containsKey(userName)) connectionMap.remove(userName);
            Server.sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            ConsoleHelper.writeMessage("Connection has been closed");
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            connection.send(new Message(MessageType.NAME_REQUEST));
            Message message = connection.receive();
            if(message.getType() != MessageType.USER_NAME) return serverHandshake(connection);
            String name = message.getData();
            if (name == "" || connectionMap.containsKey(name)) return serverHandshake(connection);

            connectionMap.put(name, connection);

            connection.send(new Message(MessageType.NAME_ACCEPTED));

            return name;

        }

        private void notifyUsers(Connection connection, String userName) throws IOException {
            for(Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
                if(!(pair.getKey().equals(userName))) connection.send(new Message(MessageType.USER_ADDED, pair.getKey()));
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    String s = String.format("%s: %s", userName, message.getData());
                    sendBroadcastMessage(new Message(MessageType.TEXT, s));
                }
                else {
                    ConsoleHelper.writeMessage("This is not text message");
                }
            }
        }
    }
}
