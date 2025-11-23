package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.service.checkOut.CheckOutAlquilerService;
import inge2.com.alquileres.backend.service.checkOut.CheckOutMultaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @AllArgsConstructor
@RequestMapping("/checkOut")
public class CheckOutController {

    private final CheckOutAlquilerService checkOutAlquilerService;
    private final CheckOutMultaService checkOutMultaService;

    //Aca se recibiran las notificaciones de mercadopago referidas a los alquileres
    @PostMapping("/notificacion/alquiler")
    public ResponseEntity<String> recibirNotificacionAlquiler(@RequestParam("type") String type, @RequestParam("data.id") String dataId) {
        checkOutAlquilerService.procesarNotificacion(dataId,type);
        // Responder 200 OK para que MP no reintente
        return ResponseEntity.ok("OK");
    }

    //Aca se recibiran las notificaciones de mercadopago referidas a las multas
    @PostMapping("/notificacion/multa")
    public ResponseEntity<String> recibirNotificacionMulta(@RequestParam("type") String type, @RequestParam("data.id") String dataId)  {
            checkOutMultaService.procesarNotificacion(dataId,type);
        // Responder 200 OK para que MP no reintente
        return ResponseEntity.ok("OK");
    }
}
