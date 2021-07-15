package com.astraube.materialcalendar;

/**
 * The callback used to indicate the user changes the displayed month
 */
public interface OnMonthChangedListener {

  /**
   * Called upon change of the selected day
   *
   * @param widget {@linkplain MaterialCalendarView} the view associated with this listener
   * @param date {@linkplain CalendarDay} the month picked, as the first day of the month
   */
  void onMonthChanged(MaterialCalendarView widget, CalendarDay date);
}
