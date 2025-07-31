package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOFilter;
import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileres.backend.dto.alquiler.MultaAlquilerDTO;
import inge2.com.alquileres.backend.dto.auto.AutoDTOListar;
import inge2.com.alquileres.backend.model.enums.Extra;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.useCase.Alquiler.ListarAlquileresUseCase;
import inge2.com.alquileres.backend.service.useCase.GestionAlquilerAutoUseCase;
import inge2.com.alquileres.backend.service.useCase.Alquiler.SugerirVehiculosSimilaresUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alquiler")
public class AlquilerController {

    private final AlquilerService alquilerService;
    private final GestionAlquilerAutoUseCase gestionAlquilerAutoUseCase;
    private final SugerirVehiculosSimilaresUseCase sugerirVehiculosSimilaresUseCase;
    private final ListarAlquileresUseCase listarAlquileresUseCase;

    public AlquilerController(AlquilerService alquilerService, GestionAlquilerAutoUseCase gestionAlquilerAutoUseCase, SugerirVehiculosSimilaresUseCase sugerirVehiculosSimilaresUseCase, ListarAlquileresUseCase listarAlquileresUseCase) {
        this.alquilerService = alquilerService;
        this.gestionAlquilerAutoUseCase = gestionAlquilerAutoUseCase;
        this.sugerirVehiculosSimilaresUseCase = sugerirVehiculosSimilaresUseCase;
        this.listarAlquileresUseCase = listarAlquileresUseCase;
    }

    @PostMapping("/cancelar")
    public ResponseEntity<String> cancelarReserva(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @RequestParam Long codigoAlquiler){
        this.alquilerService.cancelarReserva(codigoAlquiler);
        return ResponseEntity.ok("Reserva cancelada exitosamente");
    }

    @GetMapping("/listar")
    public List<AlquilerDTOListar> listarAlquileres(@ModelAttribute AlquilerDTOFilter opcionesFiltrado){
        return this.listarAlquileresUseCase.listarAlquileres(opcionesFiltrado);
    }

    @GetMapping("/empleado/get/pendientesRetiro")
    public List<AlquilerDTOListar> listarAlquileresPendientesRetiro(@Valid @RequestParam String sucursal){
        return this.listarAlquileresUseCase.listarPendientesEntrega(sucursal);
    }

    @GetMapping("/empleado/get/pendientesDevolucion")
    public List<AlquilerDTOListar> listarAlquileresPendientesDevolucion(@Valid @RequestParam String sucursal){
        return this.listarAlquileresUseCase.listarPendientesDevolucion(sucursal);
    }

    @GetMapping("/get/extras")
    public List<Extra> getExtras(){
        return List.of(Extra.values());
    }

    @PostMapping("/empleado/entregarAlquiler")
    public ResponseEntity<String> entregarAlquiler(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @RequestParam Long codigoAlquiler){
        this.gestionAlquilerAutoUseCase.iniciarAlquiler(codigoAlquiler);
        return ResponseEntity.ok("Alquiler iniciado exitosamente");
    }

    @PostMapping("/empleado/recibirAlquiler/correcto")
    public ResponseEntity<String> recibirAlquilerCorrecto(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @RequestParam Long codigoAlquiler){
        this.gestionAlquilerAutoUseCase.finalizarAlquilerCorrecto(codigoAlquiler);
        return ResponseEntity.ok("Alquiler recibido exitosamente");
    }
    @PostMapping("/empleado/recibirAlquiler/multa")
    public ResponseEntity<String> recibirAlquilerMulta(@Valid @RequestBody MultaAlquilerDTO multaAlquilerDTO){
        this.gestionAlquilerAutoUseCase.finalizarAlquilerMantenimiento(multaAlquilerDTO);
        return ResponseEntity.ok("Alquiler recibido exitosamente");
    }

    @GetMapping("/empleado/get/similares")
    public List<AutoDTOListar> sugerirSimilares(@Valid @NotNull(message = "El codigo del alquiler es obligatorio") @RequestParam Long codigoAlquiler){
        return this.sugerirVehiculosSimilaresUseCase.sugerirSimilares(codigoAlquiler);
    }

}
