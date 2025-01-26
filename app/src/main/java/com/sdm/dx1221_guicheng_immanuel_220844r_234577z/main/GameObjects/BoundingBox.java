package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;

public class BoundingBox extends GameObject {
    private static Bitmap _spriteHorizontal, _spriteVertical;
    private final AnimatedSprite _animatedSprite;
    public boolean _isHorizontal;
    public boolean _ignoreSpawn;

    public BoundingBox(int xPosFlex, int yPosFlex, int filepath, boolean isHorizontal, boolean IP) {
        type = TYPE.BOUNDING_BOX;
        _ignoreSpawn = IP;

        if (_spriteHorizontal == null || _spriteVertical == null)
        {
            _spriteHorizontal = FileSystem.LoadScaledSprite(filepath, 0.1f, 10f, true);
            _spriteVertical = FileSystem.LoadScaledSprite(filepath, 10f, 0.05f, true);
        }

        Bitmap _bmpCurrent;
        if (isHorizontal) _bmpCurrent = _spriteHorizontal;
        else _bmpCurrent = _spriteVertical;

        _animatedSprite = new AnimatedSprite(_bmpCurrent, 1,  5, 24);

        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 100) * xPosFlex;
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 100) * yPosFlex;
        rigidbody._size = new Vector2((float) _bmpCurrent.getWidth(), (float) _bmpCurrent.getHeight());

        _isHorizontal = isHorizontal;
        _isActive = true;
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
    }

    @Override
    public void onDisable() {}

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
//        _animatedSprite.onRender(canvas, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
    }
}
