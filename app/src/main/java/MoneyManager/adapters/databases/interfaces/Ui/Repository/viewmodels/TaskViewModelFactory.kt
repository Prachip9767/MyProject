package com.example.taskmanagerapicalling.viewmodels

import MoneyManager.adapters.databases.interfaces.Ui.Repository.MoneyRepo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskViewModelFactory(val repo: MoneyRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(repo) as T  //typecasting here, T is the generic type here
    }
}