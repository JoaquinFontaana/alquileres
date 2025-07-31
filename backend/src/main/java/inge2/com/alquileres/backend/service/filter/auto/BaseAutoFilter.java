package inge2.com.alquileres.backend.service.filter.auto;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.repository.IAutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAutoFilter implements IAutoFilter {
    @Autowired
    private IAutoRepository repository;

    @Override
    public List<Auto> listar() {
        return repository.findAll();
    }
}
