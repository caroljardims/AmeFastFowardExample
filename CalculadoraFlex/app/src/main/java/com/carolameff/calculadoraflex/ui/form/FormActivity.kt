package com.carolameff.calculadoraflex.ui.form

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.carolameff.calculadoraflex.R
import com.carolameff.calculadoraflex.extensions.format
import com.carolameff.calculadoraflex.model.CarData
import com.carolameff.calculadoraflex.ui.result.ResultActivity
import com.carolameff.calculadoraflex.utils.DatabaseUtil
import com.carolameff.calculadoraflex.watchers.DecimalTextWatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    companion object {
        const val GAS_PRICE = "gas_price"
        const val ETHANOL_PRICE = "ethanol_price"
        const val GAS_AVERAGE = "gas_average"
        const val ETHANOL_AVERAGE = "ethanol_average"
        const val CAR_DATA = "car_data"
    }

    private lateinit var userId: String
    private lateinit var mAuth: FirebaseAuth
    private val firebaseReferenceNode = "CarData"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        mAuth = FirebaseAuth.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        listenerFirebaseRealtime()

        gasolinePriceLabel.addTextChangedListener(DecimalTextWatcher(gasolinePriceLabel as EditText))
        ethanolPriceLabel.addTextChangedListener(DecimalTextWatcher(ethanolPriceLabel))
        gasolineConsume.addTextChangedListener(DecimalTextWatcher(gasolineConsume, 1))
        ethanolConsume.addTextChangedListener(DecimalTextWatcher(ethanolConsume, 1))


        calculateButton.setOnClickListener {

            val carData = CarData(
                gasolinePriceLabel.text.toString().toDouble(),
                ethanolPriceLabel.text.toString().toDouble(),
                gasolineConsume.text.toString().toDouble(),
                ethanolConsume.text.toString().toDouble()
            )

            saveCarData(carData)

            val nextSreen = Intent(this@FormActivity, ResultActivity::class.java)
            nextSreen.putExtra(CAR_DATA, carData)

            startActivity(nextSreen)
        }

    }

    private fun listenerFirebaseRealtime() {

        DatabaseUtil.getDatabase().getReference(firebaseReferenceNode).child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val carData = p0.getValue(CarData::class.java)

                    carData?.run {
                        gasolinePriceLabel.setText(gasPrice.format(2))
                        ethanolPriceLabel.setText(ethanolPrice.format(2))
                        gasolineConsume.setText(gasAverage.format(1))
                        ethanolConsume.setText(ethanolAverage.format(1))
                    }
                }

            })
    }

    private fun saveCarData(carData: CarData) {
        FirebaseDatabase
            .getInstance()
            .getReference(firebaseReferenceNode)
            .child(userId).setValue(carData)
    }

}
