package MoneyManager.adapters.remote.requests.local

import androidx.lifecycle.LiveData
import androidx.room.*

import MoneyManager.adapters.databases.interfaces.models.BudgetIncome

@Dao
interface BudgetDao {

    //budget_Income
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBudgetIncome(budgetIncome: BudgetIncome)

    @Query("select * from budget_income")
    fun getAllBudgetIncome():LiveData<List<BudgetIncome>>

    @Query("select SUM(amount) as TOTAL from budget_income")
    fun getAllIncome() : LiveData<Double>

    @Update
    fun updateBudgetIncome(budgetIncome: BudgetIncome)

    @Delete
    fun deleteBudgetIncome(budgetIncome: BudgetIncome)


}