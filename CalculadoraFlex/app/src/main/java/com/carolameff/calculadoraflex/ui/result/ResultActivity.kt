package com.carolameff.calculadoraflex.ui.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carolameff.calculadoraflex.R
import com.carolameff.calculadoraflex.extensions.format
import com.carolameff.calculadoraflex.model.CarData
import com.carolameff.calculadoraflex.ui.form.FormActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        calculate()
    }

    private fun calculate() {

//        val gasPrice = intent.extras?.getDouble(FormActivity.GAS_PRICE, 0.0) ?: 0.0
//        val ethanolPrice = intent.extras.getDouble(FormActivity.ETHANOL_PRICE, 0.0) ?: 0.0
//        val gasAverage = intent.extras?.getDouble(FormActivity.GAS_AVERAGE, 0.0) ?: 0.0
//        val ethanolAverage = intent.extras?.getDouble(FormActivity.ETHANOL_AVERAGE, 0.0) ?:0.0

        val carData = intent.extras?.getParcelable<CarData>(FormActivity.CAR_DATA)

        carData?.run {
            val performanceOfMyCar = ethanolAverage / gasAverage
            val priceOfFuelIndice = ethanolPrice / gasPrice
            if (priceOfFuelIndice <= performanceOfMyCar) {
                suggestionResult.text = getString(R.string.ethanol)
            } else {
                suggestionResult.text = getString(R.string.gasoline)
            }
            ethanolCost.text = (ethanolPrice / ethanolAverage).format(2)
            gasolineCost.text = (ethanolPrice / ethanolAverage).format(2)
            fuelRatio.text = getString(R.string.label_fuel_ratio, performanceOfMyCar.format(2))
        }
    }
}
