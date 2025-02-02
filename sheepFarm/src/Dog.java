package wb1ede375cacd327e78e385945;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Dog {
    private static int dogCount;
    private final int dogNumber;
    private int xCoord;
    private int yCoord;

    public Dog(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        dogNumber = dogCount++;
    }

    public void makeMove(AtomicBoolean isOver, Farm farm) {
        while (!isOver.get()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int dx = 0, dy = 0;
            while (dx == 0 && dy == 0) {
                dx = new Random().nextInt(3) - 1;
                dy = new Random().nextInt(3) - 1;
            }
            if (validMoveDog(xCoord + dx, yCoord + dy, farm)) {
                int x1, y1, x2, y2;
                if (xCoord < xCoord + dx || (xCoord == xCoord + dx && yCoord < yCoord + dy)) {
                    x1 = xCoord;
                    y1 = yCoord;
                    x2 = xCoord + dx;
                    y2 = yCoord + dy;
                } else {
                    x1 = xCoord + dx;
                    y1 = yCoord + dy;
                    x2 = xCoord;
                    y2 = yCoord;
                }

                synchronized (farm.getFarm()[x1][y1]) {
                    synchronized (farm.getFarm()[x2][y2]) {
                        if (farm.isFree(xCoord + dx, yCoord + dy)) {
                            farm.setField(xCoord, yCoord, new EmptyField());
                            xCoord += dx;
                            yCoord += dy;
                            farm.setField(xCoord, yCoord, this);
                        }

                    }
                }
            }

        }

    }

    private boolean validMoveDog(int x, int y, Farm farm) {
        if (farm.validX(x) && farm.validY(y)) {
            int xMin = (farm.getFarmWidth() - 2) / 3, yMin = (farm.getFarmHeight() - 2) / 3;
            int xMax = (farm.getFarmWidth() - 2) * 2 / 3, yMax = (farm.getFarmHeight() - 2) * 2 / 3;
            return !(x >= xMin && x <= xMax && y >= yMin && y <= yMax);
        }
        return false;
    }

    @Override
    public String toString() {
        return "" + dogNumber;
    }
}
