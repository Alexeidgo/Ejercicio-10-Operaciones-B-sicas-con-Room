package com.mexiti.costogasolina.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.costogasolina.model.GasolineTransaction
import com.mexiti.costogasolina.room.GasolineDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GasolineViewModel(private val gasolineDao: GasolineDao): ViewModel() {
    val transaction: StateFlow<List<GasolineTransaction>> = gasolineDao.getAllTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    fun addTransaction(
        price: Double,
        liters: Double,
        tip: Double,
        date: String,
        kilometers: Double,
        onComplete: () -> Unit
    ){
        viewModelScope.launch {
            val newTransaction = GasolineTransaction(
                pricePerLiter = price,
                liters = liters,
                tip = tip,
                date = date,
                kilometers = kilometers
            )
            gasolineDao.insertTransaction(newTransaction)
            onComplete()
        }
    }

    // Buscar el registro que se va a editar o eliminar.
    suspend fun getTransactionById(id: Int): GasolineTransaction? {
        return gasolineDao.getTransactionById(id)
    }

    // onComplete se ejecuta después de guardar el cambio en Room.
    fun updateTransaction(transaction: GasolineTransaction, onComplete: () -> Unit) {
        viewModelScope.launch {
            gasolineDao.updateTransaction(transaction)
            onComplete()
        }
    }

    fun deleteTransaction(transaction: GasolineTransaction, onComplete: () -> Unit) {
        viewModelScope.launch {
            gasolineDao.deleteTransaction(transaction)
            onComplete()
        }
    }
}
