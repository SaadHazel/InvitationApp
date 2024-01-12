package com.saad.invitation.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.saad.invitation.api.MyApi
import com.saad.invitation.db.Appdb
import com.saad.invitation.models.CardDesignModel
import com.saad.invitation.models.Hit
import com.saad.invitation.models.SingleCardItemsModel
import com.saad.invitation.utils.isInternetAvailable
import com.saad.invitation.utils.log
import com.sofit.app.utils.SingleLiveEvent

class RepoImpl(
    private val api: MyApi,
    private val db: Appdb,
    private val context: Context,
    private val firestore: FirebaseFirestore,

    ) : Repo {


    override val cardDesignLiveData = MutableLiveData<List<CardDesignModel>>()
    override val singleCardItemsLiveData = SingleLiveEvent<SingleCardItemsModel>()
    override val imagesLiveData = MutableLiveData<List<Hit>>()
    private val weddingCardsCollection = firestore.collection("wedding_cards_designs")
    override suspend fun networkCheck() {
        if (!isInternetAvailable(context) || images.value != null) {
            doDatabaseCallGet();
        } else {
            doNetworkCall();
        }
    }


    override suspend fun doNetworkCall() {
        try {
            val response = api.getImages(2, 20, "wedding+invitation+card", "vertical")
            if (response.isSuccessful) {
                imagesLiveData.postValue(response.body()?.hits)
                response.body()?.hits?.forEach { hit ->
                    val count = excludeSameImage(hit.id)
                    if (count == 0) {
                        doDatabaseCallAdd(hit)
                    }
                }
            } else {
                // Handle unsuccessful response here
            }
        } catch (e: Exception) {
            // Handle network call exception here
        }
    }

    override suspend fun doDatabaseCallAdd(cards: Hit) {
        db.designDao().insertAll(cards)
    }

    override suspend fun doDatabaseCallGet() {
        imagesLiveData.postValue(db.designDao().getAll())

        /*   db.designDao().getAll().forEach { hit ->
               imagesLiveData.postValue(hit)
           }*/
    }

    override suspend fun excludeSameImage(id: Int): Int {
        return db.designDao().excludeSame(id)
    }

    override suspend fun fetchSingleDocumentDataFromFireStore(documentId: String) {
        try {
            weddingCardsCollection.document(documentId).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    log("Exception : $e")
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {

                    val background = snapshot.getString("background") ?: ""
                    val arrayOfMaps =
                        snapshot.get("views") as? List<Map<String, Any>> ?: emptyList()

//                    val viewOneMap: ArrayList<Map<String, Any>> = ArrayList()
                    val weddingCardDesign = SingleCardItemsModel(
                        background = background,
                        arrayOfMaps = arrayOfMaps,
                    )

                    singleCardItemsLiveData.postValue(weddingCardDesign)
                }
            }
        } catch (e: Exception) {
            log("Exception during firebase op: $e")
        }
    }

    override suspend fun fetchAllDocumentsDataFromFireStore() {
        weddingCardsCollection.get().addOnSuccessListener { result ->
            val allCardsList = mutableListOf<CardDesignModel>()

            for (document in result) {
                val customModel = CardDesignModel(
                    id = document.id,
                    background = document.data["background"].toString()
                )
                allCardsList.add(customModel)
            }
            cardDesignLiveData.postValue(allCardsList)
        }
            .addOnFailureListener {
                log("Error getting document")
            }
    }
}