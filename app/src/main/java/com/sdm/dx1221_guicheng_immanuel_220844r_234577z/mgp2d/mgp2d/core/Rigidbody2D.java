package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core;

public class Rigidbody2D {
    public enum TYPE {
        STATIC,
        DYNAMIC
    }

    public TYPE type;
    public Vector2 _position;
    public Vector2 _size;

    public Vector2 _vel;
    public float _mass;
    public boolean _active;


    public Rigidbody2D(){
        type = TYPE.STATIC;
        _position = new Vector2(0, 0);
        _size = new Vector2(0, 0);
        _vel = Vector2.zero();
        _mass = 0f;
        _active = true;
    }

    public void _InitDynamicBody(float mass) {
        type = TYPE.DYNAMIC;
        _mass = mass;
    }

    public Vector2 getPosition() { return _position.copy(); }

    public Vector2 getSize() { return _size.copy(); }
    public void setSize(Vector2 size) { _size = size; }
}
