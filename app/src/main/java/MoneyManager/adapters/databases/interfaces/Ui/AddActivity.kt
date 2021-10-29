package MoneyManager.adapters.databases.interfaces.Ui

import MoneyManager.adapters.databases.interfaces.Ui.Repository.MoneyRepo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.budgetmanager.R
import MoneyManager.adapters.remote.BudgetRoomDatabase
import MoneyManager.adapters.remote.requests.local.BudgetDao
import MoneyManager.adapters.databases.interfaces.models.BudgetIncome
import androidx.lifecycle.ViewModelProviders
import com.example.taskmanagerapicalling.viewmodels.TaskViewModel
import com.example.taskmanagerapicalling.viewmodels.TaskViewModelFactory
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    lateinit var roomDatabase: BudgetRoomDatabase
    lateinit var budgeDao: BudgetDao
    lateinit var viewModel: TaskViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        roomDatabase = BudgetRoomDatabase.getDataBaseObject(this)
        budgeDao = roomDatabase.getTaskDao()
        val repo = MoneyRepo(budgeDao)
        val taskViewModelFactory = TaskViewModelFactory(repo)
        btnincome.setOnClickListener {
            viewModel = ViewModelProviders.of(this, taskViewModelFactory).get(TaskViewModel::class.java)
            val intent = Intent(this, HomeActivity::class.java)
            var adding = BudgetIncome(tvIncomeDesc.text.toString(), tvIncomeAmount.text.toString().toDouble(),tvDate.text.toString(),"Income")

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.addTask(adding)
            }
            startActivity(intent)

        }

//        btnExpenses.setOnClickListener {
//
//            val intent = Intent(this, HomeActivity::class.java)
//            var adding = BudgetExpenses(tvExpensesDesc.text.toString(), tvExpenseAmount.text.toString().toDouble(),"Expenses")
//
//            CoroutineScope(Dispatchers.IO).launch {
//                budgeDao.addBudgetExpenses(adding)
//            }
//            startActivity(intent)
//
//        }
    }
}