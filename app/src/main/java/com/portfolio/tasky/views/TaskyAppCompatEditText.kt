package com.portfolio.tasky.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatEditText
import com.portfolio.tasky.R
import com.portfolio.tasky.databinding.LayoutInputFieldBinding

class TaskyAppCompatEditText(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs)   {

    private lateinit var viewBinding: LayoutInputFieldBinding
    private var error : Boolean = false
    private var drawableLast : Drawable? = null
    private lateinit var editText : AppCompatEditText
    private var inputType : Int = 0
    private var hint : String? = null

    init {
        setupView(attrs)
        isError()
        setDrawableLast()
        setInputType()
        setHint()
    }

    private fun setHint() {
        viewBinding.hint = hint
    }

    private fun setInputType() {
        viewBinding.etInput.inputType = inputType
    }

    private fun setDrawableLast() {
        viewBinding.drawableLast = drawableLast
    }

    fun isError(){
        viewBinding.error = error
    }

    @SuppressLint("CustomViewStyleable")
    private fun setupView(attrs: AttributeSet) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        viewBinding = LayoutInputFieldBinding.inflate(inflater, this, false)
        addView(viewBinding.root)

        editText = viewBinding.etInput
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextInputField)
        error = typedArray.getBoolean(R.styleable.EditTextInputField_error, false)
        drawableLast = typedArray.getDrawable(R.styleable.EditTextInputField_drawableLast)
        inputType = typedArray.getInt(R.styleable.EditTextInputField_inputType, InputType.TYPE_CLASS_TEXT)
        hint = typedArray.getString(R.styleable.EditTextInputField_hint)
        typedArray.recycle()
    }
}