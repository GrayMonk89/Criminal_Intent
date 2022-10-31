package ru.gb.criminalintent.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gb.criminalintent.R
import ru.gb.criminalintent.databinding.FragmentCrimeListBinding
import ru.gb.criminalintent.databinding.ListItemCrimeBinding
import ru.gb.criminalintent.model.Crime
import ru.gb.criminalintent.utils.TAG_CRIME_LIST_FRAGMENT
import ru.gb.criminalintent.viewmodel.CrimeListViewModel

class CrimeListFragment : Fragment() {

    private var _binding: FragmentCrimeListBinding? = null
    private val binding: FragmentCrimeListBinding
        get() = _binding!!

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_CRIME_LIST_FRAGMENT, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateUI() {
        //binding.crimeRecyclerView.adapter = CrimeAdapter(crimeListViewModel.crimes)
        crimeRecyclerView =
            view?.findViewById(R.id.crimeRecyclerView) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy(
        )
    }

    private inner class CrimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.crimeTitle)
        val dateTextView: TextView = itemView.findViewById(R.id.crimeDate)
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {
        /**
         *  отвечает за создание представления
         *  на дисплее, оборачивает его в холдер и возвращает результат
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = ListItemCrimeBinding.inflate(LayoutInflater.from(parent.context))
            return CrimeHolder(view.root)
//            val itemView = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
//            return CrimeHolder(itemView)
        }

        /**
         * Заполняет данный холдер преступлением про похиции
         */
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.apply {
                titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()
            }
        }

        override fun getItemCount() = crimes.size

    }

    companion object {
        fun newInstance() = CrimeListFragment()

    }
}