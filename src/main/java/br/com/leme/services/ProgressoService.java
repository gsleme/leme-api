package br.com.leme.services;

import br.com.leme.dao.ProgressoDAO;
import br.com.leme.dto.ProgressoRequestDTO;
import br.com.leme.dto.ProgressoResponseDTO;
import br.com.leme.entities.Progresso;
import br.com.leme.exceptions.InvalidIdFormatException;

import java.util.UUID;

public class ProgressoService {
    private final ProgressoDAO dao;

    public ProgressoService() {
        this.dao = new ProgressoDAO();
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
        return new ProgressoResponseDTO(
            progresso.getId().toString(),
            progresso.getIdUsuario().toString(),
            progresso.getIdModulo().toString(),
            progresso.getDataConclusao()
        );
    }
}
