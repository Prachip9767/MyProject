package MoneyManager.adapters.databases.interfaces.Ui

import MoneyManager.adapters.databases.interfaces.Ui.Repository.MoneyRepo
import MoneyManager.adapters.remote.BudgetRoomDatabase
import MoneyManager.adapters.remote.requests.local.BudgetDao
import MoneyManager.adapters.remote.requests.responses.SignUpRequestModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.budgetmanager.R
import com.example.taskmanagerapicalling.models.remote.Status
import com.example.taskmanagerapicalling.viewmodels.TaskViewModel
import com.example.taskmanagerapicalling.viewmodels.TaskViewModelFactory
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var viewModel: TaskViewModel
    lateinit var incomeAppDao: BudgetDao
    lateinit var roomDatabase: BudgetRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        roomDatabase = BudgetRoomDatabase.getDataBaseObject(this)
        incomeAppDao = roomDatabase.getTaskDao()
        val viewModelFactory = TaskViewModelFactory(repo = MoneyRepo(incomeAppDao))
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)


        btnSignUp.setOnClickListener {
            val signUpRequestModel = SignUpRequestModel(
                age = etAgeSignUp.text.toString().toInt(),
                email = etEmailSignUp.text.toString(),
                name = etNameSignUp.text.toString(),
                password = etPasswordSignUp.text.toString()
            )

            viewModel.signUp(signUpRequestModel).observe(this, Observer {
                val response = it

                when (response.status) {
                    Status.SUCCESS -> {
                        val intent = Intent(this, LogInActivity::class.java)
                        startActivity(intent)
                    }
                    Status.ERROR -> {
                        val error = response.message!!
                        Toast.makeText(this, "error is $error", Toast.LENGTH_LONG).show()

                    }
                    Status.LOADING -> {
                        Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
                    }
                }
            })
            btnGoToLogin.setOnClickListener {
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
            }
        }
    }
    }
