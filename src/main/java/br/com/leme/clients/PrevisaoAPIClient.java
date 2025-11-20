package br.com.leme.clients;

import br.com.leme.dto.PrevisaoAPIResponseDTO;
import br.com.leme.dto.PrevisaoRequestDTO;
import br.com.leme.exceptions.ClientAPIException;
import br.com.leme.exceptions.UnavaliableAPIException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class PrevisaoAPIClient {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static PrevisaoAPIResponseDTO getPrevisao (PrevisaoRequestDTO request) {
        try {
            HttpPost requestHttp = new HttpPost("https://leme-python.onrender.com/previsoes/");
            String json = mapper.writeValueAsString(request);
            requestHttp.setEntity(new StringEntity(json));
            requestHttp.setHeader("Content-Type", "application/json");

            CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
            CloseableHttpResponse responseHttp = httpClient.execute(requestHttp);
            HttpEntity entity = responseHttp.getEntity();

            if (entity == null) {
                throw new ClientAPIException("Não foi possível processar previsão.");
            }

            String result = EntityUtils.toString(entity);
            return mapper.readValue(result, PrevisaoAPIResponseDTO.class);

        } catch (com.fasterxml.jackson.core.JsonParseException e) {
            throw new UnavaliableAPIException();

        } catch (IOException e) {
            throw new ClientAPIException(e);
        }
    }
}
