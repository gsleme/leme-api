package br.com.leme.services;


import br.com.leme.dao.TrilhaDAO;
import br.com.leme.dto.TrilhaRequestDTO;
import br.com.leme.dto.TrilhaResponseDTO;
import br.com.leme.entities.Trilha;
import br.com.leme.exceptions.EntityNotFoundException;
import br.com.leme.exceptions.InvalidIdFormatException;

import java.util.List;
import java.util.UUID;

public class TrilhaService {
    private final TrilhaDAO dao;

    public TrilhaService() {
        this.dao = new TrilhaDAO();
    }

    public List<TrilhaResponseDTO> findAll () {
        return dao.findAll().stream().map(this::toResponse).toList();
    }

    public TrilhaResponseDTO findById (String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Trilha trilha = dao.findById(id);
            if (trilha == null) throw  new EntityNotFoundException("trilha");
            return toResponse(trilha);

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }
    }

    private TrilhaResponseDTO toResponse (Trilha trilha) {
        return new TrilhaResponseDTO(
            trilha.getId().toString(),
            trilha.getTitulo(),
            trilha.getDescricao(),
            trilha.getAreaFoco(),
            trilha.getXpTrilha(),
            trilha.getDataCriacao()
        );
    }
}
