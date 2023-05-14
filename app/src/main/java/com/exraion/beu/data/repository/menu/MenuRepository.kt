package com.exraion.beu.data.repository.menu

import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.Ingredient
import com.exraion.beu.model.MenuDetail
import com.exraion.beu.model.MenuList
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    
    fun fetchMenus(): Flow<Resource<List<MenuList>>>
    
    fun fetchSearchedMenus(query: String): Flow<Resource<List<MenuList>>>
    
    fun fetchCategorizedMenus(category: String): Flow<Resource<List<MenuList>>>
    
    fun fetchDietMenus(): Flow<Resource<List<MenuList>>>

    fun fetchExclusiveMenus(): Flow<Resource<List<MenuList>>>
    
    fun fetchMenuDetail(menuId: String): Flow<Resource<MenuDetail>>
    
    fun fetchMenuIngredients(menuId: String): Flow<Resource<List<Ingredient>>>
}