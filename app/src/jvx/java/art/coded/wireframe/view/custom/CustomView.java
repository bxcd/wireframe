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

import art.coded.wireframe.R;

/**
 * TODO: document your custom view class.
 */
public class CustomView extends View {

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

    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private Paint mPaint;
    private float mTextWidth;
    private float mTextHeight;

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

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.CustomView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.CustomView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.CustomView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.CustomView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.CustomView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        setClickable(true);

        // Set up a default Paint object
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(55.0f);
        mPaint.setTypeface(Typeface.create("", Typeface.BOLD));

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    @Override public boolean performClick() {
        if (super.performClick()) return true;

        mLevel = mLevel.next();
        setContentDescription(getResources().getString(mLevel.label()));

        invalidate();
        return true;
    }

    private void invalidateTextPaintAndMeasurements() {
        mPaint.setTextSize(mExampleDimension);
        mPaint.setColor(mExampleColor);
        mTextWidth = mPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }

        int color = (mLevel == Level.OFF) ? Color.GRAY : Color.GREEN;
        mPaint.setColor(color);


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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRadius = Math.min(w, h) / 2.0f * 0.8f;
    }

    private void computeXYForLevel(Level pos, Float radius) {
        // Angles are in radians.
        final double startAngle = Math.PI * (9 / 8.0);
        final double angle = startAngle + pos.ordinal() * (Math.PI / 4);
        mPointPosition.x = ((float) (radius * Math.cos(angle))) + getWidth() / 2f;
        mPointPosition.y = ((float) (radius * Math.sin(angle))) + getHeight() / 2f;
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view"s example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view"s example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view"s example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view"s example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}