package com.pbj.sdk.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.pbj.sdk.R
import com.pbj.sdk.domain.product.model.Product

class ProductAdapter(private val onClickListener: OnProductClickListener) :
    RecyclerView.Adapter<ProductVH>() {

    interface OnProductClickListener {
        fun onClickProduct(product: Product)
    }

    private var productList: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_card_layout, parent, false)
        return ProductVH(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.bind(productList[position])
    }

    fun update(products: List<Product>) {
        productList = products
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = productList.count()
}

class ProductVH(val view: View, private val listener: ProductAdapter.OnProductClickListener) : RecyclerView.ViewHolder(view) {

    private var title: AppCompatTextView = view.findViewById(R.id.productTitle)
    private var price: AppCompatTextView = view.findViewById(R.id.productPrice)
    private var description: AppCompatTextView = view.findViewById(R.id.productDescription)
    private var image: ShapeableImageView = view.findViewById(R.id.productImage)

    fun bind(product: Product) {
        title.text = product.title
        price.text = "$${product.price}"
        description.text = product.detail?.trim()
        image.load(product.image)

        view.setOnClickListener {
            listener.onClickProduct(product)
        }
    }
}