package com.saad.invitation.viewmodels


import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saad.invitation.models.CardDesignModel
import com.saad.invitation.models.Hit
import com.saad.invitation.models.SingleCardItemsModel
import com.saad.invitation.repo.Repo
import com.saad.invitation.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Stack

class MainViewModel(private val repo: Repo) : ViewModel() {

    /*    private val undoHistory = Stack<ViewState>()
        private val redoHistory = Stack<ViewState>()
         // LiveData to observe changes in ViewState
        val viewState = MutableLiveData<ViewState>()
        */
    // List to store references to dynamically created views
    val dynamicViewsList = mutableListOf<View>()
    private val initialPositionsList = mutableListOf<MutableMap<View, Pair<Float, Float>>>()
    private val initialPositions = mutableMapOf<View, Pair<Float, Float>>()
    private val updatedPositions = mutableMapOf<View, Pair<Float, Float>>()


    // Stack to keep track of the order of changes
    private val changeOrderStack = Stack<View>()

    // Stack to keep track of views that have been undone
    private val redoStack = Stack<View>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchAllDocumentsDataFromFireStore()
            repo.doDatabaseCallGet()
            repo.networkCheck()
        }
    }

    val images: LiveData<List<Hit>>
        get() = repo.images
    val designs: LiveData<List<CardDesignModel>>
        get() = repo.designs

    val singleCardDesign: LiveData<SingleCardItemsModel>
        get() = repo.singleCardItemsLiveData

    val loading: LiveData<Boolean>
        get() = repo.loading

    fun doDatabaseCallAdd(cards: Hit) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.doDatabaseCallAdd(cards)
        }
    }

    fun doDatabaseCallGet() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.doDatabaseCallGet()

        }
    }

    fun fetchSingleDocumentDataFromFireStore(document: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchSingleDocumentDataFromFireStore(document)
        }
    }

    /*
        fun captureStateBeforeChanges() {
            val currentState = captureViewState()
            undoHistory.push(currentState)
            redoHistory.clear()
        }

        fun undoChanges() {
            if (undoHistory.isNotEmpty()) {
                val currentState = captureViewState()
                redoHistory.push(currentState)

                val previousState = undoHistory.pop()
                applyViewState(previousState)
            }
        }

        fun redoChanges() {
            if (redoHistory.isNotEmpty()) {
                val currentState = captureViewState()
                undoHistory.push(currentState)

                val nextState = redoHistory.pop()
                applyViewState(nextState)
            }
        }

        fun updateViewStateWithNewPosition(x: Float, y: Float) {
            // Update the ViewState with the new position
            viewState.value = ViewState(positionX = x, positionY = y, size = 0.0f)
        }


        private fun applyViewState(viewState: ViewState) {
            this.viewState.value = viewState
        }

        private fun captureViewState(): ViewState {
            // Capture the current state of your views
            // For example, record positions, sizes, or any other relevant properties
            return ViewState(
                positionX = viewState.value?.positionX ?: 0f,
                positionY = viewState.value?.positionY ?: 0f,
                size = 0.0f  // You need to replace this with the actual size logic
            )
        }*/

    fun addDynamicView(view: View) {
        dynamicViewsList.add(view)
    }

    fun storeInitialPosition(view: View) {
        initialPositions[view] = Pair(view.x, view.y)
        // Clone the current initialPositions map to store in the list
        /*     val clonedMap = HashMap<View, Pair<Float, Float>>(initialPositions)
             initialPositionsList.add(clonedMap)*/
    }

    fun undoToInitialPosition(view: View) {
        storeUpdatedPosition(view)
        addToRedoStack(view)

        /*     // Get the last stored initial positions map
             val lastInitialPositions = initialPositionsList.lastOrNull()


             lastInitialPositions?.let { initialPositionsMap ->
                 initialPositionsMap[view]?.let { (initialX, initialY) ->
                     // Animating the changes for a smoother transition
                     view.animate().x(initialX).y(initialY).setDuration(300).start()
                 }
             }*/
        initialPositions[view]?.let { (initialX, initialY) ->
            // Animating the changes for a smoother transition
            view.animate().x(initialX).y(initialY).setDuration(300).start()
        }
    }

    fun addChangeToOrder(view: View) {
        changeOrderStack.push(view)
        log("Stack: $changeOrderStack")

        for (element in changeOrderStack) {
            log("Stack Elements: $element")
        }

    }

    fun popLastChangeOrder(): View? {
        return if (changeOrderStack.isNotEmpty()) {
            changeOrderStack.pop()
        } else {
            null
        }
    }

    //Redo
    private fun addToRedoStack(view: View) {
        redoStack.push(view)
    }

    private fun storeUpdatedPosition(view: View) {
        updatedPositions[view] = Pair(view.x, view.y)
    }

    fun popLastRedo(): View? {
        return if (redoStack.isNotEmpty()) {
            redoStack.pop()
        } else {
            null
        }
    }

    fun clearRedoStack() {
        redoStack.clear()
    }

    fun redoToInitialPosition(view: View) {
        updatedPositions[view]?.let { (initialX, initialY) ->
            // Animating the changes for a smoother transition
            view.animate().x(initialX).y(initialY).setDuration(300).start()
        }
    }

    // Redo the last undone change

}