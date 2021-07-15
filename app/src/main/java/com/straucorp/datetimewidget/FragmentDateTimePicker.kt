package com.straucorp.datetimewidget

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.astraube.materialcalendar.CalendarDay
import com.straucorp.datetimewidget.databinding.FragmentDateTimeBinding
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalDateTime
import java.util.*

/**
 * Created on 22/06/2021
 * @author André Straube
 */
class FragmentDateTimePicker : Fragment() {

    companion object {
        val FRAGMENT_ARG_TYPE_OF_FRAG = "typeOfFrag"
        val DATE_FORMATTER_24_HR = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
        var DATE_FORMATTER_12_HR = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm a")

        fun newDateTimePickerFragment(type: String): FragmentDateTimePicker {
            val myFragment = FragmentDateTimePicker()
            val args = Bundle()
            args.putString(FRAGMENT_ARG_TYPE_OF_FRAG, type)
            myFragment.arguments = args

            return myFragment
        }
    }

    var typeOfFragment = ""
    var selectedDate : Long = 0
    var selectedCalendarDate : CalendarDay? = null

    var hours = 0
    var minutes = 0
    var amPm = ""

    private var _binding: FragmentDateTimeBinding? = null
    private val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (arguments != null) {
            typeOfFragment = arguments?.getString(FRAGMENT_ARG_TYPE_OF_FRAG) ?: throw IllegalArgumentException("Type Of Fragment Required")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDateTimeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCalendarPicker()

        setDateToCurrentDateTime()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initCalendarPicker() {
        binding.calendarView.setOnDateChangedListener { widget, calendarDay, selected ->
            selectedCalendarDate = widget.selectedDate

            Toast.makeText(
                widget.context,
                "Year=${calendarDay.year} Month=${calendarDay.month} Day=${calendarDay.day}",
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    fun setDateToCurrentDateTime() {
        val date = DateTime()
        val localDateTime = LocalDateTime()

        selectedCalendarDate = CalendarDay.today()
        binding.calendarView.selectedDate = CalendarDay.today()

        minutes = localDateTime.minuteOfHour
        hours = localDateTime.hourOfDay
    }

    fun getDateTimeOfFragment() : DateTime {
        val calendarDate = selectedCalendarDate as CalendarDay
        val date = DateTime(calendarDate.year, calendarDate.month + 1, calendarDate.day, 0, 0)
        val date2 = Calendar.getInstance().apply {
            this.set(calendarDate.year, calendarDate.month + 1, calendarDate.day)
        }

        Log.d("FragmentDateTimePicker", "joda date: ${date}")
        Log.d("FragmentDateTimePicker", "Calendar date2: ${date2}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date3 = Calendar.Builder().setDate(calendarDate.year, calendarDate.month + 1, calendarDate.day)

            Log.d("FragmentDateTimePicker", "Calendar date3: ${date3}")
        }

        if (!DateFormat.is24HourFormat(context) && amPm == "AM") {
            if (hours == 12) {
                hours = binding.timerPickerView.hour
            }
        } else if (!DateFormat.is24HourFormat(context) && amPm == "PM") {
            if (hours != 12 ) {
                hours += 12
            }
        }

        hours = binding.timerPickerView.hour
        minutes = binding.timerPickerView.minute

        return date.plusHours(hours).plusMinutes(minutes)
    }
}