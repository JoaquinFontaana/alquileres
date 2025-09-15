package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.CheckOutAlquilerDTO;
import inge2.com.alquileres.backend.dto.CheckOutAlquilerPresencialDTO;
import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOFilter;
import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileres.backend.dto.alquiler.MultaAlquilerDTO;
import inge2.com.alquileres.backend.dto.auto.AutoDTOListar;
import inge2.com.alquileres.backend.model.enums.Extra;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AuthService;
import inge2.com.alquileres.backend.service.ClienteService;
import inge2.com.alquileres.backend.service.checkOut.CheckOutAlquilerService;
import inge2.com.alquileres.backend.service.useCase.Alquiler.ListarAlquileresUseCase;
import inge2.com.alquileres.backend.service.useCase.GestionAlquilerAutoUseCase;
import inge2.com.alquileres.backend.service.useCase.Alquiler.SugerirVehiculosSimilaresUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alquileres") @AllArgsConstructor
public class AlquilerController {

    private final AlquilerService alquilerService;
    private final GestionAlquilerAutoUseCase gestionAlquilerAutoUseCase;
    private final SugerirVehiculosSimilaresUseCase sugerirVehiculosSimilaresUseCase;
    private final ListarAlquileresUseCase listarAlquileresUseCase;
    private final CheckOutAlquilerService checkOutAlquilerService;
    private final AuthService authService;
    private final ClienteService clienteService;

    @PutMapping("/{id}/cancelado")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<String> cancelarReserva(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @PathVariable Long id){
        this.alquilerService.cancelarReserva(id);
        return ResponseEntity.ok("Reserva cancelada exitosamente");
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
    @PostMapping()
    @PreAuthorize("hasAuthority('CLIENT')")
    public String registrarAlquilerCliente(@Valid @RequestBody CheckOutAlquilerDTO checkOutAlquilerDTO){
        return this.checkOutAlquilerService.registrarAlquiler(checkOutAlquilerDTO,this.authService.getMailOfContext());
    }

    @PostMapping("/presencial")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public String registrarAlquilerPresencial(@Valid @RequestBody CheckOutAlquilerPresencialDTO checkOutAlquilerDTO)  {
        return this.checkOutAlquilerService.registrarAlquiler(checkOutAlquilerDTO,checkOutAlquilerDTO.getEmailCliente());
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLEADO')")
    public List<AlquilerDTOListar> listarAlquileres(@ModelAttribute AlquilerDTOFilter opcionesFiltrado){
        return this.listarAlquileresUseCase.listarAlquileres(opcionesFiltrado);
    }

    @GetMapping("/pendientes-retiro")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public List<AlquilerDTOListar> listarAlquileresPendientesRetiro(@Valid @RequestParam String sucursal){
        return this.listarAlquileresUseCase.listarPendientesEntrega(sucursal);
    }

    @GetMapping("/pendientes-devolucion")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public List<AlquilerDTOListar> listarAlquileresPendientesDevolucion(@Valid @RequestParam String sucursal){
        return this.listarAlquileresUseCase.listarPendientesDevolucion(sucursal);
    }

    @GetMapping("/extras")
    public List<Extra> getExtras(){
        return List.of(Extra.values());
    }

    @PostMapping("/{id}/entregado")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<String> registrarEntrega(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @PathVariable Long id){
        this.gestionAlquilerAutoUseCase.iniciarAlquiler(id);
        return ResponseEntity.ok("Alquiler iniciado exitosamente");
    }

    @PostMapping("/{id}/recibido-correcto")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<String> recibirAlquilerCorrecto(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @PathVariable Long id){
        this.gestionAlquilerAutoUseCase.finalizarAlquilerCorrecto(id);
        return ResponseEntity.ok("Alquiler recibido exitosamente");
    }

    @PostMapping("/recibido-multa")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<String> recibirAlquilerMulta(@Valid @RequestBody MultaAlquilerDTO multaAlquilerDTO){
        this.gestionAlquilerAutoUseCase.finalizarAlquilerMantenimiento(multaAlquilerDTO);
        return ResponseEntity.ok("Alquiler recibido exitosamente");
    }

    @GetMapping("{id}/similares")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public List<AutoDTOListar> sugerirSimilares(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @PathVariable Long id){
        return this.sugerirVehiculosSimilaresUseCase.sugerirSimilares(id);
    }

    @GetMapping("/cliente")
    @PreAuthorize( "hasAuthority('CLIENT')")
    public List<AlquilerDTOListar> listarAlquileres() {
        return this.clienteService.listarAlquileres(authService.getMailOfContext());
    }

}
