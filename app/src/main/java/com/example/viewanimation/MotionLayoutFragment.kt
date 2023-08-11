package com.example.viewanimation

import com.example.viewanimation.databinding.FragmentMotionLayoutBinding

class MotionLayoutFragment : DefaultFragment(R.layout.fragment_motion_layout) {
    companion object {

        fun newInstance() = MotionLayoutFragment()
    }

    private val binding: FragmentMotionLayoutBinding by viewBinding(
        FragmentMotionLayoutBinding::bind
    )
}