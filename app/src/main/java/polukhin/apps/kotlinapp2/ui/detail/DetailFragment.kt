package polukhin.apps.kotlinapp2.ui.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import polukhin.apps.fridgecontent.ui.ProductList.viewModel.ProductViewModel
import polukhin.apps.fridgecontent.ui.ProductList.viewModel.ProductViewModelFactory
import polukhin.apps.kotlinapp2.R
import polukhin.apps.kotlinapp2.data.db.Product
import polukhin.apps.kotlinapp2.ui.MainActivity

class DetailFragment : Fragment() {

    private var name: String? = ""
    private var weight: Float? = 0f
    private var priority: Int? = 1
    private var shelf: Int? = 1
    private var updateMode: Boolean = false
    private lateinit var currentProduct: Product

    // change test


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root: View = inflater.inflate(R.layout.fragment_detail, container, false)

//        Check arguments from bundle transfer data:
        if (arguments?.isEmpty == false) {
            Log.d("DEBUG_2", "Update product mode")
            updateMode = true
//            Change appbar title:
            (activity as MainActivity).title = getString(R.string.title_detail_update_fragment)
            currentProduct = arguments?.getParcelable("product")!!

//            Input UI data:
            prepopulateUI(
                root, currentProduct.name, currentProduct.weight,
                currentProduct.priority, currentProduct.shelfPosition
            )

        } else {
            updateMode = false
//            Change appbar title:
            (activity as MainActivity).title = getString(R.string.title_detail_new_product_fragment)
            Log.d("DEBUG_2", "New product mode")
        }

        val productViewModelFactory = ProductViewModelFactory(requireActivity().application)
        val productViewModel =
            ViewModelProvider(this, productViewModelFactory).get(ProductViewModel::class.java)

        val buttonSave: Button = root.findViewById(R.id.detailedActivityButton)
        buttonSave.setOnClickListener {

//            Checkout data correct format:
            if (checkDataFromUI(root)) {
                try {

                    if (updateMode) {
                        Log.d("DEBUG_2", "Update mode: button press")
                        Log.d("DEBUG_2", "Bundle data: $currentProduct")
                        Log.d("DEBUG_2", "Ui data: $name, $weight, $priority, $shelf")

                        currentProduct.name = this.name!!
                        currentProduct.weight = this.weight!!
                        currentProduct.priority = this.priority!!
                        currentProduct.shelfPosition = this.shelf!!

                        productViewModel.updateProduct(currentProduct)

                    } else {
                        Log.d("DEBUG_2", "New product mode: button press")
                        productViewModel.insertProduct(
                            Product(
                                name!!,
                                weight!!,
                                priority!!,
                                shelf!!
                            )
                        )
                    }

//                    Hide soft keyboard"
                    val imm =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(root.windowToken, 0)

                    findNavController().popBackStack(R.id.listFragment, false)
                } catch (e: Exception) {
                    Log.d("DEBUG_2", "Error update/insert product")
                }
            } else {
                Log.d("DEBUG_2", "Wrong data from UI")
                Toast.makeText(root.context, "Incorrect data", Toast.LENGTH_SHORT).show()
            }
        }

//        Handle back button press:
//        requireActivity().onBackPressedDispatcher.addCallback {
//            findNavController().popBackStack(R.id.listFragment, false)
//        }

        return root
    }


    private fun checkDataFromUI(root: View): Boolean {

        val nameView: EditText = root.findViewById(R.id.detailedActivityName)
        val weightView: EditText = root.findViewById(R.id.detailedActivityWeight)
        val priorityView: Spinner = root.findViewById(R.id.detailedSpinnerPriority)
        val shelfView: Spinner = root.findViewById(R.id.detailedSpinnerShelf)

        try {
            name = nameView.text.toString()
            weight = weightView.text.toString().toFloat()
            priority = priorityView.selectedItem.toString().toInt()
            shelf = shelfView.selectedItem.toString().toInt()

            Log.d("DEBUG_2", "fun CheckDataFromUI: Success")

        } catch (e: Exception) {
            Log.d("DEBUG_2", "fun CheckDataFromUI: Error")
        }

        return !(name!!.isEmpty() || weight == 0f)
    }

    private fun prepopulateUI(root: View, name: String, weight: Float, priority: Int, shelf: Int) {

        val nameView: EditText = root.findViewById(R.id.detailedActivityName)
        val weightView: EditText = root.findViewById(R.id.detailedActivityWeight)
        val priorityView: Spinner = root.findViewById(R.id.detailedSpinnerPriority)
        val shelfView: Spinner = root.findViewById(R.id.detailedSpinnerShelf)

        nameView.setText(name)
        weightView.setText(weight.toInt().toString())
        priorityView.setSelection(priority - 1)
        shelfView.setSelection(shelf - 1)

        Log.d("DEBUG_2", "Prepopulate success")
    }
}
