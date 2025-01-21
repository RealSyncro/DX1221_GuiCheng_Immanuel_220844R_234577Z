package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.InputController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;

public class PlayerObject extends GameObject {
    private final InputController _inputReceiver;
    public final AnimatedSprite _animatedSprite;
    public final float speed = 300f;
    public float upForce;
    public float jumpTimer = 0f;
    public float jumpButtonCD = 0f;
    private Bitmap _sprite;
    private static final Vector2 size = new Vector2(0, 0);

    public PlayerObject() {
        _inputReceiver = new InputController(this);
        GameActivity._InputController = _inputReceiver;

        if (_sprite == null)
        {
            _sprite = FileSystem.LoadScaledSprite(R.drawable.sonic, 0.5f, 0.5f, true);
            size.x = (float) _sprite.getWidth() / 15;
            size.y = (float) _sprite.getHeight();
        }

        _animatedSprite = new AnimatedSprite(_sprite, 1,  15, 12);
        _animatedSprite.AddAnimation("idle", 0, 0);
        _animatedSprite.AddAnimation("walkRight", 5, 9);
        _animatedSprite.AddAnimation("walkLeft", 10, 13);
        _animatedSprite.PlayAnimation("idle");

        rigidbody._InitDynamicBody(1f);
        rigidbody._position.x = (float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2;
        rigidbody._position.y = (float) GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 2;
        rigidbody._size = size;

        upForce = 0f;

        //_srcRect = new Rect(0, 0, _sprite.getWidth() / 7, _sprite.getHeight());
        //_dstRect = new Rect();
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);

        _animatedSprite.update(dt);
        _inputReceiver.OnUpdate(dt);

        // Update player sprite animation depending on player's current speed.
        if (rigidbody._vel.x == 0) _animatedSprite.PlayAnimation("idle");
        else if (rigidbody._vel.x < 0) _animatedSprite.PlayAnimation("walkLeft");
        else if (rigidbody._vel.x > 0) _animatedSprite.PlayAnimation("walkRight");
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

    public void Jump() {
        jumpButtonCD = 1f;
        jumpTimer = 0.15f;
        AudioManager.Get().PlaySFX(GameActivity.instance, R.raw.player_jump);
    }
}






//        if (jumpTimer > 0f) {
//            jumpTimer -= dt;
//            upForce = -speed * 500f;
//            _animatedSprite.PlayAnimation("jump");
//        }
//        else {
//            upForce = 0f;
//        }
//
//        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();
//        if (motionEvent == null) {
//            return;
//        }
//        else {
//            int action = motionEvent.getActionMasked();
//            int actionIndex = motionEvent.getActionIndex();
//            int pointerId = motionEvent.getPointerId(actionIndex);
//
//            // Track pointer id when down, and stops when pointer is up
//            if (_currentPointerId == INVALID_POINTER &&
//                    (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)) {
//                _currentPointerId = pointerId;
//                System.out.println("Action: " + action);
//                System.out.println("Action Index: " + actionIndex);
//                System.out.println("Pointer ID: " + pointerId);
//                System.out.println("___________________________");
//
//
//            } else if (_currentPointerId == pointerId &&
//                    (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP)) {
//                _currentPointerId = INVALID_POINTER;
//            }
//            else if (_currentPointerId == pointerId && action == MotionEvent.ACTION_CANCEL) {
//                _currentPointerId = INVALID_POINTER;
//            }
//
//            if (_currentPointerId != -1) {
//                // i == action index
//                for(int i = 0; i < motionEvent.getPointerCount(); i++) {
//                    if (motionEvent.getPointerId(i) != _currentPointerId) continue;
//                    final float tapX = motionEvent.getX(i);
//                    final float tapY = motionEvent.getY(i);
//
//                    if (_inputReceiver.leftArrow.onDetect(tapX, tapY, 0f)) {
//                        _animatedSprite.PlayAnimation("walkLeft");
//                        rigidbody._force.x = -speed * 100f;
//                    }
//
//                    if (_inputReceiver.rightArrow.onDetect(tapX, tapY, 0f)) {
//                        _animatedSprite.PlayAnimation("walkRight");
//                        rigidbody._force.x = speed * 100f;
//                    }
//
//                    if (_inputReceiver.jumpButton.onDetect(tapX, tapY, 0f) && rigidbody._isGrounded)
//                        Jump();
//
//                    if (_inputReceiver.debugButton.onDetect(tapX, tapY, 0f))
//                        rigidbody._force.y = speed * 100f;
//                }
//            }
//            else if (rigidbody._isGrounded) {
//                _animatedSprite.PlayAnimation("idle");
//            }
//        }
//
//        rigidbody._force.y += upForce;