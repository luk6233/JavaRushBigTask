/**
 * Клас для пошуку оптимального порядку оплати за допомогою генетичного алгоритму
 * @author Dmytro Lukashchuk
 */
package JavaRushBigTasks.GeneticAlgorithm;

public class TollRoad {
    private static final int[] PAY_POINTS = new int[] {2, 5, 10, 8, 7, 8, 5, 9, 10, 1}; // Вхідні дані
    private static final int TOURNAMENT_COUNT = 5; // Кількість учасників турнірного відбору
    private static final int POPULATION_COUNT = 500; // Розмір популяції
    private static final double MUTATION_PROBABILITY = 1; //Ймовірність мутації в %
    private static final int SUM_COINS = 55; // сума монет у водія
    private PaymentOrder[] population = new PaymentOrder[POPULATION_COUNT]; // Популяція хромосом

    public static void main(String[] args) {
        new TollRoad().calculation();
    }

    /**
     * Метод для розрахунку оптимального порядку оплати
     * Створення почтакової популяції
     * @see TollRoad#createInitialPopulation()
     * @see TollRoad#addCreditToPaymentOrder()
     * Перевірка на знаходження в популяції оптимального варіанту
     * Генерування натупного покоління
     * @see TollRoad#createNewGeneration()
     */

    public void calculation() {
        createInitialPopulation();
        addCreditToPaymentOrder();

        System.out.println("Minimal credit: " + minCredit(PAY_POINTS));

        while (true) {
            for (PaymentOrder paymentOrder : population) {
                if (paymentOrder.getCredit() == minCredit(PAY_POINTS)) {
                    System.out.println("Optimal payment order:");
                    for (int i : paymentOrder.getOrder()) {
                        System.out.print(i + " ");
                    }

                    return;
                }
            }
            createNewGeneration();
            addCreditToPaymentOrder();
        }
    }

    /**
     * Створення початкової популяції розміром populationCount
     */

    public void createInitialPopulation() {
        for (int i = 0; i < POPULATION_COUNT; i++) {
            population[i] = new PaymentOrder();
            PaymentOrder.createRandomOrder(population[i]);
        }
    }

    /**
     * Додавання розміру заборгованності (значення фітнес-функції) для кожного порядку оплати
     * @see PaymentOrder#credit(int[])
     */

    public void addCreditToPaymentOrder() {
        for (int i = 0; i < POPULATION_COUNT; i++) {
            population[i].credit(PAY_POINTS);
        }
    }

    /**
     * Вибір пар для схрещування за допомогою турнірного відбору
     * @see TollRoad#tournament()
     * @return
     */

    public int[][] selectionPairParents() {
        int[][] parents = new int[POPULATION_COUNT][2];

        for (int i = 0; i < POPULATION_COUNT; i++) {
            int father = tournament();
            int mother = tournament();

            while (father != mother) {
                mother = tournament();
            }

            parents[i][0] = father;
            parents[i][1] = mother;
        }

        return parents;
    }

    /**
     * Метод турнірного відбору з кіклькістю учасників турніру tournamentCount
     * @return
     */

    public int tournament() {

        int bestOrder = (int) (Math.random() * POPULATION_COUNT);
        int bestCredit = population[bestOrder].getCredit();

        for (int i = 0; i < TOURNAMENT_COUNT - 1; i++) {
            int tempOrder = (int) (Math.random() * POPULATION_COUNT);
            if (population[tempOrder].getCredit() < bestCredit) {
                bestCredit = population[tempOrder].getCredit();
                bestOrder = tempOrder;
            }
        }

        return bestOrder;
    }

    /**
     * Метод схрещування по двох точках з врахуванням мутації
     * @see TollRoad#mutation(int[])
     * @param fatherOrder - перший экземпляр для схрещування
     * @param motherOrder - другий экземпляр для схрещування
     * @return
     */
    public PaymentOrder crossOveringAndMutation(PaymentOrder fatherOrder, PaymentOrder motherOrder) {
        int[] father = fatherOrder.getOrder();
        int[] mother = motherOrder.getOrder();

        int crossingPoint = (int) (Math.random() * father.length);
        int length = (int) (Math.random() * (father.length - crossingPoint));

        int[] child = new int[father.length];

        System.arraycopy(father, crossingPoint, child , 0, length);

        int k = length;
        for (int i = 0; i < mother.length; i++) {
            boolean contain = false;

            for (int j = 0; j < k; j++) {
                if (mother[i] == child[j]) {
                    contain = true;
                }
            }

            if (!contain) {
                child[k] = mother[i];
                k++;
            }
        }

        if (Math.random() <= MUTATION_PROBABILITY / 100) {
            child = mutation(child);
        }

        PaymentOrder childOrder = new PaymentOrder();
        childOrder.setOrder(child);

        return childOrder;

    }

    /**
     * Мутація в порядку оплати - зміна місцями двох елементів масиву
     * @param order - порядок оплати
     * @return змінений порядок оплати
     */

    public int[] mutation(int[] order) {
        int first = (int) Math.random() * 10;
        int second = (int) Math.random() * 10;
        int temp = order[first];
        order[first] = order[second];
        order[second] = temp;

        return order;
    }

    /**
     * Створення нового покоління в яке входить кращий єкземпляр батьківської популяції
     * @see TollRoad#orderWithMinCredit()
     */

    public void createNewGeneration() {
        int[][] pair = selectionPairParents();
        PaymentOrder[] newGeneration = new PaymentOrder[POPULATION_COUNT];
        newGeneration[0] = orderWithMinCredit();

        for (int i = 1; i < POPULATION_COUNT; i++) {
            PaymentOrder father = population[pair[i][0]];
            PaymentOrder mother = population[pair[i][1]];

            PaymentOrder child = crossOveringAndMutation(father, mother);
            newGeneration[i] = child;
            newGeneration[i].credit(PAY_POINTS);
        }

        population = newGeneration;
    }


    /**
     * Визначення кращої послідовності в поколінні
     * @return послідовність оплати з мінімальною заборгованістю
     */

    public PaymentOrder orderWithMinCredit() {
        int min = population[0].getCredit();
        PaymentOrder result = population[0];
        for (int i = 1; i < POPULATION_COUNT; i++) {
            if (population[i].getCredit() < min) {
                min = population[i].getCredit();
                result = population[i];
            }
        }

        return  result;
    }

    /**
     * Мінімальна заборгованість для вхідної вартості проїзду
     * @param payPoints
     * @return мінімально можливу заборгованість
     */

    public static int minCredit(int[] payPoints) {
        int sum = 0;
        for (int i = 0; i < payPoints.length; i++) {
            sum += payPoints[i];
        }

        return sum - SUM_COINS;
    }
}
