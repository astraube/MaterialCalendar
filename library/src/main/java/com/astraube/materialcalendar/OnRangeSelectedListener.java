package com.astraube.materialcalendar;

import androidx.annotation.NonNull;
import java.util.List;

/**
 * The callback used to indicate a range has been selected
 */
public interface OnRangeSelectedListener {

  /**
   * Called when a user selects a range of days.
   * There is no logic to prevent multiple calls for the same date and state.
   *
   * @param widget {@linkplain MaterialCalendarView} the view associated with this listener
   * @param dates {@linkplain List<CalendarDay>} the dates in the range, in ascending order
   */
  void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates);
}
