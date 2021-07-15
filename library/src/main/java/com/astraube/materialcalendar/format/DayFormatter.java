package com.astraube.materialcalendar.format;

import androidx.annotation.NonNull;
import com.astraube.materialcalendar.CalendarDay;
import com.astraube.materialcalendar.MaterialCalendarView;
import java.text.SimpleDateFormat;

/**
 * Supply labels for a given day. Default implementation is to format using a {@linkplain SimpleDateFormat}
 */
public interface DayFormatter {

  /**
   * Default format for displaying the day.
   */
  String DEFAULT_FORMAT = "d";

  /**
   * Default implementation used by {@linkplain MaterialCalendarView}
   */
  DayFormatter DEFAULT = new DateFormatDayFormatter();

  /**
   * Format a given day into a string
   *
   * @param day the day
   * @return {@linkplain String} a label for the day
   */
  @NonNull String format(@NonNull CalendarDay day);
}
