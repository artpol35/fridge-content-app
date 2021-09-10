package polukhin.apps.kotlinapp2.ui.list

import android.app.ActionBar
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import polukhin.apps.fridgecontent.ui.ProductList.viewModel.ProductViewModel
import polukhin.apps.fridgecontent.ui.ProductList.viewModel.ProductViewModelFactory
import polukhin.apps.kotlinapp2.R
import polukhin.apps.kotlinapp2.ui.MainActivity

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_list, container, false)

//        Change appbar title:
        (activity as MainActivity).title = getString(R.string.title_list_fragment)

        val productViewModelFactory = ProductViewModelFactory(requireActivity().application)
        val productViewModel =
            ViewModelProvider(this, productViewModelFactory).get(ProductViewModel::class.java)


        val recyclerView: RecyclerView = root.findViewById(R.id.productListRecyclerView)

//        Optional grid's raw count:
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, 3)
        }

        val productAdapter = ProductAdapter(productViewModel, findNavController())
        recyclerView.adapter = productAdapter

        productViewModel.getAllProductList().observe(viewLifecycleOwner, Observer {
            Log.d("DEBUG_1", "### All products in db ###")
            for (i in it) {
                Log.d("DEBUG_1", i.id.toString())
            }
            productAdapter.setData(it)
            productAdapter.notifyDataSetChanged()
        })

        val fab: FloatingActionButton = root.findViewById(R.id.productListFab)
        fab.setOnClickListener {
            Log.d("DEBUG_1", "Fab click -> navigate")
            findNavController().navigate(R.id.action_listFragment_to_detailFragment)
        }

        return root
    }

}