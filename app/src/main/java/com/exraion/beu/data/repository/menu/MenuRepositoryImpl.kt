package com.exraion.beu.data.repository.menu

import com.exraion.beu.base.NetworkOnlyResource
import com.exraion.beu.data.source.local.LocalDataSource
import com.exraion.beu.data.source.remote.RemoteDataSource
import com.exraion.beu.data.source.remote.RemoteResponse
import com.exraion.beu.data.source.remote.api.model.ingredient.IngredientResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuDetailResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuListResponse
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.Ingredient
import com.exraion.beu.model.MenuDetail
import com.exraion.beu.model.MenuList
import com.exraion.beu.util.toIngredient
import com.exraion.beu.util.toMenuDetail
import com.exraion.beu.util.toMenuList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MenuRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): MenuRepository {
    override fun fetchMenus(): Flow<Resource<List<MenuList>>> =
        object : NetworkOnlyResource<List<MenuList>, List<MenuListResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<MenuListResponse>?>> {
                val token = localDataSource.readPrefToken().first() ?: ""
                return remoteDataSource.fetchMenus(token)
            }
    
            override fun mapTransform(data: List<MenuListResponse>?): List<MenuList> {
                return data?.map { it.toMenuList() } ?: emptyList()
            }
    
        }.asFlow()
    
    override fun fetchSearchedMenus(query: String): Flow<Resource<List<MenuList>>> =
        object : NetworkOnlyResource<List<MenuList>, List<MenuListResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<MenuListResponse>?>> {
                val token = localDataSource.readPrefToken().first() ?: ""
                return remoteDataSource.fetchSearchedMenus(token, query)
            }
    
            override fun mapTransform(data: List<MenuListResponse>?): List<MenuList> {
                return data?.map { it.toMenuList() } ?: emptyList()
            }
    
        }.asFlow()
    
    override fun fetchCategorizedMenus(category: String): Flow<Resource<List<MenuList>>> =
        object : NetworkOnlyResource<List<MenuList>, List<MenuListResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<MenuListResponse>?>> {
                val token = localDataSource.readPrefToken().first() ?: ""
                return remoteDataSource.fetchCategorizedMenus(token, category)
            }
    
            override fun mapTransform(data: List<MenuListResponse>?): List<MenuList> {
                return data?.map { it.toMenuList() } ?: emptyList()
            }
    
        }.asFlow()
    
    override fun fetchDietMenus(): Flow<Resource<List<MenuList>>> =
        object : NetworkOnlyResource<List<MenuList>, List<MenuListResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<MenuListResponse>?>> {
                val token = localDataSource.readPrefToken().first() ?: ""
                return remoteDataSource.fetchDietMenus(token)
            }
    
            override fun mapTransform(data: List<MenuListResponse>?): List<MenuList> {
                return data?.map { it.toMenuList() } ?: emptyList()
            }
    
        }.asFlow()
    
    override fun fetchMenuDetail(menuId: String): Flow<Resource<MenuDetail>> =
        object : NetworkOnlyResource<MenuDetail, MenuDetailResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<MenuDetailResponse?>> {
                val token = localDataSource.readPrefToken().first() ?: ""
                return remoteDataSource.fetchMenuDetail(token, menuId)
            }
    
            override fun mapTransform(data: MenuDetailResponse?): MenuDetail {
                return data!!.toMenuDetail()
            }
    
        }.asFlow()
    
    override fun fetchMenuIngredients(menuId: String): Flow<Resource<List<Ingredient>>> =
        object : NetworkOnlyResource<List<Ingredient>, List<IngredientResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<IngredientResponse>?>> {
                val token = localDataSource.readPrefToken().first() ?: ""
                return remoteDataSource.fetchMenuIngredients(token, menuId)
            }
    
            override fun mapTransform(data: List<IngredientResponse>?): List<Ingredient> {
                return data?.map { it.toIngredient() } ?: emptyList()
            }
        }.asFlow()
    
}