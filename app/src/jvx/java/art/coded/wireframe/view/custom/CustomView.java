package art.coded.wireframe.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import art.coded.wireframe.R;

/**
 * 
 */
public class CustomView extends View {

    private static final String LOG_TAG = CustomView.class.getSimpleName();

    private enum Level {
        OFF { @Override public final int label() { return R.string.level_off; } },
        LOW { @Override public final int label() { return R.string.level_low; } },
        MEDIUM { @Override public final int label() { return R.string.level_medium; } },
        HIGH { @Override public final int label() { return R.string.level_high; } };
        abstract public int label();
        public Level next() {
            switch(label()) {
                case R.string.level_off: return LOW;
                case R.string.level_low: return MEDIUM;
                case R.string.level_medium: return HIGH;
                default: return OFF;
            }
        }
    }

    private final int RADIUS_OFFSET_LABEL = 30;
    private final int RADIUS_OFFSET_INDICATOR = -35;

    private float mRadius = 0.0f;
    private final PointF mPointPosition = new PointF(0.0f, 0.0f);
    private Level mLevel = Level.OFF;

    private int levelLowColor = 0;
    private int levelMediumColor = 0;
    private int levelHighColor = 0;

    private Paint mPaint;

    public CustomView(Context context) {
        super(context);
        init(null, 0);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void updateContentDescription() {
        setContentDescription(getResources().getString(mLevel.label()));
    }

    private void init(AttributeSet attrs, int defStyle) {
        setClickable(true);

        // Set up a default Paint object
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(55.0f);
        mPaint.setTypeface(Typeface.create("", Typeface.BOLD));

        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() {
            @Override public void onInitializeAccessibilityNodeInfo(
                    View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                AccessibilityNodeInfoCompat.AccessibilityActionCompat customClick =
                        new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            AccessibilityNodeInfoCompat.ACTION_CLICK,
                            getContext().getString((mLevel !=  Level.HIGH) ? R.string.change : R.string.reset)
                        );
                info.addAction(customClick);
            }
        });

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
        levelLowColor = typedArray.getColor(R.styleable.CustomView_levelColor1, 0);
        levelMediumColor =  typedArray.getColor(R.styleable.CustomView_levelColor2, 0);
        levelHighColor =  typedArray.getColor(R.styleable.CustomView_levelColor3, 0);

        typedArray.recycle();

        updateContentDescription();
    }

    @Override public boolean performClick() {
        mLevel = mLevel.next();
        updateContentDescription();

        invalidate();
        super.performClick();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        switch(mLevel) {
            case OFF: mPaint.setColor(Color.GRAY);break;
            case LOW: mPaint.setColor(levelLowColor); break;
            case MEDIUM: mPaint.setColor(levelMediumColor); break;
            default: mPaint.setColor(levelHighColor);
        }

        canvas.drawCircle(getWidth()/ 2f, getHeight()/ 2f, mRadius, mPaint);

        final float markerRadius = mRadius + RADIUS_OFFSET_INDICATOR;
        computeXYForLevel(mLevel, markerRadius);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(mPointPosition.x, mPointPosition.y, mRadius/12, mPaint);

        final float labelRadius = mRadius + RADIUS_OFFSET_LABEL;
        for (Level i : Level.values()) {
            computeXYForLevel(i, labelRadius);
            final String label = getResources().getString(i.label());
            canvas.drawText(label, mPointPosition.x, mPointPosition.y, mPaint);
        }
    }

    @Override
    public void setAccessibilityDelegate(@Nullable AccessibilityDelegate delegate) {
        super.setAccessibilityDelegate(delegate);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRadius = Math.min(w, h) / 2.0f * 0.8f;
    }

    private void computeXYForLevel(Level pos, Float radius) {
        // Angles are in radians.
        final double startAngle = Math.PI * (9 / 8.0);
        final double angle = startAngle + pos.ordinal() * (Math.PI / 4);
        mPointPosition.x = ((float) (radius * Math.cos(angle))) + getWidth() / 2f;
        mPointPosition.y = ((float) (radius * Math.sin(angle))) + getHeight() / 2f;
    }

    public int getLevelLabelRes() { return mLevel.label(); }
}