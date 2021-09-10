package polukhin.apps.kotlinapp2.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDAO {
    @Query("SELECT * FROM Products")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM Products WHERE id =:id")
    fun getProductById(id: Long): LiveData<Product>

    @Insert
    fun insertProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Update
    fun updateProduct(product: Product)
}