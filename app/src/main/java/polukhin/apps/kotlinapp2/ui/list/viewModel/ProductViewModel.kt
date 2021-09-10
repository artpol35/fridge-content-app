package polukhin.apps.fridgecontent.ui.ProductList.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import polukhin.apps.kotlinapp2.data.ProductRepository
import polukhin.apps.kotlinapp2.data.db.Product

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository = ProductRepository(application)
    private val allProductList: LiveData<List<Product>> = repository.getAllProductList()

    fun insertProduct(product: Product) {
        repository.insertProduct(product)
    }

    fun updateProduct(product: Product){
        repository.updateProduct(product)
    }

    fun deleteProduct(product: Product) {
        repository.deleteProduct(product)
    }

    fun getAllProductList(): LiveData<List<Product>> {
        return allProductList
    }

    fun getProductById(id: Long): LiveData<Product> {
        return repository.getProductById(id)
    }

}
