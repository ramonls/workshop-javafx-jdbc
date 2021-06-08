package gui.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Utils {

		public static Stage currentStage(ActionEvent event) {
			return (Stage) ((Node) event.getSource()).getScene().getWindow();
		}
		
		
		// m�todo para converter um string para integer, por�m se ocorrer uma exce��o na convers�o, vai retornar nulo.
		public static Integer tryParseToInt(String str) {
			try {
				return Integer.parseInt(str);
			}
			catch(NumberFormatException e) {
				return null;
			}
		}
		// m�todo para converter um string em double, se der alguma exce��o, retorna nulo
		public static Double tryParseToDouble(String str) {
			try {
				return Double.parseDouble(str);
			}
			catch(NumberFormatException e) {
				return null;
			}
		}
		
		// m�todo para formatar data
		public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
			tableColumn.setCellFactory(column -> {
				TableCell<T, Date> cell = new TableCell<T, Date>() {
					private SimpleDateFormat sdf = new SimpleDateFormat(format);
					
					@Override
					protected void updateItem(Date item, boolean empty) {
						super.updateItem(item, empty);
						if(empty) {
							setText(null);
						}
						else {
							setText(sdf.format(item));	
						}
					}
				};
				return cell;
			});
		}
		
		// m�todo para formatar campos doubles
		public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
			tableColumn.setCellFactory(column -> {
				TableCell<T, Double> cell = new TableCell<T, Double>() {
					
					@Override
					protected void updateItem(Double item, boolean empty) {
						super.updateItem(item, empty);
						if(empty) {
							setText(null);
						}
						else {
							Locale.setDefault(Locale.US);
							setText(String.format("%."+decimalPlaces+"f", item));
						}
					}
				};
				return cell;
			});
		}
		
		// m�todo para formatar a data dentro do date picker
		public static void formatDatePicker(DatePicker datePicker, String format) {
			datePicker.setConverter(new StringConverter<LocalDate>() {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
				{
					datePicker.setPromptText(format.toLowerCase());
				}
				
				@Override
				public String toString(LocalDate date) {
					if(date != null) {
						return dateFormatter.format(date);
					}
					else {
						return "";
					}
				}
				
				@Override
				public LocalDate fromString(String string) {
					if(string != null && string.isEmpty()) {
						return LocalDate.parse(string, dateFormatter);
					}
					else {
						return null;
					}
				}
			});
		}
}
