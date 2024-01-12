package com.saad.invitation.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.saad.invitation.R
import com.saad.invitation.databinding.FragmentEditorBinding
import com.saad.invitation.learning.UpdatedTouchListner
import com.saad.invitation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditorFragment : Fragment() {
    private lateinit var binding: FragmentEditorBinding
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var updateTouchListener: UpdatedTouchListner
    private lateinit var mContext: Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditorBinding.inflate(inflater, container, false)
        updateTouchListener = UpdatedTouchListner { }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receivedBundle = arguments
        val parentLayout = binding.parentLayout

        if (receivedBundle != null) {
            val documentId = receivedBundle.getString("document_id")
            val background = receivedBundle.getString("background")

            viewModel.fetchSingleDocumentDataFromFireStore(documentId.toString())

            Glide.with(this).asBitmap().load(background).centerInside()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        // Set the loaded bitmap as the background of the CardView
                        val drawable = BitmapDrawable(resources, resource)
                        parentLayout.background = drawable
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Implement if needed
                    }
                })
            viewModel.singleCardDesign.observe(requireActivity()) { dataList ->
                val data = dataList.arrayOfMaps
                for (map in data) {
                    val viewData = map["data"] as? String
                    val viewType = map["viewtype"] as? String
                    val fontSize = map["size"] as? String
                    val color = map["color"] as? String
                    val fontFamily = map["fontfamily"] as? String
                    val xCoordinate = map["X"] as? String
                    val yCoordinate = map["Y"] as? String
                    val width = map["width"] as? String
                    val height = map["height"] as? String
                    if (viewType == "text") {
                        xCoordinate?.toFloat()?.let { x ->
                            yCoordinate?.toFloat()?.let { y ->
                                fontSize?.toFloat()?.let { size ->
                                    viewData?.let { data ->
                                        color?.let { color ->
                                            addNewTextView(
                                                data,
                                                x,
                                                y,
                                                size,
                                                color
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else if (viewType == "image") {

                        viewData?.let {
                            xCoordinate?.let { it1 ->
                                yCoordinate?.let { it2 ->
                                    fontSize?.let { it3 ->
                                        width?.let { width ->
                                            height?.let { height ->
                                                addNewImageView(
                                                    it,
                                                    it1.toFloat(),
                                                    it2.toFloat(),
                                                    it3.toFloat(),
                                                    width = width.toInt(),
                                                    height = height.toInt()
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    private fun addNewImageView(
        imgUrl: String = "",
        x: Float = 200f,
        y: Float = 300f,
        imgSize: Float = 300f,
        width: Int = 300,
        height: Int = 300,
        parent: ViewGroup = binding.parentLayout,
    ) {

        val newImageView = ImageView(requireActivity())
        newImageView.id = View.generateViewId()

        Glide.with(this).load(imgUrl).centerInside().into(newImageView)

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParams.width = width
        layoutParams.height = height

        newImageView.x = x
        newImageView.y = y

        newImageView.layoutParams = layoutParams
        newImageView.scaleType = ImageView.ScaleType.MATRIX
        newImageView.setOnTouchListener(updateTouchListener)
        parent.addView(newImageView)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addNewTextView(
        text: String,
        x: Float = 100f,
        y: Float = 300f,
        textSize: Float = 26f,
        color: String = "#000000",
        parent: ViewGroup = binding.parentLayout,
    ) {
        val newTextView = TextView(requireActivity())
        newTextView.text = text
        newTextView.textSize = textSize
        newTextView.gravity = Gravity.CENTER
        newTextView.setTextColor(Color.BLACK)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        newTextView.x = x // X-coordinate
        newTextView.y = y // Y-coordinate
        newTextView.setTextColor(Color.parseColor(color))
        newTextView.layoutParams = layoutParams

        newTextView.setOnTouchListener(updateTouchListener)
        parent.addView(newTextView)
        newTextView.setPadding(20)

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }
}
