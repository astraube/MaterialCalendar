package com.astraube.materialcalendar.format;

import org.threeten.bp.DayOfWeek;
import com.astraube.materialcalendar.MaterialCalendarView;

/**
 * Supply labels for a given day of the week.
 */
public interface WeekDayFormatter {
  /**
   * Convert a given day of the week into a label.
   *
   * @param dayOfWeek the day of the week as returned by {@linkplain DayOfWeek#getValue()}.
   * @return {@linkplain CharSequence} a label for the day of week.
   */
  CharSequence format(DayOfWeek dayOfWeek);

  /**
   * Default implementation used by {@linkplain MaterialCalendarView}
   */
  WeekDayFormatter DEFAULT = new CalendarWeekDayFormatter();
}
