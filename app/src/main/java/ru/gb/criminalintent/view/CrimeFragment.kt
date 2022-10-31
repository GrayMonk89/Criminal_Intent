package ru.gb.criminalintent.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.criminalintent.databinding.FragmentCrimeBinding
import ru.gb.criminalintent.model.Crime

class CrimeFragment : Fragment() {

    private var _binding: FragmentCrimeBinding? = null
    private val binding: FragmentCrimeBinding
        get() = _binding!!

    private lateinit var crime: Crime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
    }

    override fun onStart() {
        super.onStart()

        setListenerOnStart()


    }

    /**
     *  Слушатель типа TextWatcher срабатывает не только при прямом взаимодействие
     * с EditText польсователем, но и при изменении состояния виджета. По этой причини
     * этот тип слушателя инициализируеся в onStart() а не в onCreate()
     */
    private fun setListenerOnStart() {
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                crime.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {

            }
        }
        binding.editTextCrimeTitle.addTextChangedListener(titleWatcher)

        binding.checkBoxCrimeSolved.apply {
            crime.isSolved = isChecked
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun btnDataSet() {
        binding.buttonCrimeDate.apply {
            //Задаем текст для кнопки и делаем ее не активной
            text = crime.date.toString()
            isEnabled = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnDataSet()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = CrimeFragment()
    }
}