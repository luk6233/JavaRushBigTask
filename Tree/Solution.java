package JavaRushBigTasks.task1916;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* 
Отслеживаем изменения
*/

public class Solution {
    public static List<LineItem> lines = new ArrayList<LineItem>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String file1 = reader.readLine();
        String file2 = reader.readLine();
        reader.close();

        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();

        while(reader1.ready()) {
            list1.add(reader1.readLine());
        }
        while(reader2.ready()) {
            list2.add(reader2.readLine());
        }

        int i = 0;
        while(i < list1.size() && i < list2.size()) {
            if (list1.get(i).equals(list2.get(i))){
                lines.add(new LineItem(Type.SAME, list1.get(i)));
                i++;
            }
            else if(i < list2.size() - 1 && list1.get(i).equals(list2.get(i + 1))){
                lines.add(new LineItem(Type.ADDED, list2.get(i)));
                list2.remove(i);
            }
            else if(i < list1.size() - 1 && list1.get(i + 1).equals(list2.get(i))){
                lines.add(new LineItem(Type.REMOVED, list1.get(i)));
                list1.remove(i);
            }
        }

        if (list1.size() > list2.size()) {
            lines.add(new LineItem(Type.REMOVED, list1.get(list1.size() - 1)));
        }
        else if (list1.size() < list2.size()) {
            lines.add(new LineItem(Type.ADDED, list2.get(list2.size() - 1)));
        }

        reader1.close();
        reader2.close();

    }

    public static enum Type {
        ADDED,        //добавлена новая строка
        REMOVED,      //удалена строка
        SAME          //без изменений
    }

    public static class LineItem {
        public Type type;
        public String line;

        public LineItem(Type type, String line) {
            this.type = type;
            this.line = line;
        }
    }
}
