package org.sjhstudio.flow.bookproject.presentation.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import org.sjhstudio.flow.bookproject.databinding.DialogMessageBinding

class MessageDialog(
    private val message: String,
    private val firstButtonText: String = "",
    private val secondButtonText: String = "",
    private val clickedFirstButton: () -> Unit = {},
    private val clickedSecondButton: () -> Unit = {},
    private val isConfirmDialog: Boolean = false
) : DialogFragment() {

    private var _binding: DialogMessageBinding? = null
    val binding: DialogMessageBinding
        get() = _binding ?: error("ViewBinding error")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMessageBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            tvMessage.text = message

            btnCancel.apply {
                isVisible = !isConfirmDialog
                text = firstButtonText

                setOnClickListener {
                    clickedFirstButton.invoke()
                    dismiss()
                }
            }

            btnOk.apply {
                text = secondButtonText

                setOnClickListener {
                    clickedSecondButton.invoke()
                    dismiss()
                }
            }
        }
    }
}