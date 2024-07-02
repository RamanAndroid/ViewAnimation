package com.example.viewanimation

import android.animation.Animator
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.example.viewanimation.databinding.FragmentLottieAnimationBinding

class LottieAnimationFragment : DefaultFragment(R.layout.fragment_lottie_animation) {
    companion object {

        fun newInstance() = LottieAnimationFragment()
    }

    private val binding: FragmentLottieAnimationBinding by viewBinding(
        FragmentLottieAnimationBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStartAnimationButton()
        initStopAnimationButton()
    }

    private fun initStopAnimationButton() {
        binding.stopAnimationButton.setOnClickListener {
            binding.lottieAnimationView.pauseAnimation()
        }
    }

    private fun initStartAnimationButton() {
        binding.startAnimationButton.setOnClickListener {
            binding.lottieAnimationView.repeatCount = 3
            binding.lottieAnimationView.setAnimation("lottie_download.json")
            binding.lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    val randomNumber = (0..100).random()
                    binding.lottieAnimationView.repeatCount = LottieDrawable.INFINITE

                    if (randomNumber >= 50) {
                        binding.lottieAnimationView.setAnimation("lottie_success.json")
                    } else {
                        binding.lottieAnimationView.setAnimation("lottie_error.json")
                    }
                    binding.lottieAnimationView.playAnimation()
                }
            })
            binding.lottieAnimationView.playAnimation()
        }
    }
}