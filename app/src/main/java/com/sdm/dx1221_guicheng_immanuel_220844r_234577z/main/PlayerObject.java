package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
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

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.player_heli_body);
        Bitmap sprite = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * 1.5f), (int) (bmp.getHeight() * 1.5f), true);

        _inputReceiver = new InputController();
        _animatedSprite = new AnimatedSprite(sprite, 1,  7, 24);
        rigidbody._size = new Vector2((float) sprite.getWidth() / 7, (float) sprite.getHeight());

        //_srcRect = new Rect(0, 0, _sprite.getWidth() / 7, _sprite.getHeight());
        //_dstRect = new Rect();
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
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

                if (_inputReceiver.leftArrow.onDetect(tapX, tapY, 30f)) {
                    rigidbody._force.x -= 2f;
                }
                else if (_inputReceiver.rightArrow.onDetect(tapX, tapY, 30f)) {
                    rigidbody._force.x += 2f;
                }
                else if (_inputReceiver.jumpButton.onDetect(tapX, tapY, 30f)) {
                    rigidbody._force.y = -30f;
                }

//                rigidbody._position.x = motionEvent.getX(i);
//                rigidbody._position.y = motionEvent.getY(i);
//                rigidbody._vel = new Vector2(0f, 10f);
            }
        }
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        _animatedSprite.render(canvas, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
        _inputReceiver.onRender(canvas);

        //_dstRect.left = (int) _position.x - _sprite.getWidth() / 7 / 2;
        //_dstRect.top = (int) _position.y - _sprite.getHeight() / 2;
        //_dstRect.right = (int) _position.x + _sprite.getWidth() / 7 / 2;
        //_dstRect.bottom = (int) _position.y + _sprite.getHeight() / 2;
        //canvas.drawBitmap(_sprite, _position.x, _position.y, null);
        //canvas.drawBitmap(_sprite, _srcRect, _dstRect, null);
    }
}
