package polukhin.apps.kotlinapp2.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import polukhin.apps.kotlinapp2.data.db.Product
import polukhin.apps.kotlinapp2.data.db.ProductDAO
import polukhin.apps.kotlinapp2.data.db.ProductDatabase

class ProductRepository(application: Application) {

    private val productDao: ProductDAO
    private val allProducts: LiveData<List<Product>>

    init {
        val database = ProductDatabase.getInstance(application)
        productDao = database!!.productDao()
        allProducts = productDao.getAllProducts()
    }

    fun insertProduct(product: Product) = runBlocking {
        this.launch(Dispatchers.IO) {
            productDao.insertProduct(product)
        }
    }

    fun updateProduct(product: Product) = runBlocking {
        this.launch(Dispatchers.IO) {
            productDao.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                productDao.deleteProduct(product)
            }
        }
    }

    fun getAllProductList(): LiveData<List<Product>> {
        return allProducts
    }

    fun getProductById(id: Long): LiveData<Product> {
        return productDao.getProductById(id)
    }
}