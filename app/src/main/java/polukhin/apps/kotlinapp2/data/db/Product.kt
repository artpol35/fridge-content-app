package polukhin.apps.kotlinapp2.data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Products")
data class Product(

    var name: String,
    var weight: Float,
    var priority: Int,
    @ColumnInfo(name = "shelf_position")
    var shelfPosition: Int
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}