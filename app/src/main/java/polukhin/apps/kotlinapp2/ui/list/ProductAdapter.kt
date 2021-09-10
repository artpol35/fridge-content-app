package polukhin.apps.kotlinapp2.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import polukhin.apps.fridgecontent.ui.ProductList.viewModel.ProductViewModel
import polukhin.apps.kotlinapp2.R
import polukhin.apps.kotlinapp2.data.db.Product

class ProductAdapter(productViewModel: ProductViewModel, navController: NavController) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var productList: List<Product> = arrayListOf()
    private var productViewModel: ProductViewModel? = null
    private var navController: NavController? = null

    init {
        this.productViewModel = productViewModel
        this.navController = navController
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct: Product = productList[position]
        holder.name?.text = currentProduct.name
        holder.weight?.text = currentProduct.weight.toString()
        holder.priority?.text = currentProduct.priority.toString()
        holder.shelfPosition?.text = currentProduct.shelfPosition.toString()

        holder.imageInfo?.setOnClickListener {
//            Create popup menu:
            val popUpMenu = PopupMenu(it.context, it)
            popUpMenu.inflate(R.menu.popup_menu)
            popUpMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

//                Handler for image click:
                when (item!!.itemId) {
                    R.id.popUpEdit -> {
                        val bundle = Bundle()
                        bundle.putParcelable("product", currentProduct)
                        Log.d("DEBUG_2", "Edit product: $currentProduct")
                        navController?.navigate(R.id.action_listFragment_to_detailFragment, bundle)
                    }
                    R.id.popUpDelete -> {
                        Log.d("DEBUG_2", "Delete product: $currentProduct")
                        productViewModel?.deleteProduct(currentProduct)
                    }
                }

                true
            })

            popUpMenu.show()
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = null
        var weight: TextView? = null
        var priority: TextView? = null
        var shelfPosition: TextView? = null
        var imageInfo: ImageView? = null

        init {
            name = itemView.findViewById(R.id.productItemTextViewName)
            weight = itemView.findViewById(R.id.productItemTextViewWeight)
            priority = itemView.findViewById(R.id.productItemTextViewPriority)
            shelfPosition = itemView.findViewById(R.id.productItemTextViewShelfPosition)
            imageInfo = itemView.findViewById(R.id.productItemTextViewImageInfo)
        }

    }

    fun setData(data: List<Product>) {
        productList = data
    }
}
