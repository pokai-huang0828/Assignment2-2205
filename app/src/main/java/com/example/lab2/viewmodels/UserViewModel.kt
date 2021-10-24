package com.example.lab2.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lab2.model.entities.User
import com.example.lab2.model.repositories.UserRepository
import com.example.lab2.model.responses.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel(val userRepository: UserRepository) : ViewModel() {
    val userStateFlow = MutableStateFlow<Resource<*>?>(null)

    init {
        viewModelScope.launch {
            userRepository.getUsers().collect {
                userStateFlow.value = it
            }
        }
    }

    suspend fun getUserById(id: String) = userRepository.getUserById(id)

    fun createUser(user: User, onResponse: (Resource<*>) -> Unit) = viewModelScope.launch {
        userRepository.createUser(user, onResponse)
    }

    fun updateUser(user: User, onResponse: (Resource<*>) -> Unit) = viewModelScope.launch {
        userRepository.updateUser(user, onResponse)
    }

    fun deleteUser(user: User, onResponse: (Resource<*>) -> Unit) = viewModelScope.launch {
        userRepository.deleteUser(user, onResponse)
    }

}

class UserViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        }

        throw IllegalStateException()
    }
}