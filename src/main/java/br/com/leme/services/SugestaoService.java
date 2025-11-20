package br.com.leme.services;

import br.com.leme.clients.SugestaoAPIClient;
import br.com.leme.dao.SugestaoDAO;
import br.com.leme.dto.SugestaoRequestDTO;
import br.com.leme.dto.SugestaoResponseDTO;
import br.com.leme.entities.Sugestao;
import br.com.leme.exceptions.DatabaseException;
import br.com.leme.exceptions.EntityNotFoundException;
import br.com.leme.exceptions.InvalidIdFormatException;

import java.util.UUID;

public class SugestaoService {
    private final SugestaoDAO dao;

    public SugestaoService() {
        this.dao = new SugestaoDAO();
    }

    public SugestaoResponseDTO findById (String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Sugestao sugestao = dao.findById(id);
            if (sugestao == null) throw  new EntityNotFoundException("sugestão");
            return toResponse(sugestao);

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }
    }

    public SugestaoResponseDTO register (SugestaoRequestDTO request, String idUsuario) {
        try {
            UUID uuidUsuario = UUID.fromString(idUsuario);

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }

        String idTrilha = SugestaoAPIClient.getSugestao(request);
        Sugestao sugestao = new Sugestao(
            UUID.fromString(idUsuario),
            UUID.fromString(idTrilha)
        );

        dao.register(sugestao);
        return toResponse(sugestao);
    }

    private SugestaoResponseDTO toResponse(Sugestao sugestao) {
        return new SugestaoResponseDTO(
            sugestao.getId().toString(),
            sugestao.getIdUsuario().toString(),
            sugestao.getIdTrilha().toString(),
            sugestao.getDataSugestao()
        );
    }
}
