package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core;

import android.graphics.Canvas;

public abstract class GameObject {

    public Rigidbody2D rigidbody = new Rigidbody2D();

    protected int _ordinal = 0;
    public int getOrdinal() { return _ordinal; }

    private boolean _isDone = false;
    public void destroy() { _isDone = true; }
    public boolean canDestroy() { return _isDone; }

    public void onUpdate(float dt) {}

    public abstract void onUpdate();

    public abstract void onRender(Canvas canvas);
}
