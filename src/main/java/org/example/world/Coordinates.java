package org.example.world;

public record Coordinates (int x, int y){

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Coordinates that)) return false;

        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
