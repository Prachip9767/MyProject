package MoneyManager.adapters.databases.interfaces.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetmanager.R

import MoneyManager.adapters.BudgetIncomeAdapter
import MoneyManager.adapters.databases.interfaces.Ui.Repository.MoneyRepo
import MoneyManager.adapters.remote.BudgetRoomDatabase
import MoneyManager.adapters.remote.requests.local.BudgetDao
import MoneyManager.adapters.remote.requests.local.OnItemClickListener

import MoneyManager.adapters.databases.interfaces.models.BudgetIncome
import androidx.lifecycle.ViewModelProviders
import com.example.taskmanagerapicalling.models.remote.Status
import com.example.taskmanagerapicalling.models.remote.requests.LoginRequestModel
import com.example.taskmanagerapicalling.viewmodels.TaskViewModel
import com.example.taskmanagerapicalling.viewmodels.TaskViewModelFactory
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.longToast

class HomeActivity : AppCompatActivity(), OnItemClickListener {
    lateinit var viewModel: TaskViewModel
    private var budgetListIncome = mutableListOf<BudgetIncome>()
 //   private var budgetListExpenses = mutableListOf<BudgetExpenses>()
    private lateinit var budgetIncomeAdapter: BudgetIncomeAdapter
   // private lateinit var budgetExpensesAdapter: BudgetExpenseAdapter
    private lateinit var roomDatabase: BudgetRoomDatabase
    private lateinit var budgetDao: BudgetDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        roomDatabase = BudgetRoomDatabase.getDataBaseObject(this)
        budgetDao = roomDatabase.getTaskDao()

        val repo = MoneyRepo(budgetDao)
        val viewModelFactory = TaskViewModelFactory(repo)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)
        fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        //income setting adaptor
        budgetIncomeAdapter = BudgetIncomeAdapter(this, budgetListIncome, this)
        recyclerViewIncome.adapter = budgetIncomeAdapter
        recyclerViewIncome.layoutManager = LinearLayoutManager(this)
//
//        //expenses setting adaptor
//        budgetExpensesAdapter = BudgetExpenseAdapter(this, budgetListExpenses, this)
//        recyclerViewExpense.adapter = budgetExpensesAdapter
//        recyclerViewExpense.layoutManager = LinearLayoutManager(this)


        var income = 0.0
        var expense = 0.0

        //to add income
        budgetDao.getAllIncome().observe(this, Observer {
            if (it != null) {
                tvIncome.text = it.toString()
                income = it
//                tvBalance.text = (income - expense).toString()

            } else {
                tvIncome.text = "0.0"
            }
        })

        // to add Expense
//        budgetDao.getAllExpenses().observe(this, Observer {
//            if (it != null) {
//                tvExpense.text = it.toString()
//                expense = it
//                tvBalance.text = (income - expense).toString()
//            } else {
//                tvExpense.text = "0.0"
//            }
//        })
//
//        if (income == null && expense == null) {
//            tvBalance.text = "0.0"
//        }

        //to live data
        budgetDao.getAllBudgetIncome().observe(this, Observer {
            val task = it
            CoroutineScope(Dispatchers.Main).launch {
                budgetListIncome.clear()
                budgetListIncome.addAll(task)
                budgetIncomeAdapter.notifyDataSetChanged()

            }
        })

//        budgetDao.getAllBudgetExpense().observe(this, Observer {
//            val task = it
//
//            CoroutineScope(Dispatchers.IO).launch {
//                budgetListExpenses.clear()
//                budgetListExpenses.addAll(task)
//                budgetExpensesAdapter.notifyDataSetChanged()
//
//            }
//        })
   }


    override fun onEditBudgetIncome(budgetIncome: BudgetIncome) {
        linearLinear.visibility = View.VISIBLE
        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                budgetIncome.title = eteditdesc.text.toString()
                budgetIncome.amount = eteditamount.text.toString().toDouble()
                viewModel.updateTask(budgetIncome)
            }
            linearupdate.visibility = View.GONE
        }
    }

    override fun onDeleteBudgetIncome(budgetIncome: BudgetIncome) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.deleteTask(budgetIncome)
        }
    }

//    override fun onEditBudgetExpense(budgetExpenses: Any) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onDeleteBudgetExpenses(budgetExpenses: Any) {
//        TODO("Not yet implemented")
//    }


//    override fun onEditBudgetExpense(budgetExpenses: BudgetExpenses) {
//        linearupdate.visibility = View.VISIBLE
//        btnUpdate.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                budgetExpenses.title = eteditdesc.text.toString()
//                budgetExpenses.amount = eteditamount.text.toString().toDouble()
//                budgetDao.updateBudgetExpenses(budgetExpenses)
//            }
//            linearupdate.visibility = View.GONE
//        }
//    }
//
//    override fun onDeleteBudgetExpenses(budgetExpenses: BudgetExpenses) {
//        CoroutineScope(Dispatchers.IO).launch {
//            budgetDao.deleteBudgetExpenses(budgetExpenses)
//        }
//    }
}