package com.straucorp.datetimewidget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.startup.AppInitializer
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.astraube.materialcalendar.CalendarDay
import com.astraube.materialcalendar.MaterialCalendarView
import com.astraube.materialcalendar.TimePickerView
import com.straucorp.datetimewidget.databinding.ActivityMainBinding
import com.straucorp.datetimewidget.widget.pager.SelectTimeFragmentPagerAdapter
import com.straucorp.datetimewidget.widget.pager.PagerSlidingTabStrip
import net.danlew.android.joda.JodaTimeInitializer

class MainActivity : AppCompatActivity() {

    val binding by viewBinding(ActivityMainBinding::inflate)

    lateinit var listFragments: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppInitializer.getInstance(this).initializeComponent(JodaTimeInitializer::class.java)

        listFragments = initViewPager(binding.viewTabs, binding.viewPager)

        binding.btnDialogStartDate.setOnClickListener {
            showDialogStartDate()
        }

        binding.btnDialogFinalDate.setOnClickListener {
            showDialogFinalDate()
        }

        binding.btnDialogTabsDate.setOnClickListener {
            showDialogTabsDate()
        }

        binding.btnDone.setOnClickListener {
            setSaveButton()
        }
    }

    fun showDialogTabsDate() {
        MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).apply {
            title(text = "Data inicial e final")
            customView(R.layout.content_date_time_pager, scrollable = false)
            onShow { dialog ->
                // Setup custom view content
                val customView = dialog.getCustomView()

                val tabsView = customView.findViewById<PagerSlidingTabStrip>(R.id.viewTabs)
                val timerPickerView = customView.findViewById<ViewPager2>(R.id.viewPager)

                initViewPager(tabsView, timerPickerView)

            }
            positiveButton(text = "ok") { dialog ->
                // Setup custom view content
                val customView = dialog.getCustomView()


            }
        }.also {
            it.show()
        }
    }

    fun showDialogStartDate() {
        MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).apply {
            title(text = "Data Inicial")
            customView(R.layout.fragment_date_time, scrollable = false)
            onShow { dialog ->
                // Setup custom view content
                val customView = dialog.getCustomView()

                val timerPickerView = customView.findViewById<TimePickerView>(R.id.timerPickerView)
                val calendarView = customView.findViewById<MaterialCalendarView>(R.id.calendarView)

                calendarView.selectedDate = CalendarDay.today()
            }
            positiveButton(text = "ok") { dialog ->
                // Setup custom view content
                val customView = dialog.getCustomView()

                val timerPickerView = customView.findViewById<TimePickerView>(R.id.timerPickerView)
                val calendarView = customView.findViewById<MaterialCalendarView>(R.id.calendarView)


            }
        }.also {
            it.show()
        }
    }

    fun showDialogFinalDate() {
        MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).apply {
            title(text = "Data Final")
            customView(R.layout.fragment_date_time, scrollable = false)
            onShow { dialog ->
                // Setup custom view content
                val customView = dialog.getCustomView()

                val timerPickerView = customView.findViewById<TimePickerView>(R.id.timerPickerView)
                val calendarView = customView.findViewById<MaterialCalendarView>(R.id.calendarView)

                calendarView.selectedDate = CalendarDay.today()
            }
            positiveButton(text = "ok") { dialog ->
                // Setup custom view content
                val customView = dialog.getCustomView()

                val timerPickerView = customView.findViewById<TimePickerView>(R.id.timerPickerView)
                val calendarView = customView.findViewById<MaterialCalendarView>(R.id.calendarView)
            }
        }.also {
            it.show()
        }
    }

    fun setViewPagerTab(pager: ViewPager2, int : Int) {
        pager.currentItem = int
    }

    fun initViewPager(tabs: PagerSlidingTabStrip, pager: ViewPager2) : List<String> {
        val listTabs = listOf(getString(R.string.period_tab_title_start), getString(R.string.period_tab_title_end))

        val adapter = SelectTimeFragmentPagerAdapter(
            listItems = listTabs,
            fragmentManager = this,
            creatorFragment = FragmentDateTimePicker::newDateTimePickerFragment
        )

        pager.adapter = adapter
        pager.currentItem = 0
        pager.offscreenPageLimit = adapter.itemCount

        tabs.setViewPager(pager)

        return listTabs
    }

    fun setSaveButton() {
        val fragmentFrom = supportFragmentManager.fragments[0] as FragmentDateTimePicker
        val fragmentTo = supportFragmentManager.fragments[1] as FragmentDateTimePicker
        val dateTimeFrom = fragmentFrom.getDateTimeOfFragment()
        val dateTimeTo = fragmentTo.getDateTimeOfFragment()

        Toast.makeText(this, "${dateTimeFrom.toDate()}",Toast.LENGTH_LONG).show()

        var dialogTitle = ""
        var dialogMessage = ""
        val dialogNeutralButton = resources.getString(R.string.ok)

        if (dateTimeFrom.isAfter(dateTimeTo)) {
            dialogTitle = resources.getString(R.string.period_invalid)
            dialogMessage = resources.getString(R.string.period_from_is_after_to)
        } else {
            var stringDateFrom = ""
            var stringDateTo = ""

            if (!android.text.format.DateFormat.is24HourFormat(this)) {
                stringDateFrom = dateTimeFrom.toString(FragmentDateTimePicker.DATE_FORMATTER_12_HR)
                stringDateTo = dateTimeTo.toString(FragmentDateTimePicker.DATE_FORMATTER_12_HR)
            } else {
                stringDateFrom = dateTimeFrom.toString(FragmentDateTimePicker.DATE_FORMATTER_24_HR)
                stringDateTo = dateTimeTo.toString(FragmentDateTimePicker.DATE_FORMATTER_24_HR)
            }

            dialogTitle = resources.getString(R.string.period_selected)
            dialogMessage = resources.getString(R.string.period_start_end, stringDateFrom, stringDateTo)
        }

        AlertDialog.Builder(this@MainActivity)
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setNeutralButton(dialogNeutralButton) { dialog, id ->
                dialog.dismiss()
            }
            .show()
    }

}