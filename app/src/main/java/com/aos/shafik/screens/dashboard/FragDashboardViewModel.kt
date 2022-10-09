package com.aos.shafik.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.shafik.commons.GenericStateFlow
import com.aos.shafik.enums.EnumString
import com.aos.shafik.model.AlbumResp
import com.aos.shafik.model.Results
import com.aos.shafik.retrofit.repository.AlbumRepo
import com.aos.shafik.room.entity.EntityAlbum
import com.aos.shafik.room.repository.DaoAlbumRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@HiltViewModel
class FragDashboardViewModel @Inject constructor(
    private val accountRepo: AlbumRepo,
    private val daoAlbumRepo: DaoAlbumRepo
) : ViewModel() {

    //API KEY FROM CPP FILE ++
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    /**
     * @Usage: get API key from CPP file
     * @Author: Shafik Shaikh
     */
    private external fun getAPIKey(): String?
    private external fun getUrl(): String?
    //API KEY FROM CPP FILE --

    val stateFlowAlbum = MutableStateFlow<GenericStateFlow<AlbumResp>>(GenericStateFlow.Empty)
    val stateFlowList =
        MutableStateFlow<GenericStateFlow<ArrayList<EntityAlbum>>>(GenericStateFlow.Empty)

    init {
        getAlbumList()
    }

    /**
     * @Usage: get album data from local db
     * @Author: Shafik Shaikh
     */
    private fun getAlbumList() {
        viewModelScope.launch(Dispatchers.IO) {
            daoAlbumRepo.getAllAlbum().collectLatest {
                stateFlowList.emit(it)
            }
        }
    }

    /**
     * @Usage: get album data from server and emit AlbumResp in stateFlowAlbum obj
     * @Author: Shafik Shaikh
     */
    fun getAlbumData() {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepo.getAlbumData(
                url = getUrl().toString(),
                method = EnumString.METHOD.getValue(),
                album = EnumString.ALBUM.getValue(),
                apiKey = getAPIKey().toString(),
                format = EnumString.FORMAT_JSON.getValue()
            ).collectLatest {
                stateFlowAlbum.emit(it)
            }
        }
    }

    /**
     * @Usage: insert album response data in local db
     * @Author: Shafik Shaikh
     */
    fun insertAlbumData(results: Results?) {
        viewModelScope.launch(Dispatchers.IO) {
            results?.albumMatches?.album?.let {
                for (albumItem in it) {
                    val entityAlbum = EntityAlbum(
                        url = albumItem.url.toString(),
                        name = albumItem.name.toString(),
                        artist = albumItem.artist.toString(),
                        image = albumItem.image?.get(3)?.text.toString()
                    )
                    daoAlbumRepo.insertAlbum(entityAlbum)
                }
            }
            getAlbumList()
        }
    }

    /**
     * @Usage: update album data in local db
     * @Author: Shafik Shaikh
     */
    fun updateImage(selectedEntityAlbum: EntityAlbum) {
        viewModelScope.launch(Dispatchers.IO)
        {
            daoAlbumRepo.updateAlbum(entityAlbum = selectedEntityAlbum)
        }
    }
}