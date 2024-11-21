package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Map;

public class AnimatedSprite {
    public static class animation {
        public int id, start, end;
        public animation(int from, int to) {
            id = 0;
            start = from;
            end = to;
        }
    }

    private final int _col, _width, _height;
    private int _currentFrame = 0, _totalAnim = 0;
    private animation _currentAnim;
    private final Map<String, animation> _animationMap;
    private final float _timePerFrame;
    private float _timeAccumulated = 0f;
    private final Bitmap _bmp;
    private boolean _isLooping = true;
    private final Rect _src, _dst;

    public AnimatedSprite(Bitmap bitmap, int row, int col, int fps) {
        _bmp = bitmap;
        _col = col;
        _width = _bmp.getWidth() / _col;
        _height = _bmp.getHeight() / row;
        _animationMap = new HashMap<>();
        _timePerFrame = 1f / fps;
        _src = new Rect();
        _dst = new Rect();

        AddAnimation("Default", 0, _col * row);
        _currentAnim = _animationMap.get("Default");
    }

//    public AnimatedSprite(Bitmap bitmap, int row, int col, int fps, int startFrame, int endFrame) {
//        this(bitmap, row, col, fps);
//    }


    public void AddAnimation(String name, int frameStart, int frameEnd) {
        animation temp = new animation(frameStart, frameEnd);
        _totalAnim++;
        temp.id = _totalAnim;
        _animationMap.put(name, temp);
    }

    public void PlayAnimation(String name) {
        animation incoming = _animationMap.get(name);

        if (incoming != null) {
            if (_currentAnim.id == incoming.id) return;

            _currentAnim = incoming;
            _currentFrame = _currentAnim.start;
        }
        else System.out.println(name + " animation does not exist!");
    }

    public void setLooping(boolean shouldLoop) { _isLooping = shouldLoop; }

    public boolean hasFinished() {
        if (_isLooping) return false;
        return _currentFrame == _currentAnim.end;
    }

    public void update(float dt) {
        if (hasFinished()) return;
        _timeAccumulated += dt;
        if (_timeAccumulated > _timePerFrame) {
            _currentFrame++;
            if (_currentFrame > _currentAnim.end && _isLooping)
                _currentFrame = _currentAnim.start;
            _timeAccumulated = 0f;
        }
    }

    public void onRender(Canvas canvas, int x, int y, Paint paint) {
//        float xElement = (float) ((_currentFrame - 1) / 4) + 1;
//        float yElement = (float) ((_currentFrame - 1) % 4) + 1;

        int frameX = _currentFrame % _col;
        int frameY = _currentFrame / _col;
        int srcX = frameX * _width;
        int srcY = frameY & _height;

        x -= (int) (0.5f * _width);
        y -= (int) (0.5f * _height);

        _src.left = srcX;
        _src.top = srcY;
        _src.right = srcX + _width;
        _src.bottom = srcY + _height;

        _dst.left = x;
        _dst.top = y;
        _dst.right = x + _width;
        _dst.bottom = y + _height;

        canvas.drawBitmap(_bmp, _src, _dst, paint);
    }
}
