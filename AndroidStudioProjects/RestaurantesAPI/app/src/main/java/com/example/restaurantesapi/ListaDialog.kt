import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.example.restaurantesapi.R
import com.example.restaurantesapi.models.Plate

class ListaDialog(private val context: Context) {

    fun noHayNada() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Lista de Platos")
        builder.setMessage("No tiene ningún plato")
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }



    fun mostrarListaDialog(context: Context, listaPlatos: List<Plate>) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Lista de Platos")

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item_layout, null)
        builder.setView(view)

        val textViewItem = view.findViewById<TextView>(R.id.textViewItem)

        val stringBuilder = StringBuilder()
        for (plato in listaPlatos) {
            stringBuilder.append("Nombre: ${plato.name}\n")
            stringBuilder.append("Descripción: ${plato.description}\n")
            stringBuilder.append("Precio: ${plato.price}\n\n")
            stringBuilder.append("ID: ${plato.id}\n\n")
        }

        textViewItem.text = stringBuilder.toString()
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}