package br.com.leme.services;

import br.com.leme.clients.PrevisaoAPIClient;
import br.com.leme.dao.PrevisaoDAO;
import br.com.leme.dto.PrevisaoAPIResponseDTO;
import br.com.leme.dto.PrevisaoRequestDTO;
import br.com.leme.dto.PrevisaoResponseDTO;
import br.com.leme.entities.Previsao;
import br.com.leme.exceptions.InvalidIdFormatException;

import java.util.UUID;

public class PrevisaoService {
    private final PrevisaoDAO dao;

    public PrevisaoService() {
        this.dao = new PrevisaoDAO();
    }

    public PrevisaoResponseDTO register (PrevisaoRequestDTO request, String idUsuario) {
        try {
            UUID uuidUsuario = UUID.fromString(idUsuario);

        } catch (IllegalArgumentException e) {
            throw new InvalidIdFormatException();
        }

        PrevisaoAPIResponseDTO apiResponse = PrevisaoAPIClient.getPrevisao(request);
        Previsao previsao = new Previsao(
            UUID.fromString(idUsuario),
            apiResponse.taxaSucesso(),
            apiResponse.categoria()
        );

        dao.register(previsao);
        return new PrevisaoResponseDTO(
            previsao.getId().toString(),
            previsao.getIdUsuario().toString(),
            previsao.getTaxaSucesso(),
            previsao.getCategoria(),
            previsao.getDataPrevisao()
        );
    }
}
