import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class conversor {

    public static void main(String[] args) {

        boolean continuarPrograma = true;
        DecimalFormat formatoDecimal = new DecimalFormat("#.##");

        while (continuarPrograma) {

            String[] opciones = { "Pesos (Ars) a Euros", "Pesos (Ars) a Dólares", "Pesos (Ars) a Libras Esterlinas", "Pesos (Ars) a Yen",
                    "Pesos a Won" };
            String eleccion = (String) JOptionPane.showInputDialog(null, "Elija una opción:",
                    "Conversor de Moneda", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            String entrada = JOptionPane.showInputDialog("Ingrese la cantidad en  Pesos Argentinos: ");
            double pesos;
            try {
                pesos = Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor no válido.");
                return;
            }

            double tasaDeCambio = obtenerTasaDeCambio();

            double resultado = convertirMoneda(pesos, tasaDeCambio);

            JOptionPane.showMessageDialog(null,
                    pesos + " pesos argentinos son aproximadamente " + formatoDecimal.format(resultado) + " "
                            + eleccion.split(" ")[2]);

            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea continuar usando el programa?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (confirmar != JOptionPane.YES_OPTION) {
                continuarPrograma = false;
                JOptionPane.showMessageDialog(null, "Programa Finalizado");
            }
        }

    }

    public static double obtenerTasaDeCambio() {
        try {
            String apiKey = "88da59ba86106cbe4e3f482b"; // clave API
            URL url = new URL("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/ARS");
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                sb.append(scanner.next());
            }
            scanner.close();
            JSONObject json = new JSONObject(sb.toString());
            return json.getJSONObject("conversion_rates").getDouble("USD");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la tasa de cambio.");
            return 0.0;
        }
    }

    public static double convertirMoneda(double cantidad, double tasaDeCambio) {
        return cantidad * tasaDeCambio;
    }
}
