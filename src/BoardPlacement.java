public class BoardPlacement {
    private final int x;
    private final int y;
    private final int oneDimensionalPosition;

    /*public BoardPlacement(int x, int y) {
        this.x = x;
        this.y = y;
    }*/

    public BoardPlacement(int position, int width, int height){
        int y = 1;
        this.oneDimensionalPosition = position;
        while(true){
            if(position <= width){
                this.x = position;
                this.y = y;
                break;
            }
            else{
                y++;
                position -= width;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOneDimensionalPosition(){
        return this.oneDimensionalPosition;
    }

    @Override
    public int hashCode(){
        return (17 * x) + (3 * y);
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof BoardPlacement){
            return this.x == ((BoardPlacement) other).getX() && this.y == ((BoardPlacement) other).getY();
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        return oneDimensionalPosition+" "+x+" "+y;
    }
}
