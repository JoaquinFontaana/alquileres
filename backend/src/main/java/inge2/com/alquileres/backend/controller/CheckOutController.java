package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.CheckOutAlquilerDTO;
import inge2.com.alquileres.backend.dto.CheckOutAlquilerPresencialDTO;
import inge2.com.alquileres.backend.dto.DatosPagoDTO;
import inge2.com.alquileres.backend.service.checkOut.CheckOutAlquilerService;
import inge2.com.alquileres.backend.service.checkOut.CheckOutMultaService;
import inge2.com.alquileres.backend.service.helper.AuthHelperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkOut")
public class CheckOutController {

    private final CheckOutAlquilerService checkOutAlquilerService;
    private final CheckOutMultaService checkOutMultaService;
    private final AuthHelperService authHelperService;
    @Autowired
    public CheckOutController(CheckOutAlquilerService checkOutAlquilerService, CheckOutMultaService checkOutMultaService, AuthHelperService authHelperService) {
        this.checkOutAlquilerService = checkOutAlquilerService;
        this.checkOutMultaService = checkOutMultaService;
        this.authHelperService = authHelperService;
    }

    /*
        ** Ejemplo de peticion
        *  {
        "datosPagoDTO": {
          "titulo": "Reserva de Auto - 5 días",
          "successUrl": "https://miapp.com/pago/exito",
          "failureUrl": "https://miapp.com/pago/fallo",
          "pendingUrl": "https://miapp.com/pago/pendiente"
        },
        "alquilerDTO": {
          "rangoFecha": {
            "fechaDesde": "2025-06-10",
            "fechaHasta": "2025-06-15"
          },
          "licenciaConductor": "ABC123456",
          "patenteAuto": "ADF111",
          "sucursal": "Trelew",
        }
     }   */
    @PostMapping("/cliente/registrarAlquiler")
    public String registrarAlquilerCliente(@Valid @RequestBody CheckOutAlquilerDTO checkOutAlquilerDTO){
        return this.checkOutAlquilerService.registrarAlquiler(checkOutAlquilerDTO,this.authHelperService.getMailOfContext());
    }


    @PostMapping("/cliente/pagarMulta")
    public String pagarMulta(@Valid @RequestBody DatosPagoDTO datosPagoDTO) {
        return this.checkOutMultaService.pagarMulta(datosPagoDTO);
    }


    @PostMapping("/empleado/registrarAlquiler")
    public String registrarAlquilerEmpleado(@Valid @RequestBody CheckOutAlquilerPresencialDTO checkOutAlquilerDTO)  {
        return this.checkOutAlquilerService.registrarAlquiler(checkOutAlquilerDTO,checkOutAlquilerDTO.getMailCliente());
    }

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
