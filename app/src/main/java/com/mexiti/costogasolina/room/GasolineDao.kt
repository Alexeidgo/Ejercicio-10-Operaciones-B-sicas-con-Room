package com.mexiti.costogasolina.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mexiti.costogasolina.model.GasolineTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface GasolineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: GasolineTransaction)

    @Query("SELECT * FROM gasoline_transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<GasolineTransaction>>

    // Buscar una transacción por su ID
    @Query("SELECT * FROM gasoline_transactions WHERE id = :id")
    suspend fun getTransactionById(id: Int): GasolineTransaction?

    // Actualizar un registro existente
    @Update
    suspend fun updateTransaction(transaction: GasolineTransaction)

    // Eliminar un registro de la base de datos
    @Delete
    suspend fun deleteTransaction(transaction: GasolineTransaction)
}
