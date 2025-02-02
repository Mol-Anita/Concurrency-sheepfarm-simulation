package wb1ede375cacd327e78e385945;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sheep {
    private int xCoord;
    private int yCoord;

    public Sheep(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;

    }

    public void makeMove(AtomicBoolean isOver, Farm farm) {
        while (!isOver.get()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int dx = 0;
            int dy = 0;
            boolean dogX = false;
            boolean dogY = false;

            for (int i = -1; i <= 1; i++) {
                if (farm.validX(xCoord + i)){
                    synchronized (farm.getFarm()[xCoord + i][yCoord]) {
                        if (farm.isDog(xCoord + i, yCoord)) {
                            dogX = i < 0;
                        }
                    }
                }
                if (farm.validY(yCoord + i)) {
                    synchronized (farm.getFarm()[xCoord][yCoord + i]) {
                        if (farm.isDog(xCoord, yCoord + i)){
                            dogY = i < 0;
                        }
                    }
                }
            }
            dx = decideMoveAlongAxis(dogX, farm);
            dy = decideMoveAlongAxis(dogY, farm);

            while (dx == 0 && dy == 0) {
                dx = new Random().nextInt(3) - 1;
                dy = new Random().nextInt(3) - 1;
            }

            if (farm.validX(dx + xCoord) && farm.validY(dy + yCoord)) {
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
                        if (farm.isGate(xCoord + dx, yCoord + dy)) {
                            farm.setField(xCoord, yCoord, new EmptyField());
                            xCoord += dx;
                            yCoord += dy;
                            farm.setField(xCoord, yCoord, this);
                            isOver.set(true);
                        }

                    }
                }

            }
        }
    }

    private int decideMoveAlongAxis(boolean dogDetected, Farm farm) {
        int dAxis;
        if (dogDetected) {
            dAxis = farm.isFree(xCoord - 1, yCoord) ? -1 : 0;
        } else {
            dAxis = new Random().nextInt(3) - 1;
        }
        return dAxis;
    }


    @Override
    public String toString() {
        return "S";
    }
}
