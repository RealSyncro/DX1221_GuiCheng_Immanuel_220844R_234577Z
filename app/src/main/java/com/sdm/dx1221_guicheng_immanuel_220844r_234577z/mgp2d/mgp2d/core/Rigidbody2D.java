package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core;

public class Rigidbody2D {
    public enum TYPE {
        STATIC,
        DYNAMIC
    }

    public TYPE type;
    public Vector2 _position, _size, _force;
    public Vector2 _airDrag, _vel;
    public float _mass;
    public boolean _active;
    public boolean _isGrounded;

    public Rigidbody2D(){
        type = TYPE.STATIC;
        _position = Vector2.zero();
        _size = Vector2.zero();
        _force = Vector2.zero();
        _airDrag = Vector2.zero();
        _vel = new Vector2(0f, 0f);
        _mass = 0f;
        _active = true;
        _isGrounded = false;
    }

    public void _InitDynamicBody(float mass) {
        type = TYPE.DYNAMIC;
        _mass = mass;
    }

    public Vector2 getPosition() { return _position.copy(); }
    public Vector2 getSize() { return _size.copy(); }
    public void setSize(Vector2 size) { _size = size; }
}
