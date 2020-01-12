package com.hfad.bitsandpizzas


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.app.Fragment
import android.app.ListFragment

/**
 * A simple [Fragment] subclass.
 */
class PizzaFragment : ListFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            inflater.getContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.pizzas)
        )
        listAdapter = adapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
