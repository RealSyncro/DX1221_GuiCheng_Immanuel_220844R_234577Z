package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ig_ui;

import android.graphics.Canvas;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.PlayerObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.Item;
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
    private final boolean isAccelEnabled;
    private float _timerStart;

    private final PauseButton pauseButton;
    private final ButtonUI leftArrow, rightArrow, jumpButton;
    private ConsumableButton consumableButton;
    private final Vector<ButtonUI> _controls;


    public InputController(PlayerObject reference) {
        player = reference;
        isAccelEnabled = SaveSystem.Get().GetAccelSetting();
        _timerStart = 3.0f;

        float screenRatio = 100; // 20
        float screenX = GameActivity.instance.getResources().getDisplayMetrics().widthPixels / screenRatio;
        float screenY = GameActivity.instance.getResources().getDisplayMetrics().heightPixels / screenRatio;

        Vector2 _buttonSize = new Vector2(screenX * 25f, screenX * 25f);

        // Set-up position of button relative to screen.
        Vector2 PAUSE_POS = new Vector2(screenX * 15f, screenY * 10f);
        Vector2 LA_POS = new Vector2(screenX * 15f, screenY * 90f);
        Vector2 RA_POS = new Vector2(screenX * 50f, screenY * 90f);
        Vector2 J_POS = new Vector2(screenX * 80f, screenY * 90f);

        consumableButton = null;
        pauseButton = new PauseButton(PAUSE_POS, new Vector2(screenX * 20f, screenX * 20f), R.drawable.inactive_pause, R.drawable.active_pause);
        leftArrow = new ButtonUI(LA_POS, _buttonSize, R.drawable.inactive_left, R.drawable.active_left);
        rightArrow = new ButtonUI(RA_POS, _buttonSize, R.drawable.inactive_right, R.drawable.active_right);
        jumpButton = new ButtonUI(J_POS, _buttonSize, R.drawable.inactive_jump, R.drawable.active_jump);

        _controls = new Vector<>();
        _controls.add(pauseButton);
        _controls.add(leftArrow);
        _controls.add(rightArrow);
        _controls.add(jumpButton);

        Item heldItem = SaveSystem.Get().GetHeldItem();
        if (heldItem != null)
        {
            Vector2 CONSUME_POS = new Vector2(screenX * 85f, screenY * 40f);
            Vector2 CONSUME_SIZE = new Vector2(screenX * 15f, screenX * 15f);
            consumableButton = new ConsumableButton(CONSUME_POS, CONSUME_SIZE, heldItem, player);
            _controls.add(consumableButton);
        }
    }

    public void OnUpdate(float dt) {

        if (_timerStart > 0.0f) {
            _timerStart -= dt;
            return;
        }

        pauseButton.onUpdate(dt);

        if (consumableButton != null)
            consumableButton.onUpdate(dt);

        if (isAccelEnabled) {
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

        onPlayerMove(dt);
    }

    public void onRender(Canvas canvas) {
        for (ButtonUI buttonUI : _controls)
            buttonUI.onRender(canvas);
    }


    private void onPlayerMove(float dt) {
        // Checks if buttons is active. If so, do something/
        if (leftArrow._isActive)
            player.rigidbody._force.x = -player.speed * 100f;

        if (rightArrow._isActive)
            player.rigidbody._force.x = player.speed * 100f;

        if (jumpButton._isActive && player.jumpButtonCD <= 0f && player.rigidbody._isGrounded)
            player.Jump();

        // Linearly Interpolate player jump force over time.
        if (player.jumpTimer > 0f) {
            player.jumpTimer -= 0.01f;
            player.upForce = -player.speed * 500f; // 500f
        }
        else {
            player.upForce = 0f;
        }

        player.jumpButtonCD = player.jumpButtonCD > 0f ? player.jumpButtonCD - dt : 0f;
        player.rigidbody._force.y += player.upForce;
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
    }

    private void LogActionPointer(int action, int actionIndex, int pointerId)
    {
        System.out.println("Action: " + action);
        System.out.println("Action Index: " + actionIndex);
        System.out.println("Pointer ID: " + pointerId);
        System.out.println("___________________________");
    }
}
