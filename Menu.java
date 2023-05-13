import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Predicate;

public class Menu {
    public Menu() {
    }

    public static void menu() {
        ToyStore store = new ToyStore();
        Scanner scanner = new Scanner(System.in);
        Toy toy1 = new RubberToy("Мяч", 20, 35);
        Toy toy2 = new RubberToy("Уточка", 10, 25);
        Toy toy3 = new PlasticToy("Кубик Рубик", 10, 50);
        Toy toy4 = new SoftToy("Мишка", 10, 71);
        Toy toy5 = new SoftToy("Зайчик", 10, 20);
        Toy toy6 = new SoftToy("Котик", 10, 45);
        store.addToy(toy1);
        store.addToy(toy2);
        store.addToy(toy3);
        store.addToy(toy4);
        store.addToy(toy5);
        store.addToy(toy6);

        while(true) {
            while(true) {
                System.out.println("Меню:");
                System.out.println("1. Добавить новую игрушку в розыгрыш");
                System.out.println("2. Изменить вероятность выигрыша");
                System.out.println("3. Запустить розыгрыш");
                System.out.println("4. Показать список всех игрушек");
                System.out.println("5. Увеличить количество игрушки по ID");
                System.out.println("6. Выход");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {
                    addNewToy(store, scanner);
                } else if (choice == 2) {
                    changeToyWeights(store, scanner);
                } else if (choice == 3) {
                    startToyDraw(store);
                } else if (choice == 4) {
                    printToys(store);
                } else if (choice == 5) {
                    increaseToy(store, scanner);
                } else if (choice == 6) {
                    scanner.close();
                    return;
                }
            }
        }
    }

    public static void addNewToy(ToyStore store, Scanner scanner) {
        int toyType = promptInt(scanner, "Выберите тип игрушки (1 - пластиковая, 2 - резиновая, 3 - мягкая):", (n) -> {
            return n >= 1 && n <= 3;
        });
        String name = promptString(scanner, "Название игрушки: ");
        int quantity = promptInt(scanner, "Количество: ", (n) -> {
            return n >= 0;
        });
        int weight = promptInt(scanner, "Вес игрушки: ", (n) -> {
            return n > 0;
        });
        switch (toyType) {
            case 1:
                store.addToy(new PlasticToy(name, quantity, weight));
                break;
            case 2:
                store.addToy(new RubberToy(name, quantity, weight));
                break;
            case 3:
                store.addToy(new SoftToy(name, quantity, weight));
        }

    }

    private static int promptInt(Scanner scanner, String prompt, Predicate<Integer> validator) {
        while(true) {
            System.out.print(prompt);

            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (validator.test(n)) {
                    return n;
                }
            } catch (NumberFormatException var4) {
            }

            System.out.println("Что-то пошло не так... Попробуйте ещё раз.");
        }
    }

    private static String promptString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static void changeToyWeights(ToyStore store, Scanner scanner) {
        System.out.printf("Изменение веса вероятности выигрыша игрушек сейчас\n Маленькие игрушки 65 %% вес до: %s\n Средние игрушки  35 %% вес до: %s\n Большие игрушки  10 %% вес до: %s\n Вес маленьких: ", store.getSmallToys(), store.getMediumToys(), store.getBigToys());
        int small = promptInt(scanner, "Вес маленьких: ", (n) -> {
            return n > 0;
        });
        int medium = promptInt(scanner, "Вес средних: ", (n) -> {
            return n > small;
        });
        int big = promptInt(scanner, "Вес больших: ", (n) -> {
            return n > medium;
        });
        store.setSmallToys(small);
        store.setMediumToys(medium);
        store.setBigToys(big);
    }

    public static void startToyDraw(ToyStore store) {
        Toy toy = store.winToy();
        if (toy != null) {
            System.out.println("Ваш выигрыш: " + toy.getName());
            store.saveToyToFile(toy, "ToyStore/prizes.txt");
        } else {
            System.out.println("К сожалению, игрушки закончились");
        }

    }

    public static void printToys(ToyStore store) {
        System.out.println("Список игрушек:");
        Iterator var1 = store.getToys().iterator();

        while(var1.hasNext()) {
            Toy toy = (Toy)var1.next();
            System.out.println(toy.toString());
        }

    }

    public static void increaseToy(ToyStore store, Scanner scanner) {
        int id = promptInt(scanner, "Введите ID игрушки, количество которой хотите увеличить: ", (n) -> {
            return n > 0 && n <= store.getToys().size();
        });
        int quantity = promptInt(scanner, "Введите количество: ", (n) -> {
            return n >= 0;
        });
        store.increaseToyQuantity(id, quantity);
    }
}
