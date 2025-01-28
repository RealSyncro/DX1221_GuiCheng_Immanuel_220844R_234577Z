package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.PlayerObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Rigidbody2D;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

import java.util.Vector;

public class Physics2D {
    private final float _gravity;
    private float _countdown = 1.5f;
    private Vector2 temp, acceleration, displacement;

    public Physics2D(float gravity) {
        _gravity = gravity;
        temp = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        displacement = new Vector2(0, 0);
    }

    public void onUpdate(float dt, Vector<GameObject> goList, Vector<GameObject> platformList) {

        // Pause all physics calculation until player is fully loaded.
        if (_countdown > 0f) _countdown -= dt;
        else if (_countdown <= 0f) _countdown = 0f;

        if (_countdown == 0f) {
            for (GameObject go : goList) {
                PhysicsCalculation(go, dt);
            }

            for (GameObject platform : platformList) {
                PhysicsCalculation(platform, dt);
            }
        }
    }

    private void PhysicsCalculation(GameObject go, float dt) {

        // Check if Rigidbody is affected by gravity
        if (go.rigidbody.type == Rigidbody2D.TYPE.DYNAMIC)
        {
            temp = new Vector2(go.rigidbody._vel);

            float density = 3.0f;

            go.rigidbody._airDrag.x = (0.5f * density * 1.0f * 1.0f) * temp.x * temp.x;
            go.rigidbody._airDrag.y = (0.5f * density * 1.0f * 1.0f) * temp.y * temp.y;

            // Resist opposing forces
            if (temp.x > 0.0f) go.rigidbody._force.x -= go.rigidbody._airDrag.x;
            else go.rigidbody._force.x += go.rigidbody._airDrag.x;

            if (temp.y > 0.0f) go.rigidbody._force.y -= go.rigidbody._airDrag.y;
            else go.rigidbody._force.y += go.rigidbody._airDrag.y;

            // Calculate acceleration
            float multiplier = 1.0f / go.rigidbody._mass;
            go.rigidbody._force.multiply(multiplier);

            acceleration = new Vector2(go.rigidbody._force);

            // Add additional gravity for player so they fall to platform faster.
            if (go instanceof PlayerObject){
                if (!go.rigidbody._isGrounded)
                    acceleration.y += _gravity + 25000f;
            }
            else {
                acceleration.y += _gravity;
            }


            // Update velocity
            acceleration.multiply(dt);
            temp.add(acceleration);
            go.rigidbody._vel = temp;

            // Speed limit
//            float maxSpeed = 500.0f;
//            if (go.rigidbody._vel.getMagnitude() > maxSpeed) {
//                go.rigidbody._vel.normalize();
//                go.rigidbody._vel.multiply(maxSpeed);
//            }

            // Update position
            if (go.rigidbody._force.x < 0 || go.rigidbody._force.x > 0 ||
                    go.rigidbody._force.y < 0 || go.rigidbody._force.y > 0 || !go.rigidbody._isGrounded)
            {
                float displace = (0.5f * dt);
                go.rigidbody._vel.add(temp);
                go.rigidbody._vel.multiply(displace);

                displacement = new Vector2(go.rigidbody._vel);
                
                go.rigidbody._position.add(displacement);
            }

            ResetForce(go, go.rigidbody._force);
        }
    }

    private void ResetForce(GameObject go, Vector2 netForce) {
        if (netForce.x == 0.0f)
            go.rigidbody._vel.x = 0.0f;

        if (netForce.y == 0.0f)
            go.rigidbody._vel.y = 0.0f;

        go.rigidbody._force.SetZero();
    }
}




//    public void ResetVelocity(GameObject go) {
//        if (go.rigidbody._vel.x > 0 && go.rigidbody._vel.x < 0.3f
//                || go.rigidbody._vel.x > -0.3f && go.rigidbody._vel.x < 0f)
//            go.rigidbody._vel.x = 0f;
//
//        if (go.rigidbody._vel.y > 0f && go.rigidbody._vel.y < 0.3f ||
//                go.rigidbody._vel.y > -0.3f && go.rigidbody._vel.y < 0f)
//            go.rigidbody._vel.y = 0f;
//    }

//Vector2 F_Resistant = Vector2.zero();
// Adds resistant force to dynamic body
//                if (!go.rigidbody._vel.isZero())
//                {
//                    if (go.rigidbody._vel.x > 0f)
//                        F_Resistant.x = -go.rigidbody._mass;
//                    else if (go.rigidbody._vel.x < 0f)
//                        F_Resistant.x = go.rigidbody._mass;
//
//                    if (go.rigidbody._vel.y < 0f) // Is in the Air
//                        F_Resistant.y = 15f;
//                    else if (go.rigidbody._vel.y > 0f)
//                        go.rigidbody._vel.y = 15f;
//                }
//
//                float aX = (go.rigidbody._force.x + F_Resistant.x) / go.rigidbody._mass;
//                float aY = (go.rigidbody._force.y + F_Resistant.y) / go.rigidbody._mass;
//
//                Vector2 Acceleration = new Vector2(aX, aY);
//                Vector2 InitialVelocity = new Vector2(go.rigidbody._vel);
//
//                // Final velocity
//                go.rigidbody._vel.x += Acceleration.x * _timeScale * dt;
//                go.rigidbody._vel.y += Acceleration.y * _timeScale * dt;
//
//                // s = 1/2(u+v)t
//                float displacementX = 0.5f * (InitialVelocity.x + go.rigidbody._vel.x) * _timeScale * dt;
//                float displacementY = 0.5f * (InitialVelocity.y + go.rigidbody._vel.y) * _timeScale * dt;
//                go.rigidbody._position.x += displacementX;
//                go.rigidbody._position.y += displacementY;
//
//                go.rigidbody._force = Vector2.zero();
//                ResetVelocity(go);