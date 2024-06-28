package com.javiersalinas.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class NinjaGoldController {
	
	private List<String> historialMensajes = new ArrayList<>();
	
	// Este método calcula el oro que se dará, principalmente se hizo un método aparte para no repetir acciones dentro del código y entorpecer su desarrollo
	public int goldSetter(int tipoBoton) { 
		
		// Declararemos los máximos y mínimos
		int min = 0;
		int max = 0;
		
		// Según el tipo de botón que se clickee se establecerán los máximos y mínimos del otro que se dará
		if(tipoBoton == 0) {
			min = 10;
			max = 20;
		}
		else if (tipoBoton == 1) {
			min = 5;
			max = 10;
		}
		else if (tipoBoton == 2) {
			min = 2;
			max = 5;
		}
		else if (tipoBoton == 3) {
			min = -50;
			max = 50;
		}
		else if (tipoBoton == 4) {
			min = -20;
			max = -5;
		}
		
		// Obtenemos la cantidad de oro
		int goldAmount = ThreadLocalRandom.current().nextInt(min, max);
		
		// Se la entregamos al método principal
		return goldAmount;
	}

	// Para que la gente vea la página al entrar a la ruta
	@GetMapping("/Gold")
	public String desplegarHome() {
		return "index.jsp";
	}
	
	// Esto no fue pedido pero se incorporó igual, si el usuario entra a la url sin escribir nada más redigirá automáticamente al juego
	@GetMapping("")
	public String redirigirGold() {
		return "redirect:/Gold";
	}
	
	// El cuerpo y escencia de la página, entrará un parámetro cantidad que será en realidad el tipoBoton, gracias a esto sabremos qué botón se está presionando, y esto nos ayudará a poder implementar
	// una lógica personalizada para cada botón. El sesion nos ayudará a mantener un seguimiento del gold de la sesión. Model nos ayudará a poder devolver el historial de mensajes al usuario.
	@PostMapping("/Gold")
	public String actualizarGolden(@RequestParam("cantidad") int tipoBoton, HttpSession sesion, Model model) {
		Integer gold = (Integer) sesion.getAttribute("gold");
		
		java.util.Date date = new java.util.Date();
		// Definir el formato deseado
	    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd 'de' MMMM, yyyy", new Locale("es", "CL"));
	 	// Formatear la fecha
	    String fechaFormateada = sdf.format(date);
		
	    SimpleDateFormat sdh = new SimpleDateFormat("HH:mm a");
	 	// Formatear la fecha
	    String horaFormateada = sdh.format(date);
	    
	    // Botón de reinicio, implementado en las dos páginas (la cárcel y el juego)
	    if (tipoBoton == 5) {
			sesion.setAttribute("gold", 0);
			historialMensajes.clear();
			return "redirect:/Gold"; // Esta línea pudo haberse omitido pero fue para no hacer dos botones, si no estuviese el botón de la prisión no redirigiría al juego
		}
	    
	    // Una condicional por si la cantidad de oro es nula
		if(gold == null) {
			gold = 0;
		}
		
		// Si la deuda de oro supera los 100 va a redirigir a la prisión
		if (gold < -100) {
			return "redirect:/prison";
		}
		
		// Inicializa el mensaje
		String message = "";
		
		// Según el botón clickeado tendrá una acción distinta
		if(tipoBoton == 0) {
			int amount = goldSetter(tipoBoton); // Llamará al método "goldSetter" dándole el tipo del botón, esta nos retornará la cantidad de oro obtenida en un click, y lo almacenamos en amount
			sesion.setAttribute("gold", gold + amount); // Se sumará el oro obtenido con el click al oro total
			message = "<span style=color:green>"+"You entered a farm and earned "+ amount + " gold. (" + fechaFormateada + " " + horaFormateada + ")</span>"; // Mostrará un mensaje en verde con el contenido
		}
		else if (tipoBoton == 1) {
			int amount = goldSetter(tipoBoton);
			sesion.setAttribute("gold", gold + amount);
			message = "<span style=color:green>"+"You entered a cave and earned "+ amount + " gold. (" + fechaFormateada + " " + horaFormateada + ")</span>";
		}
		else if (tipoBoton == 2) {
			int amount = goldSetter(tipoBoton);
			sesion.setAttribute("gold", gold + amount);
			message = "<span style=color:green>"+"You entered a house and earned "+ amount + " gold. (" + fechaFormateada + " " + horaFormateada + ")</span>";
		}
		else if (tipoBoton == 3) { // Esta función puede tener valores positivos y negativos, por lo que necesitamos hacer una condicional según esto
			int amount = goldSetter(tipoBoton);
			sesion.setAttribute("gold", gold + amount);
			if (amount > 0) { // Si la cantidad de oro obtenida por el click es positiva entonces el mensaje será verde
				message = "<span style=color:green>"+"You entered a casino and earned "+ amount + " gold. (" + fechaFormateada + " " + horaFormateada + ")</span>";
			}
			else if (amount < 0){ // Si la cantidad de oro obtenida por el click es negativa entonces el mensaje será rojo
				message = "<span style=color:red>"+"You entered a casino and lost "+ amount + " gold. Ouch. (" + fechaFormateada + " " + horaFormateada + ")</span>";
			}
			else { // Si la cantida de oro es 0 entonces el mensaje será gris
				message = "<span style=color:gray>"+"You entered a casino and lost "+ amount + " gold. (" + fechaFormateada + " " + horaFormateada + ")</span>";
			}
			// NOTA: Se hicieron todos los span dentro porque si se hacen dentro del jsp mostrará toda la lista de un color u otro, al dejarlos declarados acá también se guardarán con el formato
			// en el historial, esto es súper beneficioso para cuando queramos hacer el for Each
		}
		else if (tipoBoton == 4) { // Si el botón es el SPA se mostará en rojo siempre porque siempre será pérdida
			int amount = goldSetter(tipoBoton);
			sesion.setAttribute("gold", gold + amount);
			message = "<span style=color:red text-align:left;>"+"You entered a spa and lost "+ amount + " gold. (" + fechaFormateada + " " + horaFormateada + ")</span>";
		}
		
		// Esta sección estaba en un método aparte y se sacó por optimización, recordemos que tenemos un List<String> en el que se tendrá el historial, lo que queremos rescatar de esto es que agregará
		// el mensaje dentro del historial de mensajes, lo agrega con el span incluido entonces cada elemento del string tendrá su propio color cuando se muestre en el historial, para no recaer en que
		// todo la lista sea de un solo color (o rojo o verde)
		historialMensajes.add(message);
		
		// Le agrega al atributo message el historial para que pueda ser mostrado en el jsp
		model.addAttribute("message", historialMensajes);
		
		return "index.jsp";
	}
	
	// Poder entrar a la prisión
	@GetMapping("/prison")
	public String desplegarPrison() {
		return "prison.jsp";
	}
	
}
