package br.com.leme.services;

import br.com.leme.dao.ModuloDAO;
import br.com.leme.dto.ModuloResponseDTO;
import br.com.leme.entities.Modulo;
import br.com.leme.exceptions.EntityNotFoundException;
import br.com.leme.exceptions.InvalidIdFormatException;

import java.util.List;
import java.util.UUID;

public class ModuloService {
    private final ModuloDAO dao;

    public ModuloService() {
        this.dao = new ModuloDAO();
    }

    public List<ModuloResponseDTO> findAll () {
        return dao.findAll().stream().map(this::toResponse).toList();
    }

    public ModuloResponseDTO findById (String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Modulo modulo = dao.findById(id);
            if (modulo == null) throw  new EntityNotFoundException("módulo");
            return toResponse(modulo);

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }
    }

    private ModuloResponseDTO toResponse (Modulo modulo) {
        return new ModuloResponseDTO(
            modulo.getId().toString(),
            modulo.getIdTrilha().toString(),
            modulo.getTitulo(),
            modulo.getDescricao(),
            modulo.getTipo(),
            modulo.getConteudo(),
            modulo.getXpRecompensa(),
            modulo.getAdaptacaoNecessaria()
        );
    }
}
