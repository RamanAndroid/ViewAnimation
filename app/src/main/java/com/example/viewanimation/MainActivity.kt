package com.example.viewanimation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFragment(LottieAnimationFragment.newInstance())
    }

    private fun openFragment(fragment: DefaultFragment) {
        this.supportFragmentManager.commit {
            replace(
                R.id.fragment_container_main_activity,
                fragment,
                fragment::class.java.simpleName
            )
            addToBackStack(fragment::class.java.simpleName)
            setPrimaryNavigationFragment(fragment)
        }
    }
}