package net.orfdev.integration.test.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NavigatorDateUtil {

	private final static DateTimeFormatter SLASH_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final static DateTimeFormatter SPACE_FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy");
	private final static DateTimeFormatter LONG_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");
	private final static DateTimeFormatter LONGER_FORMATTER = DateTimeFormatter.ofPattern("EEE d MMMM  hh:mm");
	
	public static String convertDurationToDateString(int duration, LocalDate sourceDate) {
		LocalDate source = sourceDate.plusDays(duration - 1);
		return convertDateToDateString(source);
	}
	
	public static String convertDateToDateString(LocalDate source) {
		return source.format(SLASH_FORMATTER);
	}
	
	public static String convertDateSlashToSpace(String dateString) {
		LocalDate parsed = LocalDate.from(SLASH_FORMATTER.parse(dateString));
		return parsed.format(SPACE_FORMATTER);
	}
	
	public static String convertDateStringToLongDateString(String dateString) {
		LocalDate parsed = LocalDate.from(SLASH_FORMATTER.parse(dateString));
		return parsed.format(LONG_FORMATTER);
	}
	
	public static String toLongFormat(LocalDate ld) {
		return ld.format(LONG_FORMATTER);
	}
	
	public static String toShortFormat(LocalDate ld) {
		return ld.format(SLASH_FORMATTER);
	}
	
	public static LocalDate fromShortFormat(String value) {
		return LocalDate.from(SLASH_FORMATTER.parse(value));
	}
	
	public static String toLongerFormat(LocalDateTime ldt) {
		return ldt.format(LONGER_FORMATTER);
	}
	
//	public static String getReturnDateInIntegerFormats(int duration, LocalDate ld) {		
//		String dateString = convertDurationToDateString(duration, ld);
//		return convertDateSlashToSpace(dateString);
//	}
	
	private static LocalDate convertDurationToDate(int duration, LocalDate startDate) {
		return startDate.plusDays(duration - 1);
	}
	
	public static String convertDurationToLongDate(int duration, LocalDate startDate) {
		return toLongFormat(convertDurationToDate(duration, startDate));
	}
}
