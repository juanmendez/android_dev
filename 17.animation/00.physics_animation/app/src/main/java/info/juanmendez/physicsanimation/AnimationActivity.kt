package info.juanmendez.physicsanimation

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.view.MotionEventCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {
    var animation: SpringAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        android_icon.setOnTouchListener { view, ev ->
            val action = MotionEventCompat.getActionMasked(ev)
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    //stop spring animation
                    animation?.skipToEnd()
                }
                MotionEvent.ACTION_MOVE -> {
                    view.x = ev.rawX - view.width / 2
                    view.y = ev.rawY - view.height
                    view.invalidate()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    //when stop dragging, then do spring animation
                    resetVerticalPosition(view)
                }
            }
            true
        }
    }

    private fun resetVerticalPosition(view: View) {
        animation = SpringAnimation(view,
                DynamicAnimation.TRANSLATION_Y, 0f).apply {
            spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            spring.stiffness = SpringForce.STIFFNESS_LOW
            start()
        }

    }
}
