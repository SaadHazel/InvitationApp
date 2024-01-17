package com.saad.invitation.views

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.saad.invitation.R
import com.saad.invitation.adapters.LayersAdapter
import com.saad.invitation.databinding.FragmentEditorBinding
import com.saad.invitation.eventsrecycler.ItemTouchHelperAdapter
import com.saad.invitation.eventsrecycler.ItemTouchHelperCallback
import com.saad.invitation.eventsrecycler.UpdateTouchListenerCallback
import com.saad.invitation.learning.UpdatedTouchListner
import com.saad.invitation.models.LayersModel
import com.saad.invitation.utils.log
import com.saad.invitation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class EditorFragment : Fragment(), ItemTouchHelperAdapter, UpdateTouchListenerCallback {
    private lateinit var binding: FragmentEditorBinding
    private val viewModel by activityViewModel<MainViewModel>()
    private lateinit var updateTouchListener: UpdatedTouchListner
    private var receivedBundle: Bundle? = null
    private val layerList = mutableListOf<LayersModel>()
    private lateinit var itemTouchHelper: ItemTouchHelper
    var isRecyclerViewVisible = false
    private var boolTopBar = false
//    private val dynamicViewsList = mutableListOf<View>()

    private val adapter: LayersAdapter by lazy {
        LayersAdapter(layerList, this, updateTouchListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setData()
        backToHome()
        undoRedoListener()
//        observeViewState()
    }

    private fun layers(view: View, text: String, priority: Int) {
        layerList.add(
            LayersModel(view, text, lock = false, hide = false, priority = priority)
        )
        layerList.sortBy { it.priority }

        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewLayers.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewLayers.adapter = adapter

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewLayers)
    }

    private fun setupButtonLayers() = binding.apply {

        btnLayers.setOnClickListener {
            isRecyclerViewVisible = !isRecyclerViewVisible
            recyclerViewLayers.visibility = if (isRecyclerViewVisible) {
                View.VISIBLE
            } else {
                View.GONE
            }
            btnLayers.setImageResource(if (isRecyclerViewVisible) R.drawable.baseline_layers_24_filled else R.drawable.baseline_layers_24)
        }
    }


    private fun backToHome() = binding.apply {
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_editorFragment_to_homeFragment)
        }
    }

    private fun setData() {
        viewModel.singleCardDesign.observe(viewLifecycleOwner) { dataList ->
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
                val priority = map["priority"] as? String
                if (viewType == "text") {
                    xCoordinate?.toFloat()?.let { x ->
                        yCoordinate?.toFloat()?.let { y ->
                            fontSize?.toFloat()?.let { size ->
                                viewData?.let { data ->
                                    color?.let { color ->
                                        priority?.let { priority ->
                                            addNewTextView(
                                                data,
                                                x,
                                                y,
                                                size,
                                                color,
                                                priority = priority.toInt()
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }

                } else if (viewType == "image") {

                    viewData?.let { imgUrl ->
                        xCoordinate?.let { x ->
                            yCoordinate?.let { y ->
                                fontSize?.let { fontSize ->
                                    width?.let { width ->
                                        height?.let { height ->
                                            priority?.let { priority ->
                                                addNewImageView(
                                                    imgUrl,
                                                    x.toFloat(),
                                                    y.toFloat(),
                                                    fontSize.toFloat(),
                                                    width = width.toInt(),
                                                    height = height.toInt(),
                                                    priority = priority.toInt()
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

    private fun fetchData(documentId: String, background: String) {
        viewModel.fetchSingleDocumentDataFromFireStore(documentId)

        Glide.with(this).asBitmap().load(background).centerInside()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?,
                ) {
                    // Set the loaded bitmap as the background of the View
                    val drawable = BitmapDrawable(resources, resource)
                    binding.editorLayout.background = drawable
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun init() {
        updateTouchListener = UpdatedTouchListner(this) { booleanCallback ->
            boolTopBar = booleanCallback
            toggleUndoRedo(booleanCallback)
        }
        receivedBundle = arguments
        if (receivedBundle != null) {
            val documentId = receivedBundle!!.getString("document_id")
            val background = receivedBundle!!.getString("background")
            documentId?.let { docId -> background?.let { bg -> fetchData(docId, bg) } }
        }
        setupRecyclerView()
        setupButtonLayers()
    }

    private fun toggleUndoRedo(toggle: Boolean) = binding.apply {
        if (toggle) {
            isRecyclerViewVisible = false
            btnUndo.visibility = View.GONE
            btnRefresh.visibility = View.GONE
            btnRedo.visibility = View.GONE
            btnDone.visibility = View.VISIBLE
            recyclerViewLayers.visibility = View.GONE
            btnBack.visibility = View.GONE
        }

        btnDone.setOnClickListener {
            isRecyclerViewVisible = false
            btnUndo.visibility = View.VISIBLE
            btnRefresh.visibility = View.VISIBLE
            btnRedo.visibility = View.VISIBLE
            btnBack.visibility = View.VISIBLE
            recyclerViewLayers.visibility = View.GONE
            btnDone.visibility = View.GONE
        }
    }

    private fun undoRedoListener() = binding.apply {
        /*      btnUndo.setOnClickListener {
                  viewModel.undoChanges()
              }
              btnRedo.setOnClickListener {
                  viewModel.redoChanges()
              }*/
        btnUndo.setOnClickListener {
            val viewToUndo = viewModel.popLastChangeOrder()
            viewToUndo?.let {
                viewModel.undoToInitialPosition(it)
            }
        }
        btnRedo.setOnClickListener {
            val viewToRedo = viewModel.popLastRedo()
            viewToRedo?.let {
                viewModel.redoToInitialPosition(it)
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
        parent: ViewGroup = binding.editorLayout,
        priority: Int,
    ) {
        log("NewImageAdded")
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
        newImageView.z = priority.toFloat()
        newImageView.layoutParams = layoutParams
        newImageView.scaleType = ImageView.ScaleType.MATRIX
        newImageView.setOnTouchListener(updateTouchListener)
        parent.addView(newImageView)
//        dynamicViewsList.add(newImageView)
        viewModel.storeInitialPosition(newImageView)
        viewModel.addDynamicView(newImageView)
        layers(newImageView, "Image", priority)

    }

    // Function to update the z-order of shapes based on priorities
    /*    private fun updateShapePriorities() {
            for ((index, shape) in viewListWithPriority.withIndex()) {
                // Bring the shape to the front based on its position in the list
                shape.bringToFront()
            }
        }*/

    @SuppressLint("ClickableViewAccessibility")
    private fun addNewTextView(
        text: String,
        x: Float = 100f,
        y: Float = 300f,
        textSize: Float = 26f,
        color: String = "#000000",
        parent: ViewGroup = binding.editorLayout,
        priority: Int,
    ) {
        log("NewTextAdded")

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
        newTextView.z = priority.toFloat()
        newTextView.setTextColor(Color.parseColor(color))
        newTextView.layoutParams = layoutParams

        newTextView.setOnTouchListener(updateTouchListener)
        parent.addView(newTextView)
        newTextView.setPadding(20)
//        viewListWithPriority.add(priority, newTextView)
//        updateShapePriorities()
//        dynamicViewsList.add(newTextView)
        viewModel.storeInitialPosition(newTextView)
        viewModel.addDynamicView(newTextView)
        layers(newTextView, text, priority)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        TODO("Not yet implemented")
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun updateZOrder() {
        adapter.updateZOrder()
    }

    override fun onDrag(view: View) {
        /*      // Capture the state before changes (e.g., when dragging starts)
              viewModel.captureStateBeforeChanges()

              // Use x, y values to perform additional actions or update the ViewState
              // For example, you might want to update the position of your views
              viewModel.updateViewStateWithNewPosition(x, y)*/
        viewModel.addChangeToOrder(view)
    
        /*  viewModel.storeInitialPosition(newTextView)
          viewModel.addDynamicView(newTextView)*/

    }
    /*
        private fun observeViewState() {
            viewModel.viewState.observe(viewLifecycleOwner) { newState ->
                applyViewStateToUI(newState)
            }
        }

        private fun applyViewStateToUI(viewState: ViewState) {
            for (textView in dynamicViewsList) {
                // Animate the changes for a smoother transition
                textView.animate().x(viewState.positionX).y(viewState.positionY).setDuration(300)
                    .start()
            }
        }*/
}
