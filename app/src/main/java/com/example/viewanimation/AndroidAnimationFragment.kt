package com.example.viewanimation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import androidx.core.animation.addListener
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.viewanimation.databinding.FragmentAndroidAnimationBinding

class AndroidAnimationFragment : DefaultFragment(R.layout.fragment_android_animation) {

    companion object {

        fun newInstance() = AndroidAnimationFragment()
    }

    private val binding: FragmentAndroidAnimationBinding by viewBinding(
        FragmentAndroidAnimationBinding::bind
    )

    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.drawableAnimation.setOnClickListener {
            showAnimation()
            launchDrawableAnimation()
        }
        binding.propertyAnimation.setOnClickListener {
            showAnimation()
            launchPropertyAnimation()
        }
        binding.viewAnimation.setOnClickListener {
            showAnimation()
            launchViewAnimation()
        }
        binding.animatedVectorDrawableAnimation.setOnClickListener {
            showAnimation()
            launchAnimatedVectorDrawable()
        }
    }

    private fun launchDrawableAnimation() {
        binding.animation.setBackgroundResource(R.drawable.drawable_animation)
        val animationDrawable = binding.animation.background as AnimationDrawable
        var durationAnimation = 100L
        animationDrawable.start()

        for (i in 0..animationDrawable.numberOfFrames) {
            durationAnimation += animationDrawable.getDuration(i)
        }

        handler.postDelayed({
            animationDrawable.stop()
            hideAnimation()
        }, durationAnimation)
    }

    private fun launchPropertyAnimation() {
        binding.animation.setBackgroundResource(R.drawable.animation_property_rocket)
        val animationScaleX =
            ObjectAnimator.ofFloat(binding.animation, "scaleX", 1F, 0.3F, 0.6F, 2F)
        val animationScaleY =
            ObjectAnimator.ofFloat(binding.animation, "scaleY", 1F, 0.3F, 0.6F, 2F)
        val fadeAnim =
            ObjectAnimator.ofFloat(binding.animation, "alpha", 1f, 0f, 1f).apply { duration = 250 }

        val scaleAnimation = AnimatorSet().apply { play(animationScaleX).with(animationScaleY) }

        AnimatorSet().apply {
            play(scaleAnimation).before(fadeAnim)
            duration = 900L
            interpolator = DecelerateInterpolator()
            addListener(
                onEnd = {
                    hideAnimation()
                }
            )
            start()
        }


        /**
         * Анимация через xml конфигурацию анимации
         * Также добавил анимацию к button с помощью xml через stateListAnimator
         *
         */
//        binding.animation.setBackgroundResource(R.drawable.animation_property_rocket)
//        val scaleAnim: AnimatorSet = AnimatorInflater.loadAnimator(
//            requireContext(),
//            R.animator.property_animation
//        ) as AnimatorSet
//        scaleAnim.apply {
//            setTarget(binding.animation)
//            addListener(
//                onEnd = {
//                    hideAnimation()
//                }
//            )
//            start()
//        }
    }

    private fun launchViewAnimation() {
        binding.animation.setBackgroundResource(R.drawable.animation_property_rocket)
        val scaleAnimation = ScaleAnimation(
            0F, 1F,
            0F, 1F,
            0F, binding.animation.measuredHeight.toFloat()
        )
        scaleAnimation.duration = 250L
        scaleAnimation.interpolator = DecelerateInterpolator()
        scaleAnimation.fillAfter = true
        scaleAnimation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                hideAnimation()
            }
        })
        binding.animation.startAnimation(scaleAnimation)
    }

    private fun launchAnimatedVectorDrawable() {
        val drawableAnimation = AnimatedVectorDrawableCompat.create(
            requireContext(),
            R.drawable.animated_vector_drawable
        )
        binding.animation.setImageDrawable(drawableAnimation)
        drawableAnimation?.start()
        drawableAnimation?.registerAnimationCallback(object :
            Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                hideAnimation()
                drawableAnimation.stop()
            }
        })
    }

    private fun showAnimation() {
        binding.animation.visibility = View.VISIBLE
        binding.buttons.visibility = View.GONE
    }

    private fun hideAnimation() {
        binding.animation.background = null
        binding.animation.setImageDrawable(null)
        binding.animation.visibility = View.GONE
        binding.buttons.visibility = View.VISIBLE
    }
}