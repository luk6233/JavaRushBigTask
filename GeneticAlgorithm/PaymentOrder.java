/**
 * Клас порядку оплати на пунктах.
 * @author Dmytro Lukashchuk
 */

package JavaRushBigTasks.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentOrder { // Хромосома
    private int[] order; // Послідовність оплати
    private int credit;  // Кредит для даної послідовності оплати

    /**
     * Метод для створення випадкової послідовності оплати монетами від 1 до 10
     * @param paymentOrder - Екземпляр порядку оплати
     */

    public static void createRandomOrder(PaymentOrder paymentOrder) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        paymentOrder.setOrder(array);
    }


    /**
     * Фітнес-функція
     * Метод для розрахунку мінімальної заборгованності для послідовності оплати
     * @param payPoints - вартість проїзду на кожному з пунктів оплати
     */
    public void credit(int[] payPoints) {
        if (payPoints.length != getOrder().length) {
            throw new IllegalArgumentException("Wrong parameter");
        }

        int credit = 0;
        for (int i = 0; i < payPoints.length; i++) {
            int delta = payPoints[i] - getOrder()[i];
            if (delta > 0) {
                credit += delta;
            }
        }

        setCredit(credit);
    }

    public int[] getOrder() {
        return order;
    }

    public void setOrder(int[] order) {
        this.order = order;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
