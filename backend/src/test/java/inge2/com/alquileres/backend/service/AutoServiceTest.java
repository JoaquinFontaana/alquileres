package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.dto.auto.AutoDTOCrear;
import inge2.com.alquileres.backend.mapper.AutoMapper;
import inge2.com.alquileres.backend.mapper.AutoMapperImpl;
import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.Sucursal;
import inge2.com.alquileres.backend.model.enums.CategoriaAuto;
import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import inge2.com.alquileres.backend.model.enums.TiposRembolso;
import inge2.com.alquileres.backend.repository.IAutoRepository;
import inge2.com.alquileres.backend.service.helper.AutoHelperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutoServiceTest {
    @Mock
    private IAutoRepository autoRepository;
    @Mock
    private SucursalService sucursalService;
    @Mock
    private AutoHelperService autoHelperService;
    @Mock
    private FileStorageService fileStorageService;

    private final AutoMapper autoMapper = new AutoMapperImpl();

    @InjectMocks
    private AutoService autoService;

    private AutoDTOCrear dto;
    private Sucursal sucursalMock;
    private MultipartFile imagenMock;

    private final String PATENTE = "123ABC";
    private final String CIUDAD = "La plata";

    @BeforeEach
    public void setUp(){
        autoService = new AutoService(autoRepository,sucursalService,autoHelperService,fileStorageService,autoMapper);
        imagenMock = mock(MultipartFile.class);

        sucursalMock = new Sucursal();
        sucursalMock.setCiudad(CIUDAD);

        dto = new AutoDTOCrear();
        dto.setImagen(imagenMock);
        dto.setPatente(PATENTE);
        dto.setCapacidad(4);
        dto.setMarca("Ford");
        dto.setModelo("Raptor");
        dto.setPrecioPorDia(5000);
        dto.setSucursal(CIUDAD);
        dto.setRembolso(TiposRembolso.SIN_REMBOLSO);
        dto.setCategoria(CategoriaAuto.SUV);
    }

    @Test
    @DisplayName("Crear auto: Debe guardar el auto correctamente cuando todos los datos son válidos")
    void crearAuto_Success(){
        //Defino comportamiento
        when(sucursalService.findSucursalByCiudad(CIUDAD)).thenReturn(sucursalMock);
        when(fileStorageService.guardarImagen(imagenMock)).thenReturn("ruta/valida/imagen.jpg");

        //ACT
        autoService.crearAuto(dto);
        //Assert
        verify(fileStorageService).checkImagen(imagenMock);
        verify(autoHelperService).checkPatenteNotExists(PATENTE);

        ArgumentCaptor<Auto> autoCaptor = ArgumentCaptor.forClass(Auto.class);
        verify(autoRepository).save(autoCaptor.capture());

        Auto autoGuardado = autoCaptor.getValue();
        assertEquals(PATENTE, autoGuardado.getPatente());
        assertEquals(sucursalMock, autoGuardado.getSucursal());
        assertEquals(EstadoAutoEnum.DISPONIBLE,autoGuardado.getEstado());
        assertEquals(TiposRembolso.SIN_REMBOLSO,autoGuardado.getRembolso());
    }

    @Test
    @DisplayName("Crear auto: Debe fallar por patente duplicada y no guardar ningun vehiculo")
    public void crearAuto_PatenteDuplicada_Exception(){
        doThrow(new RuntimeException("La patente ya existe"))
                .when(autoHelperService)
                .checkPatenteNotExists(PATENTE);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            autoService.crearAuto(dto);
        });

        assertEquals("La patente ya existe", exception.getMessage());
        verify(autoRepository, never()).save(any());
    }
}
