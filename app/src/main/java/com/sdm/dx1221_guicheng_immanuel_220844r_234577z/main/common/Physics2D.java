package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Rigidbody2D;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

import java.util.Vector;

public class Physics2D {
    private final float _timeScale;

    public Physics2D(float timeScale) {
        _timeScale = timeScale;
    }

    public void onUpdate(float dt, Vector<GameObject> goList) {
        for (GameObject go : goList)
        {
            if (go.rigidbody.type == Rigidbody2D.TYPE.DYNAMIC)
            {
                Vector2 F_Resistant = Vector2.zero();

                if (!go.rigidbody._vel.isZero())
                {
                    if (go.rigidbody._vel.x > 0f)
                        F_Resistant.x = -1f;
                    else
                        F_Resistant.x = 1f;

                    if (go.rigidbody._vel.y > 0f)
                        F_Resistant.y = -1f;
                    else
                        F_Resistant.y = 1f;
                }

                F_Resistant.y += go.rigidbody._mass * -3f;

                Vector2 Acceleration = F_Resistant.multiply(go.rigidbody._mass);
                Vector2 TempVelocity = go.rigidbody._vel;

                go.rigidbody._vel.add(new Vector2(dt * _timeScale * Acceleration.x, dt * _timeScale * Acceleration.y));
                float displacementX = 0.5f * dt * _timeScale * (TempVelocity.x + go.rigidbody._vel.x);
                float displacementY = 0.5f * dt * _timeScale * (TempVelocity.y + go.rigidbody._vel.y);
                go.rigidbody._position.x += displacementX;
                go.rigidbody._position.y += displacementY;
            }
        }
    }
}
