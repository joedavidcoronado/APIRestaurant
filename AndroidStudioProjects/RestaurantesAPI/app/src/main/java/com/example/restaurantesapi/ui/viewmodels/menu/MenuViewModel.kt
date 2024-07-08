package com.example.restaurantesapi.ui.viewmodels.menu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.models.Menus
import com.example.restaurantesapi.models.MenuInsert
import com.example.restaurantesapi.repositories.objetos.MenuRepository


class MenuViewModel : ViewModel() {
    private val _menuList: MutableLiveData<Menus> by lazy {
        MutableLiveData<Menus>(Menus())
    }
    val menuList: LiveData<Menus> get() = _menuList

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity

    fun fetchMenus(id: Int) {
        MenuRepository.getMenu(
            id,
            success = { menus ->
                menus?.let {
                    _menuList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun addCategory(token: String, name: String, id: String) {
        val menuInsert = MenuInsert(
            name = name,
            restaurant_id = id
        )

        MenuRepository.insertMenu(token ,menuInsert,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }
}