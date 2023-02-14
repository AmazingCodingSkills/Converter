package com.currency.converter.base

/*
class CustomDialog: DialogFragment() {
    var listener: Listener? = null
    var messageText = ""
    private lateinit var binding: DialogCustomBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCustomBinding.inflate(inflater, container, false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = context?.let { AlertDialog.Builder(it) }
        val rootView = activity?.layoutInflater?.inflate(R.layout.dialog_custom, null)
        isCancelable = false
        val messageTextView: TextView? = rootView?.findViewById(R.id.messageTextView)
        val okButton: Button? = rootView?.findViewById(R.id.okButton)

        if (messageText.isNotBlank()) {
            messageTextView?.text = messageText
        }

        okButton?.setOnClickListener {
            listener?.customOkClicked()
            dismiss()
        }

        builder.setView(rootView)
        return builder.create()
    }



    interface Listener {
        fun customOkClicked()
    }
}
}*/
