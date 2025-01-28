package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ig_ui.InputController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.Buff;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.Item;

public class PlayerObject extends GameObject {
    private static Bitmap _sprite;
    private static final Vector2 size = new Vector2(0, 0);
    private final AnimatedSprite _animatedSprite;
    private final InputController _inputReceiver;
    private Buff activeBuff;
    public final float speed = 300f;
    public float upForce;
    public float jumpTimer, jumpButtonCD, _consumableCD;

    public PlayerObject() {
        type = TYPE.PLAYER;

        if (_sprite == null)
        {
            _sprite = FileSystem.LoadScaledSprite(R.drawable.sonic, 0.5f, 0.5f, true);
            size.x = (float) _sprite.getWidth() / 15;
            size.y = (float) _sprite.getHeight();
        }

        _animatedSprite = new AnimatedSprite(_sprite, 1,  15, 12);
        _animatedSprite.AddAnimation("idle", 0, 0);
        _animatedSprite.AddAnimation("walkRight", 5, 8);
        _animatedSprite.AddAnimation("walkLeft", 10, 13);
        _animatedSprite.PlayAnimation("idle");

        rigidbody._InitDynamicBody(1f);
        rigidbody._position.x = (float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2;
        rigidbody._position.y = (float) GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 2;
        rigidbody._size = size;

        _inputReceiver = new InputController(this);
        GameActivity._InputController = _inputReceiver;

        activeBuff = null;
        upForce = 0f;
        jumpTimer = 0f;
        jumpButtonCD = 0f;
        _consumableCD = 0f;
        _isActive = true;
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

        if (_consumableCD > 0f) _consumableCD -= dt;

        if (activeBuff != null)
        {
            activeBuff.onUpdate(dt);

            if (!activeBuff._isActive)
                activeBuff = null;
        }
    }

    @Override
    public void onDisable() {}

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        _animatedSprite.onRender(canvas, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
        _inputReceiver.onRender(canvas);

        if (activeBuff != null) activeBuff.onRender(canvas);
    }

    public void Jump() {
        jumpButtonCD = 1f;
        jumpTimer = 0.15f;
        AudioController.Get().PlaySFX(R.raw.player_jump);
    }
    public Buff GetBuff() {return activeBuff;}
    public void ConsumeItem(Item item)
    {
        if (item.ID == 0)
        {
            activeBuff = new Buff(this, 3.0f, 0, R.drawable.aura);
            _consumableCD = 3.0f;
        }
        else if (item.ID == 1)
        {
            activeBuff = new Buff(this, 3.0f, 1, R.drawable.shield);
            _consumableCD = 3.0f;
        }
    }
}