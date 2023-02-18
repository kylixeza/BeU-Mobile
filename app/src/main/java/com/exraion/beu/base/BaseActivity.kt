package com.exraion.beu.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.validator.ConstraintValidator

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    private lateinit var _binding: VB
    val binding get() = _binding

    abstract fun inflateViewBinding(): VB
    abstract fun determineScreenOrientation(): ScreenOrientation?
    abstract fun VB.binder()

    open fun constraintValidator(): ConstraintValidator<VB>? { return null }
    open fun onBackPressedBehaviour() { finish() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateViewBinding()
        setContentView(binding.root)

        val screenOrientation = determineScreenOrientation()

        requestedOrientation = if(screenOrientation != null) {
            if (screenOrientation == ScreenOrientation.PORTRAIT)
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            android.content.pm.ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        }

        binding.apply {
            binder()
            constraintValidator()?.apply { validate() }
        }
    
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedBehaviour()
            }
        }
    
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun View.show() = run { visibility = View.VISIBLE }
    fun View.hide() = run { visibility = View.INVISIBLE }
    fun View.dispose() = run { visibility = View.GONE }
    
    fun doNothing() = Unit
    
}