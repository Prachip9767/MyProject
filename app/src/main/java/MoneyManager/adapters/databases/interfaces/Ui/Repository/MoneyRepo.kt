package MoneyManager.adapters.databases.interfaces.Ui.Repository

import androidx.lifecycle.LiveData
import MoneyManager.adapters.remote.requests.local.BudgetDao
import MoneyManager.adapters.databases.interfaces.models.BudgetIncome
import com.example.taskmanagerapicalling.models.remote.Resource
import com.example.taskmanagerapicalling.models.remote.ResponseHandler
import com.example.taskmanagerapicalling.models.remote.TasksAPI
import com.example.taskmanagerapicalling.models.remote.requests.LoginRequestModel
import com.example.taskmanagerapicalling.models.remote.responses.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import MoneyManager.adapters.remote.Network
import MoneyManager.adapters.remote.requests.responses.SignUpRequestModel

/**
 * communicate with the database and make the task happens
 * If you need to communicate with the Database then you need to add the object of Dao
 * If you need to communicate with the API-Calling then you need to add the object of Retrofit
 */
class MoneyRepo(private val budgetDao: BudgetDao) {

    private val api: TasksAPI = Network.getRetrofit().create(TasksAPI::class.java)
    private val responseHandler = ResponseHandler()
    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MGE0YmI3OTAzMjdlN2MwNmE2MTk1ODYiLCJpYXQiOjE2MzIxMzg2ODR9.cTxpYQrTfvramIOSPih6b1hJO_x1G-V2GmaRnTYSjU0"

    //as it will take some time so that's we are marking it as suspend function
    //login
    suspend fun login(loginRequestModel: LoginRequestModel): Resource<LoginResponse> {
        return try {
            val response = api.login(loginRequestModel)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            e.printStackTrace()
            responseHandler.handleException(e) //passing exception here
        }
    }

    //signup
    suspend fun signUp(signUpRequestModel: SignUpRequestModel):Resource<SignUpRequestModel>{
        return try {
            val response = api.signUp(signUpRequestModel)
            responseHandler.handleSuccess(response)
        }catch (e:Exception){
            responseHandler.handleException(e)
        }
    }

    // this operation should be happened in the bg thread
    fun addTaskToRoom(budgetIncome: BudgetIncome) {
        CoroutineScope(Dispatchers.IO).launch {
           budgetDao.addBudgetIncome(budgetIncome)
        }
    }


    //returning the live data of list
    fun getAllTask(): LiveData<List<BudgetIncome>> {
        return budgetDao.getAllBudgetIncome()
    }

    fun updateTask(budgetIncome: BudgetIncome) {
        CoroutineScope(Dispatchers.IO).launch {
            budgetDao.updateBudgetIncome(budgetIncome)
        }
    }


    fun deleteTask(budgetIncome: BudgetIncome) {
        CoroutineScope(Dispatchers.IO).launch {
           budgetDao.deleteBudgetIncome(budgetIncome)
        }
    }
}