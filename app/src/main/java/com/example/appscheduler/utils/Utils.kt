package com.example.appscheduler.utils

import android.app.Activity
import android.content.Intent
import android.text.InputType
import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker

//Generic Function to open a new activity
fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

//Function for Show and Hide Items
fun View.visibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

//Function For Enable or Disable Items
fun View.enable(enable: Boolean) {
    isEnabled = enable
    alpha = if (enable) 1f else 0.5f
}

//Material date picker extension function for textInputLayout
fun TextInputEditText.datePickerMaterialAndGetValue(
    fragmentManager: FragmentManager,
    myCalling: ((input: String) -> Unit)?
) {
    showSoftInputOnFocus = false
    inputType = InputType.TYPE_NULL
    isFocusable = false

    val materialTimeDatePickerBuilder = MaterialTimePicker.Builder()
    materialTimeDatePickerBuilder.setTitleText("Select a Time")
    materialTimeDatePickerBuilder.setHour(MaterialTimePicker.INPUT_MODE_CLOCK)
    materialTimeDatePickerBuilder.setMinute(MaterialTimePicker.INPUT_MODE_CLOCK)
    val materialTimeDatePicker = materialTimeDatePickerBuilder.build()

    setOnClickListener {
        materialTimeDatePicker.show(fragmentManager, "TIME_PICKER")
        materialTimeDatePicker.addOnPositiveButtonClickListener {
            setText(materialTimeDatePicker.hour)
            setText(materialTimeDatePicker.minute)
            val dateTime = "${materialTimeDatePicker.hour}, ${materialTimeDatePicker.minute}"

            if (myCalling != null) myCalling(dateTime)
        }
    }
}
