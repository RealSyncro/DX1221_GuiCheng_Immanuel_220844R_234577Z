package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core;

import android.graphics.Canvas;

import java.util.concurrent.ConcurrentHashMap;

public abstract class GameScene {

    // region Static props for managing multiple scenes

    private static GameScene _current = null;

    private static GameScene _next = null;

    public static GameScene getCurrent() { return _current; }

    public static GameScene getNext() { return _next; }

    private static final ConcurrentHashMap<Class<?>, GameScene> map = new ConcurrentHashMap<>();

    public static void enter(Class<?> gameSceneClass) {
        if (!map.containsKey(gameSceneClass)) {
            try {
                map.put(gameSceneClass, (GameScene) gameSceneClass.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        _next = map.get(gameSceneClass);
    }

    public static void enter(GameScene gameScene) {
        if (_current != null) _current.onExit();
        _current = gameScene;
        if (_current == null) return;
        _current.onEnter();
    }

    public static void exitCurrent() {
        if (_current == null) return;
        _current.onExit();
        _current = null;
    }

    // Only call this when you want to reset everything to 0.
    public static void exitAll() {
        if (_current == null) return;
        _current.onExit();
        _current = null;

        if (!map.isEmpty()) {
            map.clear();
        }
    }

    // endregion

    // region Props for handling internal behaviour of game scene

    private boolean _isCreated = false;
    public void onCreate() { _isCreated = true; }
    public void onEnter() { if (!_isCreated) onCreate(); }
    public abstract void onUpdate(float dt);
    public abstract void onRender(Canvas canvas);
    public void onExit() {}

    //endregion
}
