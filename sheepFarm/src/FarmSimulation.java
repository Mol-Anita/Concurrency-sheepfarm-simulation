package wb1ede375cacd327e78e385945;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class FarmSimulation {
    private static final int NUMBER_OF_SHEEP = 10;
    private static final int NUMBER_OF_DOGS = 5;
    private static final Random random = new Random();
    private static final Farm farm = new Farm();
    private static final AtomicBoolean isOver = new AtomicBoolean(false);
    private static final AtomicBoolean running = new AtomicBoolean(false);
    private static final LinkedList<Sheep> sheep = new LinkedList<>();
    private static final LinkedList<Dog> dogs = new LinkedList<>();
    private static final LinkedList<Thread> threads = new LinkedList<>();


    public static void main(String[] args) {
        System.out.println("\u001B[0;0H");
        placeSheep();
        placeDogs();
        startSimulation();
    }


    private static void startSimulation() {
        for (int i = 0; i < NUMBER_OF_SHEEP; i++) {
            int finalI = i;
            Thread S = new Thread(() -> sheep.get(finalI).makeMove(isOver, farm));
            S.setName("SHEEP_" + i);
            threads.add(S);
            S.start();
        }
        for (int i = 0; i < NUMBER_OF_DOGS; i++) {
            int finalI = i;
            Thread D = new Thread(() -> dogs.get(finalI).makeMove(isOver, farm));
            D.setName("DOG_" + i);
            threads.add(D);
            D.start();
        }
        running.set(true);
        logging();
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        running.set(false);
        System.out.println("One of the sheep successfully got out!");

    }

    private static void placeSheep() {
        int placedSheep = 0;
        int xMin = (farm.getFarmWidth() - 1) / 3 + 1, yMin = (farm.getFarmHeight() - 2) / 3 + 1;
        int xMax = (farm.getFarmWidth() - 1) * 2 / 3, yMax = (farm.getFarmHeight() - 2) * 2 / 3;
        while (placedSheep < NUMBER_OF_SHEEP) {
            int x = xMin + random.nextInt(xMax - xMin + 1);
            int y = yMin + random.nextInt(yMax - yMin + 1);

            if (farm.isFree(x, y)) {
                Sheep s = new Sheep(x, y);
                farm.setField(x, y, s);
                sheep.add(s);
                placedSheep++;
            }
        }
    }

    private static void placeDogs() {
        int placedDogs = 0;
        int xMin = (farm.getFarmWidth() - 1) / 3 + 1, yMin = (farm.getFarmHeight() - 2) / 3;
        int xMax = (farm.getFarmWidth() - 1) * 2 / 3, yMax = (farm.getFarmHeight() - 2) * 2 / 3;

        while (placedDogs < NUMBER_OF_DOGS) {
            int x, y;

            if (random.nextBoolean()) {
                x = random.nextInt(xMin) + 1;
                if (random.nextBoolean()) {
                    x += xMax;
                }
                y = random.nextInt(farm.getFarmHeight() - 2) + 1;
            } else {

                y = random.nextInt(yMin) + 1;
                if (random.nextBoolean()) {
                    y += yMax;
                }
                x = random.nextInt(farm.getFarmWidth() - 2) + 1;
            }

            if (farm.isFree(x, y)) {
                Dog d = new Dog(x, y);
                farm.setField(x, y, d);
                dogs.add(d);
                placedDogs++;
            }
        }
    }

    private static void logging() {
        Thread t = new Thread(() -> {
            while (running.get()) {
                System.out.println("\033[H\033[2J");
                System.out.flush();
                synchronized (farm) {
                    for (int i = 0; i < farm.getFarmHeight(); i++) {
                        for (int j = 0; j < farm.getFarmWidth(); j++) {
                            System.out.print(farm.getFarm()[i][j]);
                        }
                        System.out.print("\n");
                    }
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

}
