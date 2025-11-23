package br.com.leme.services;

import br.com.leme.dao.ProgressoDAO;
import br.com.leme.dto.ProgressoRequestDTO;
import br.com.leme.dto.ProgressoResponseDTO;
import br.com.leme.entities.Progresso;
import br.com.leme.exceptions.EntityNotFoundException;
import br.com.leme.exceptions.InvalidIdFormatException;

import java.util.UUID;

public class ProgressoService {
    private final ProgressoDAO dao;

    public ProgressoService() {
        this.dao = new ProgressoDAO();
    }

    public ProgressoResponseDTO findById (String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Progresso progresso = dao.findById(id);
            if (progresso == null) {
                throw new EntityNotFoundException("progresso");
            };

            return toResponse(progresso);

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }
    }

    public ProgressoResponseDTO register (ProgressoRequestDTO request) {
        try {
            UUID uuidUsuario = UUID.fromString(request.idUsuario());
            UUID uuidModulo = UUID.fromString(request.idModulo());

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }

        Progresso progresso = new Progresso(
            UUID.fromString(request.idUsuario()),
            UUID.fromString(request.idModulo())
        );

        dao.register(progresso);
        return toResponse(progresso);
    }

    private ProgressoResponseDTO toResponse(Progresso progresso) {
        return new ProgressoResponseDTO(
                progresso.getId().toString(),
                progresso.getIdUsuario().toString(),
                progresso.getIdModulo().toString(),
                progresso.getDataConclusao()
        );
    }
}
