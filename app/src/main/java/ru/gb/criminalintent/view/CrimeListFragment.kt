package ru.gb.criminalintent.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gb.criminalintent.R
import ru.gb.criminalintent.databinding.FragmentCrimeListBinding
import ru.gb.criminalintent.model.Crime
import ru.gb.criminalintent.utils.DEFAULT_VALUE_ONE
import ru.gb.criminalintent.utils.DEFAULT_VALUE_ZERO
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

    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {
        /**
         *  отвечает за создание представления
         *  на дисплее, оборачивает его в холдер и возвращает результат
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            var itemView: View? = null
            when (viewType) {
                DEFAULT_VALUE_ZERO -> {
                    itemView = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
                }
                DEFAULT_VALUE_ONE -> {
                    itemView = layoutInflater.inflate(R.layout.list_item_crime_hard, parent, false)
                }
            }
            //val view = ListItemCrimeBinding.inflate(LayoutInflater.from(parent.context))
            return CrimeHolder(itemView!!)
            //val itemView = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
//            return CrimeHolder(itemView)
        }

        override fun getItemViewType(position: Int): Int {
            return when (crimes[position].hard) {
                DEFAULT_VALUE_ZERO -> {
                    return DEFAULT_VALUE_ZERO
                }
                DEFAULT_VALUE_ONE -> {
                    return DEFAULT_VALUE_ONE
                }

                else -> {
                    DEFAULT_VALUE_ZERO
                }
            }
        }

        /**
         * Заполняет данный холдер преступлением про похиции
         */
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount() = crimes.size

    }

    private inner class CrimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val titleTextView: TextView = itemView.findViewById(R.id.crimeTitle)
        val dateTextView: TextView = itemView.findViewById(R.id.crimeDate)
        private lateinit var crime: Crime

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        fun newInstance() = CrimeListFragment()

    }
}