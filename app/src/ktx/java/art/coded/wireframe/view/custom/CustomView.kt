package art.coded.wireframe.view.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import art.coded.wireframe.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

enum class Level(val label : Int) {
    OFF(R.string.level_off),
    LOW(R.string.level_low),
    MEDIUM(R.string.level_medium),
    HIGH(R.string.level_high);

    fun next() = when (this) {
        OFF -> LOW
        LOW -> MEDIUM
        MEDIUM -> HIGH
        HIGH -> OFF
    }
}

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35

/**
 * TODO: document your custom view class.
 */
class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var radius = 0.0f
    private var level = Level.OFF
    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    private var levelLowColor = 0
    private var levelMediumColor = 0
    private var levelHighColor = 0


    init {
        isClickable = true

        ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                    host: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                val customClick = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    AccessibilityNodeInfoCompat.ACTION_CLICK,
                    context.getString(if (level !=  Level.HIGH) R.string.change else R.string.reset)
                )
                info.addAction(customClick)
            }
        })

        context.withStyledAttributes(attrs, R.styleable.CustomView) {
            levelLowColor = getColor(R.styleable.CustomView_levelColor1, 0)
            levelMediumColor = getColor(R.styleable.CustomView_levelColor2, 0)
            levelHighColor = getColor(R.styleable.CustomView_levelColor3, 0)
        }
    }

    override fun performClick(): Boolean {
        level = level.next()
        contentDescription = resources.getString(level.label)

        invalidate()
        super.performClick()
        return true
    }

    override fun onDraw(canvas: Canvas) {

        paint.color = when (level) {
            Level.OFF -> Color.GRAY
            Level.LOW -> levelLowColor
            Level.MEDIUM -> levelMediumColor
            Level.HIGH -> levelHighColor
        }

        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForLevel(level, markerRadius)
        paint.color = Color.BLACK
        canvas.drawCircle(pointPosition.x, pointPosition.y, radius/12, paint)

        val labelRadius = radius + RADIUS_OFFSET_LABEL
        for (i in Level.values()) {
            pointPosition.computeXYForLevel(i, labelRadius)
            val label = resources.getString(i.label)
            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (min(w, h) / 2.0 * 0.8).toFloat()
    }

    private fun PointF.computeXYForLevel(pos: Level, radius: Float) {
        // Angles are in radians.
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    val levelLabelRes: Int
        get() = level.label
}
