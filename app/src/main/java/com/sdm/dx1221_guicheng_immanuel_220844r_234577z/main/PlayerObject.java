package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.InputController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;

public class PlayerObject extends GameObject {

    private final InputController _inputReceiver;
    private final AnimatedSprite _animatedSprite;
    private int _currentPointerId;

    public PlayerObject() {
        rigidbody._InitDynamicBody(1f);
        rigidbody._position.x = (float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2;
        rigidbody._position.y = (float) GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 2;

        Bitmap sprite = FileSystem.LoadScaledSprite(R.drawable.sonic, 0.5f, 0.5f, true);
        _inputReceiver = new InputController();
        _animatedSprite = new AnimatedSprite(sprite, 1,  16, 12);
        rigidbody._size = new Vector2((float) sprite.getWidth() / 16, (float) sprite.getHeight());

        _animatedSprite.AddAnimation("idle", 0, 0);
        _animatedSprite.PlayAnimation("idle");

        //_srcRect = new Rect(0, 0, _sprite.getWidth() / 7, _sprite.getHeight());
        //_dstRect = new Rect();
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
        _inputReceiver.OnUpdate(dt);

        _animatedSprite.update(dt);
        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();
        if (motionEvent == null) return;

        int action = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        float tapX, tapY;

        // Track pointer id when down, and stops when pointer is up
        if (_currentPointerId == -1 &&
                (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)) {
            _currentPointerId = pointerId;
        } else if (_currentPointerId == pointerId &&
                (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP)) {
            _currentPointerId = -1;
        }

        if (_currentPointerId != -1) {
        // i == action index
            for(int i = 0; i < motionEvent.getPointerCount(); i++) {
                if (motionEvent.getPointerId(i) != _currentPointerId) continue;

                tapX = motionEvent.getX(i);
                tapY = motionEvent.getY(i);

                if (_inputReceiver.leftArrow.onDetect(tapX, tapY, 0f)) {
                    rigidbody._force.x -= 200f * dt;
                }
                else if (_inputReceiver.rightArrow.onDetect(tapX, tapY, 0f)) {
                    rigidbody._force.x += 200f * dt;
                }
                else if (_inputReceiver.jumpButton.onDetect(tapX, tapY, 0f)) {
                    rigidbody._force.y -= 300f * dt;
                }
                else if (_inputReceiver.debugButton.onDetect(tapX, tapY, 0f)) {
                    rigidbody._force.y += 300f * dt;
                }

//                rigidbody._vel = new Vector2(0f, 10f);
            }
        }
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        _animatedSprite.onRender(canvas, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
        _inputReceiver.onRender(canvas);

        //_dstRect.left = (int) _position.x - _sprite.getWidth() / 7 / 2;
        //_dstRect.top = (int) _position.y - _sprite.getHeight() / 2;
        //_dstRect.right = (int) _position.x + _sprite.getWidth() / 7 / 2;
        //_dstRect.bottom = (int) _position.y + _sprite.getHeight() / 2;
        //canvas.drawBitmap(_sprite, _position.x, _position.y, null);
        //canvas.drawBitmap(_sprite, _srcRect, _dstRect, null);
    }
}
