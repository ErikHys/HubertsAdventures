package utils;

public record Vector2D(double x, double y) {

    public double mul(Vector2D u){
        return this.x * u.x + this.y * u.y;
    }

    public Vector2D mul(double s){
        return new Vector2D(this.x * s, this.y * s);
    }

    public Vector2D add(Vector2D u){
        return new Vector2D(this.x + u.x, this.y + u.y);
    }

    public Vector2D add(double s){
        return new Vector2D(this.x + s, this.y + s);
    }

    public Vector2D sub(Vector2D u){
        return new Vector2D(this.x - u.x, this.y - u.y);
    }

    public Vector2D sub(double s){
        return new Vector2D(this.x - s, this.y - s);
    }
}
