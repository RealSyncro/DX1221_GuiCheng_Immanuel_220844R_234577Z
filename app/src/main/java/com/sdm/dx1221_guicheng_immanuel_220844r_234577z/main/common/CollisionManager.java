package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

public class CollisionManager {
    private static boolean CheckIfXIntersect(GameObject entityA, GameObject entityB) {
        float aMinX = entityA.rigidbody.getPosition().x - entityA.rigidbody.getSize().x / 2;
        float aMaxX = entityA.rigidbody.getPosition().x + entityA.rigidbody.getSize().x / 2;
        float bMinX = entityB.rigidbody.getPosition().x - entityB.rigidbody.getSize().x / 2;
        float bMaxX = entityB.rigidbody.getPosition().x + entityB.rigidbody.getSize().x / 2;

        if (bMinX <= aMaxX && aMinX <= bMaxX) return true;

        return aMinX <= bMaxX && bMinX <= aMaxX;
    }

    private static boolean CheckIfYIntersect(GameObject entityA, GameObject entityB) {
        float aBtmY = entityA.rigidbody.getPosition().y - entityA.rigidbody.getSize().y / 2;
        float aTopY = entityA.rigidbody.getPosition().y + entityA.rigidbody.getSize().y / 2;
        float bBtmY = entityB.rigidbody.getPosition().y - entityB.rigidbody.getSize().y / 2;
        float bTopY = entityB.rigidbody.getPosition().y + entityB.rigidbody.getSize().y / 2;

        if (bBtmY <= aTopY && aBtmY <= bTopY) return true;

        return aBtmY <= bTopY && bBtmY <= aTopY;
    }

    public static boolean isColliding(GameObject entityA, GameObject entityB) {
        // Calculate boundaries for entityB
        if (CheckIfXIntersect(entityA, entityB)) {
            return CheckIfYIntersect(entityA, entityB);
        }
        return false;
    }

    public static Vector2 ResolutionCalc(GameObject player, GameObject entity) {
        float[] snap = new float[4];
        //0: Up, 1: Down, 2: Left, 3: Right
        snap[0] = (entity.rigidbody._position.y + entity.rigidbody._size.y * 0.5f) - (player.rigidbody._position.y + player.rigidbody._size.y * 0.5f);
        snap[1] = (entity.rigidbody._position.y - entity.rigidbody._size.y * 0.5f) - (player.rigidbody._position.y - player.rigidbody._size.y * 0.5f);
        snap[2] = (entity.rigidbody._position.x - entity.rigidbody._size.x * 0.5f) - (player.rigidbody._position.x - player.rigidbody._size.x * 0.5f);
        snap[3] = (entity.rigidbody._position.x + entity.rigidbody._size.x * 0.5f) - (player.rigidbody._position.x + player.rigidbody._size.x * 0.5f);

        int shortest = 0;
        for (int i = 1; i < 4; i++)
        {
            if (Math.abs(snap[i]) < Math.abs(snap[shortest]))
                shortest = i;
        }

        if (shortest == 2 || shortest == 3) {player.rigidbody._vel.x = 0f;}

        if (shortest <= 1)
            return new Vector2(0, snap[shortest]);
        else
            return new Vector2(snap[shortest], 0);
    }
}
