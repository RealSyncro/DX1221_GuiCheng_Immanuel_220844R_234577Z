package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core;

import static java.lang.Math.sqrt;

public class Vector2 {
    public float x, y;

    public Vector2(float x, float y) { this.x = x; this.y = y; }

    public Vector2 (Vector2 vector) {this.x = vector.x; this.y = vector.y;}

    public Vector2 add(Vector2 b) {
        this.x += b.x; this.y += b.y;
        return this;
    }

    public Vector2 multiply(float scale) {
        this.x *= scale; this.y *= scale;
        return this;
    }

    public Vector2 subtract(Vector2 b) { return this.add(b.multiply(-1)); }

    public float getMagnitude() { return (float)sqrt(x * x + y * y); }

    public Vector2 normalize() {
        this.x /= getMagnitude(); this.y /= getMagnitude();
        return this;
    }

    public Vector2 copy() { return new Vector2(x, y); }
    public void SetTo(Vector2 b) {
        this.x = b.x; this.y = b.y;
    }

    public boolean equals(Vector2 other) { return x == other.x && y == other.y; }

    public Vector2 limit(float maxMagnitude) {
        float length = getMagnitude();
        float multiplier = length <= maxMagnitude ? 1 : maxMagnitude / length;
        this.x *= multiplier;
        this.y *= multiplier;
        return this;
    }

    public boolean isZero() {
        return this.getMagnitude() <= 0;
    }

    public void SetZero() {this.x = 0; this.y = 0;}

    public static Vector2 up() {
        return new Vector2(0f, 1f);
    }
    public static Vector2 down() {
        return new Vector2(0f, -1f);
    }
    public static Vector2 left() {
        return new Vector2(1f, 0f);
    }
    public static Vector2 right() {
        return new Vector2(-1f, 0f);
    }

}
