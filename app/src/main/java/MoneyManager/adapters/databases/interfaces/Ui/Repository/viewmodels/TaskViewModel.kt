package com.example.taskmanagerapicalling.viewmodels

import MoneyManager.adapters.databases.interfaces.Ui.Repository.MoneyRepo
import MoneyManager.adapters.databases.interfaces.models.BudgetIncome
import MoneyManager.adapters.remote.requests.responses.SignUpRequestModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskmanagerapicalling.models.local.Task
import com.example.taskmanagerapicalling.models.remote.Resource
import com.example.taskmanagerapicalling.models.remote.requests.LoginRequestModel
import com.example.taskmanagerapicalling.models.remote.responses.LoginResponse
import kotlinx.coroutines.Dispatchers

class TaskViewModel(val repo: MoneyRepo) : ViewModel() {

    fun userLogin(loginRequestModel: LoginRequestModel): LiveData<Resource<LoginResponse>> {
        val livedata =  liveData(Dispatchers.IO){
            val result = repo.login(loginRequestModel)
            emit(result)
        }
        return livedata
      /**
        CoroutineScope(Dispatchers.IO).launch {
            repo.login(loginRequestModel)
        }
        */
    }
    //signup
    fun signUp(signUpRequestModel: SignUpRequestModel):LiveData<Resource<SignUpRequestModel>>{
        return liveData(Dispatchers.IO) {
            val result = repo.signUp(signUpRequestModel)
            emit(result)
        }
    }
    fun addTask(budgetIncome: BudgetIncome) {
        repo.addTaskToRoom(budgetIncome)
    }

    fun getTasks(): LiveData<List<BudgetIncome>> {
        return repo.getAllTask()
    }

    fun updateTask(budgetIncome: BudgetIncome) {
        repo.updateTask(budgetIncome)
    }

    fun deleteTask(budgetIncome: BudgetIncome) {
        repo.deleteTask(budgetIncome)
    }

}