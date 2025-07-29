package org.example.world;

public class Coordinates {
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public final boolean equals(Object object) {
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
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean isAdjacent(Coordinates other) {
        int dx = Math.abs(this.x - other.x);
        int dy = Math.abs(this.y - other.y);
        return (dx + dy == 1); // соседняя клетка (вверх/вниз/влево/вправо)
    }

    public int distanceTo(Coordinates other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y); // Манхэттенское расстояние
    }


}
