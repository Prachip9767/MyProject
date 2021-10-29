package MoneyManager.adapters.remote.requests.local


import MoneyManager.adapters.databases.interfaces.models.BudgetIncome

interface OnItemClickListener {

    fun onEditBudgetIncome(budgetIncome: BudgetIncome)
    fun onDeleteBudgetIncome(budgetIncome: BudgetIncome)

//    fun onEditBudgetExpense(budgetExpenses: BudgetExpenses)
//    fun onDeleteBudgetExpenses(budgetExpenses: BudgetExpenses)

}