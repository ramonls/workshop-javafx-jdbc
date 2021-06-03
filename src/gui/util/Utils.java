package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

		public static Stage currentStage(ActionEvent event) {
			return (Stage) ((Node) event.getSource()).getScene().getWindow();
		}
		
		
		// método para converter um string para integer, porém se ocorrer uma exceção na conversão, vai retornar nulo.
		public static Integer tryParseToInt(String str) {
			try {
				return Integer.parseInt(str);
			}
			catch(NumberFormatException e) {
				return null;
			}
		}
}
