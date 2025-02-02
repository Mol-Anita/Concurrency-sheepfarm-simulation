package wb1ede375cacd327e78e385945;

public class Farm {

    private final int farmHeight;
    private final int farmWidth;
    private static Object[][] farm;

    public Farm(){
        this(14,14);
    }

    public Farm(int farmHeight , int farmWidth) {
        this.farmHeight = farmHeight;
        this.farmWidth = farmWidth;
        farm = new Object[farmHeight][farmWidth];

        for (int i = 0; i < farmHeight; i++){
            for (int j = 0; j < farmWidth; j++){
                if (i == 0 || j == 0 || i == farmHeight-1 || j == farmWidth-1){
                    farm[i][j] = new Wall();
                }
                else{
                    farm[i][j] = new EmptyField();
                }
            }
        }
        generateGates();

    }

    private void generateGates() {
        farm[0][1 + (int) (Math.random() * (farmHeight - 2))] = new Gate();
        farm[farmHeight - 1][1 + (int) (Math.random() * (farmWidth - 2))] = new Gate();
        farm[1 + (int) (Math.random() * (farmHeight - 2))][0] = new Gate();
        farm[1 + (int) (Math.random() * (farmHeight - 2))][farmWidth - 1] = new Gate();
    }

    public Object[][] getFarm() {
        return farm;
    }

    public int getFarmHeight() {
        return farmHeight;
    }

    public int getFarmWidth() {
        return farmWidth;
    }


    public boolean isFree(int x, int y) {
        return farm[x][y] instanceof EmptyField;
    }

    public boolean isDog(int x, int y) {
            return farm[x][y] instanceof Dog;
    }

    public boolean isGate(int x, int y) {
            return farm[x][y] instanceof Gate;
    }

    public void setField(int x, int y, Object value) {
            farm[x][y] = value;
    }

    public boolean validX(int x) {
        return x >= 0 && x < farmHeight;
    }

    public boolean validY(int y) {
        return y >= 0 && y < farmWidth;
    }

}
