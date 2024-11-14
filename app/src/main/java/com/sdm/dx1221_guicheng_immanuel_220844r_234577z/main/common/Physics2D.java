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
                        F_Resistant.x = -go.rigidbody._mass;
                    else if (go.rigidbody._vel.x < 0f)
                        F_Resistant.x = go.rigidbody._mass;

                    if (go.rigidbody._vel.y < 0f) // Is in the Air
                        F_Resistant.y = 10f;
                    else if (go.rigidbody._vel.y > 0f)
                        go.rigidbody._vel.y = 10f;
                }

                float aX = (go.rigidbody._force.x + F_Resistant.x) / go.rigidbody._mass;
                float aY = (go.rigidbody._force.y + F_Resistant.y) / go.rigidbody._mass;

                Vector2 Acceleration = new Vector2(aX, aY);
                Vector2 InitialVelocity = new Vector2(go.rigidbody._vel);

                // Final velocity
                go.rigidbody._vel.x += Acceleration.x * _timeScale * dt;
                go.rigidbody._vel.y += Acceleration.y * _timeScale * dt;

                // s = 1/2(u+v)t
                float displacementX = 0.5f * (InitialVelocity.x + go.rigidbody._vel.x) * _timeScale * dt;
                float displacementY = 0.5f * (InitialVelocity.y + go.rigidbody._vel.y) * _timeScale * dt;
                go.rigidbody._position.x += displacementX;
                go.rigidbody._position.y += displacementY;

                go.rigidbody._force = Vector2.zero();
            }
        }
    }
}
