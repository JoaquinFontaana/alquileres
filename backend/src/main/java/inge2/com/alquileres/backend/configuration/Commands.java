package inge2.com.alquileres.backend.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent @AllArgsConstructor
public class Commands {
    private final AdminCreation adminCreation;
    private final RoleDataLoader roleDataLoader;
    private final JdbcTemplate jdbcTemplate;

    @ShellMethod(key="init_db")
    public void seedDb(){
        roleDataLoader.loadRoles();
        adminCreation.crearAdmin();
    }

    @ShellMethod(key = "reset-db", value = "¡PELIGRO! Borra TODOS los datos de la base de datos.")
    @Transactional
    public String resetDatabase() {
        try {
            // 1. Desactivar chequeo de claves foráneas (Solo funciona en MySQL/MariaDB)
            jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");

            // 2. Obtener todas las tablas de tu esquema actual
            List<String> tablas = jdbcTemplate.queryForList(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema = DATABASE()",
                    String.class
            );

            // 3. Recorrer y vaciar cada tabla
            for (String tabla : tablas) {
                // Ignoramos tablas de sistema o migraciones si usaras Flyway (opcional)
                if (!tabla.equals("flyway_schema_history")) {
                    jdbcTemplate.execute("TRUNCATE TABLE " + tabla);
                }
            }

            // 4. Reactivar chequeo de claves foráneas
            jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");

            // 5. Opcional: Volver a insertar datos iniciales (Admin, Roles, etc.)
            // adminCreation.run(); <--- Si inyectas tu AdminCreation aquí podrías repoblar

            return "✅ Base de datos vaciada correctamente. Estructura mantenida.";

        } catch (Exception e) {
            return "❌ Error al resetear la BD: " + e.getMessage();
        }
    }
}
