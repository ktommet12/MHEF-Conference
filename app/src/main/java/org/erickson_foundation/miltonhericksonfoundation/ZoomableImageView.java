package org.erickson_foundation.miltonhericksonfoundation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by User on 4/25/2017.
 */

public class ZoomableImageView extends ImageView {
    private Matrix matrix;

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    static final int CLICK = 3;
    int mode = NONE;

    private PointF mLastTouch = new PointF();
    private PointF mStartTouch = new PointF();
    private PointF mid = new PointF();

    private float maxScale = 1f;
    private float minScale = 3f;

    float[] m;
    float redundantXSpace, redundantYSpace, originalRedundantXSpace, originalRedundantYSpace;

    int viewWidth, viewHeight;

    static final float SAVE_SCALE = 1f;
    float saveScale = SAVE_SCALE;

    protected float originalWidth, originalHeight;
    int oldMeasuredWidth, oldMeasuredHeight;
    float originalScale, bottom, originalBottom, right, originalRight;

    ScaleGestureDetector mScaleDetector;
    GestureDetector mGestureDetector;

    private Context mCTX;

    public ZoomableImageView(Context context){
        super(context);
        initImageView(context);
    }
    public ZoomableImageView(Context ctx, AttributeSet attr){
        super(ctx, attr);
        initImageView(ctx);
    }
    private void initImageView(Context context){
        super.setClickable(true);
        this.mCTX = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        matrix = new Matrix();
        m = new float[9];
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mode = ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactor = detector.getScaleFactor();
            float origScale = saveScale;
            saveScale *= mScaleFactor;
            if(saveScale > maxScale){
                saveScale = maxScale;
                mScaleFactor = maxScale / originalScale;
            }else if(saveScale < minScale){
                saveScale = minScale;
                mScaleFactor = minScale / originalScale;
            }

            right = viewWidth * saveScale - viewWidth - (2 * redundantXSpace * saveScale);
            bottom = viewHeight * saveScale - viewHeight - (2 * redundantYSpace * saveScale);

            if(originalWidth * saveScale <= viewWidth || originalHeight * saveScale <= viewHeight){
                matrix.postScale(mScaleFactor, mScaleFactor, viewWidth / 2, viewHeight / 2);
            } else{
                matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
            }
            fixTrans();
            return true;
        }
    }
    private void fixTrans(){
        matrix.getValues(m);
        float transX = m[Matrix.MTRANS_X];
        float transY = m[Matrix.MTRANS_Y];

        float fixTransX = getFixTrans(transX, viewWidth, originalWidth * saveScale);
        float fixTransY = getFixTrans(transY, viewHeight, originalHeight * saveScale);

        if(fixTransX != 0 || fixTransY != 0){
            matrix.postTranslate(fixTransX, fixTransY);
        }
    }
    private float getFixTrans(float trans, float viewSize, float contentSize){
        float minTrans, maxTrans;

        if(contentSize <= viewSize){
            minTrans = 0;
            maxTrans = viewSize - contentSize;
        }else{
            minTrans = viewSize - contentSize;
            maxTrans = 0;
        }

        if(trans  < minTrans){
            return -trans + minTrans;
        }if(trans > maxTrans){
            return -trans + maxTrans;
        }
        return 0;
    }
    private float getFixDragTrans(float delta, float viewSize, float contentSize){
        if(contentSize <= viewSize){
            return 0;
        }
        return delta;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int bmWidth = getWidth();
        int bmHeight = getHeight();

        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        float scale = 1;

        if(width < bmWidth || height < bmHeight){
            scale = width > height ? height / bmHeight : width / bmWidth;
        }

        matrix.setScale(scale, scale);
        scale = 1f;

        originalHeight = scale * bmHeight;
        originalWidth = scale * bmWidth;

        redundantYSpace = (height - originalHeight);
        redundantXSpace = (width - originalWidth);

        matrix.postTranslate(redundantXSpace/2, redundantYSpace/2);

        setImageMatrix(matrix);
        //viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        //viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        /*if(oldMeasuredHeight == viewHeight && oldMeasuredWidth == viewWidth || viewWidth == 0 || viewHeight == 0) return;

        oldMeasuredHeight = viewHeight;
        oldMeasuredWidth = viewWidth;


        int w = bmWidth;
        int h = bmHeight;

        viewHeight = resolveSize(w, widthMeasureSpec);
        viewWidth = resolveSize(h, heightMeasureSpec);

        float scaleX = (float) viewWidth / (float) bmWidth;
        float scaleY = (float) viewHeight / (float) bmHeight;

        scale = Math.min(scaleX, scaleY);

        matrix.setScale(scale, scale);

        saveScale = SAVE_SCALE;
        originalScale = scale;

        redundantYSpace = (float) viewHeight - (scale * (float) bmHeight);
        redundantXSpace = (float) viewWidth - (scale * (float) bmWidth);
        redundantYSpace /= (float) 2;

        originalRedundantXSpace = redundantXSpace;
        originalRedundantYSpace = redundantYSpace;

        matrix.postTranslate(redundantXSpace, redundantYSpace);

        originalWidth = viewWidth - 2 * redundantXSpace;
        originalHeight = viewHeight - 2 * redundantYSpace;

        right = viewWidth * saveScale - viewWidth - (2 * redundantXSpace * saveScale);
        bottom = viewHeight * saveScale - viewHeight - (2 * redundantYSpace * saveScale);

        originalBottom = bottom;
        originalRight = right;

        setImageMatrix(matrix);*/
        fixTrans();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        matrix.getValues(m);
        float translateY = m[Matrix.MTRANS_Y];
        float translateX = m[Matrix.MTRANS_X];
        PointF currentPoint = new PointF(event.getX(), event.getY());


        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastTouch.set(event.getX(), event.getY());
                mStartTouch.set(mLastTouch);
                mode = DRAG;
                break;
        }
        return true;
    }

    OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return true;
        }
    };
}
