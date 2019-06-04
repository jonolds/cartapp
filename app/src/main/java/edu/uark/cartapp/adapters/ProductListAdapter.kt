package edu.uark.cartapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.Locale

import edu.uark.cartapp.R
import edu.uark.cartapp.models.api.Product

/* ==== APP ProductListAdapter.java ====*/
class ProductListAdapter(context: Context, products: List<Product>) : ArrayAdapter<Product>(context, R.layout.list_view_item_product, products) {

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
		var view = convertView
		if (view == null) {
			val inflater = LayoutInflater.from(this.context)
			view = inflater.inflate(R.layout.list_view_item_product, parent, false)
		}

		val product = this.getItem(position)
		if (product != null) {
			val lookupCodeTextView = view!!.findViewById<TextView>(R.id.list_view_item_product_lookup_code)
			if (lookupCodeTextView != null) {
				lookupCodeTextView.text = product.getLookupCode()
			}

			val countTextView = view.findViewById<TextView>(R.id.list_view_item_product_count)
			if (countTextView != null) {
				countTextView.text = String.format(Locale.getDefault(), "%d", product.getCount())
			}
		}
		return view
	}
}