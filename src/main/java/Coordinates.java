public class Coordinates {

    short x, y;

    private Coordinates(short x, short y){
        this.x = x;
        this.y = y;
    }

    private Coordinates(int x, int y){
        this.x = (short) x;
        this.y = (short) y;
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    public String toString(){
        return ""+(char)(65+y)+""+(x+1);
    }

    public boolean equals(Object other){
        boolean equals = false;
        if(other instanceof Coordinates){
            return this.x == ((Coordinates) other).x && this.y==((Coordinates) other).y;
        }
        return equals;
    }

    public int hashCode(){
        return (10 * x) + y;
    }

    public static Coordinates of(int x, int y){
        return new Coordinates(x, y);
    }

    public static Coordinates of(short x, short y){
        return new Coordinates(x, y);
    }

}
