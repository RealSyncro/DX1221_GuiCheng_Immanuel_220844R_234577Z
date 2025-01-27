package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ig_ui;

import android.graphics.Canvas;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.PlayerObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.ButtonUI;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

import java.util.Vector;

public class InputController {
    // UI Interface for Input Buttons
    // Button Types:
    // Normal: ButtonUI
    // Inherited: PauseButton

    private final PlayerObject player;
    public final PauseButton pauseButton;
    public final ButtonUI leftArrow, rightArrow;
    public final ButtonUI jumpButton;
    private final Vector<ButtonUI> _controls;
    private final boolean isAccelEnabled;
    private float _timerStart;

    public InputController(PlayerObject reference) {
        float screenRatio = 20;
        float screenX = GameActivity.instance.getResources().getDisplayMetrics().widthPixels / screenRatio;
        float screenY = GameActivity.instance.getResources().getDisplayMetrics().heightPixels / screenRatio;

        Vector2 _buttonSize = new Vector2(screenX * 5f, screenX * 5f);

        // Set-up position of button relative to screen.
        Vector2 PAUSE_POS = new Vector2(screenX * 3, screenY * 2);
        Vector2 LA_POS = new Vector2(screenX * 3, screenY * 18);
        Vector2 RA_POS = new Vector2(screenX * 10, screenY * 18);
        Vector2 J_POS = new Vector2(screenX * 16, screenY * 18);
//        Vector2 DEBUG_POS = new Vector2(screenX * 16, screenY * 12);

        pauseButton = new PauseButton(PAUSE_POS, new Vector2(screenX * 4f, screenX * 4f), R.drawable.inactive_pause, R.drawable.active_pause);
        leftArrow = new ButtonUI(LA_POS, _buttonSize, R.drawable.inactive_left, R.drawable.active_left);
        rightArrow = new ButtonUI(RA_POS, _buttonSize, R.drawable.inactive_right, R.drawable.active_right);
        jumpButton = new ButtonUI(J_POS, _buttonSize, R.drawable.inactive_jump, R.drawable.active_jump);

        _controls = new Vector<>();
        _controls.add(pauseButton);
        _controls.add(leftArrow);
        _controls.add(rightArrow);
        _controls.add(jumpButton);

        player = reference;
        _timerStart = 3.0f;
        isAccelEnabled = SaveSystem.Get().GetAccelSetting();
    }

    public void OnUpdate(float dt) {

        if (_timerStart > 0.0f) {
            _timerStart -= dt;
            return;
        }

        if (isAccelEnabled)
        {
            if (GameActivity.instance.areSensorsWorking()) {
                // Control player movement using accelerometer
                SensorEvent sensorEvent = GameActivity.instance.getSensorEvent();

                if (sensorEvent != null) {
                    if (sensorEvent.values != null)
                    {
                        float direction = sensorEvent.values[0];

                        if (direction < 0) player.rigidbody._force.x = player.speed * 100f;
                        else if (direction > 0) player.rigidbody._force.x = -player.speed * 100f;
                    }
                }
            }
        }

        // Linearly Interpolate player jump force over time.
        if (player.jumpTimer > 0f) {
            player.jumpTimer -= 0.01f;
            player.upForce = -player.speed * 500f; // 500f
        }
        else {
            player.upForce = 0f;
        }

        player.jumpButtonCD = player.jumpButtonCD > 0f ? player.jumpButtonCD - dt : 0f;

        // Checks if buttons is active. If so, do something/
        if (leftArrow._isActive)
            player.rigidbody._force.x = -player.speed * 100f;

        if (rightArrow._isActive)
            player.rigidbody._force.x = player.speed * 100f;

        if (jumpButton._isActive && player.jumpButtonCD <= 0f && player.rigidbody._isGrounded)
            player.Jump();


        player.rigidbody._force.y += player.upForce;
        pauseButton.onUpdate(dt);
    }

    public void onRender(Canvas canvas) {
        for (ButtonUI buttonUI : _controls)
            buttonUI.onRender(canvas);
    }


    // Checks user finger touch input on the phone screen. (Called from GameActivity).
    public void onTouchEvent(MotionEvent event) {
        if (event != null) {
            int action = event.getActionMasked();
            int actionIndex = event.getActionIndex();
            int pointerId = event.getPointerId(actionIndex);

            switch (action)
            {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                {
                    float tapX = event.getX(actionIndex);
                    float tapY = event.getY(actionIndex);

                    for (ButtonUI button : _controls) {
                        if (button.isPressed(tapX, tapY, 0f, pointerId)) {
                            AudioController.Get().PlayVibration(100, 10);
                            break;
                        }
                    }

//                    System.out.println("Action: " + action);
//                    System.out.println("Action Index: " + actionIndex);
//                    System.out.println("Pointer ID: " + pointerId);
//                    System.out.println("___________________________");
                    break;
                }

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                {
                    for (ButtonUI button : _controls) {
                        if (button.isDeactivated(pointerId))
                            break;
                    }
                    break;
                }
            }
        }


//        if (event != null) {
//            int action = event.getActionMasked();
//            int actionIndex = event.getActionIndex();
//            int pointerId = event.getPointerId(actionIndex);
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
//                for(int i = 0; i < event.getPointerCount(); i++) {
//                    float tapX = event.getX(i);
//                    float tapY = event.getY(i);
//
//                    if (leftArrow.isPressed(tapX, tapY, 0f)) {
//                        player._animatedSprite.PlayAnimation("walkLeft");
//                        player.rigidbody._force.x = -player.speed * 100f;
//                    }
//
//                    if (rightArrow.isPressed(tapX, tapY, 0f)) {
//                        player._animatedSprite.PlayAnimation("walkRight");
//                        player.rigidbody._force.x = player.speed * 100f;
//                    }
//
//                    if (jumpButton.isPressed(tapX, tapY, 0f) && player.rigidbody._isGrounded)
//                        player.Jump();
//
//                    if (debugButton.isPressed(tapX, tapY, 0f))
//                        player.rigidbody._force.y = player.speed * 100f;
//                }
//            }
//        }
    }
}
