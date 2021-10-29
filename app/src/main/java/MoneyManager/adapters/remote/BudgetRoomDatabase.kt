package MoneyManager.adapters.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import MoneyManager.adapters.remote.requests.local.BudgetDao

import MoneyManager.adapters.databases.interfaces.models.BudgetIncome

@Database(entities = [BudgetIncome::class], version = 1)
abstract class BudgetRoomDatabase : RoomDatabase() {

    abstract fun getTaskDao(): BudgetDao

    companion object {
        private var INSTANCE: BudgetRoomDatabase? = null

        fun getDataBaseObject(context: Context): BudgetRoomDatabase {
            if (INSTANCE == null) {

                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    BudgetRoomDatabase::class.java,
                    "task_db"
                )
                builder.fallbackToDestructiveMigration()

                INSTANCE = builder.build()
                return INSTANCE!!
            } else {
                return INSTANCE!!
            }
        }
    }
}
