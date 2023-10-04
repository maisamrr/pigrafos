package com.pigrafos.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigrafos.model.LabyrinthResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LabyrinthClient {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> verifyLabyrinths() throws IOException {
        String url = "https://gtm.delary.dev/labirintos";
        HttpGet request = new HttpGet(url);

        CloseableHttpResponse httpResponse = httpClient.execute(request);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = httpResponse.getEntity();
            String responseBody = EntityUtils.toString(entity);
            String[] labyrinths = objectMapper.readValue(responseBody, String[].class);

            return Arrays.stream(labyrinths).toList();
        } else {
            throw new IOException("Erro na solicitação: Código de status " + statusCode);
        }
    }

    public LabyrinthResponse startExploration(String user, String labyrinths) throws IOException {
        String url = "https://gtm.delary.dev/iniciar";
        HttpPost request = new HttpPost(url);
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        String json = "{\"id\":\"" + user + "\",\"labirinto\":\"" + labyrinths + "\"}";

        final StringEntity myEntity = new StringEntity(json);

        request.setEntity(myEntity);
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");

        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);


            return objectMapper.readValue(responseBody, LabyrinthResponse.class);
        } else {
            throw new IOException("Erro na solicitação de exploração: Código de status " + statusCode);
        }
    }

    public LabyrinthResponse move(String user, String labyrinths, int newPosition) throws IOException {
        String url = "https://gtm.delary.dev/movimentar";

        HttpPost request = new HttpPost(url);
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        String json = "{\"id\":\"" + user + "\",\"labirinto\":\"" + labyrinths + "\",\"nova_posicao\":" + newPosition + "}";

        final StringEntity myEntity = new StringEntity(json);

        request.setEntity(myEntity);
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");

        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);


            return objectMapper.readValue(responseBody, LabyrinthResponse.class);
        } else {
            throw new IOException("Erro na solicitação de movimento: Código de status " + statusCode);
        }
    }

    public HttpResponse validatePath(String user, String labyrinths, List<Integer> todosMovimentos) throws IOException {
        String url = "https://gtm.delary.dev/valida_caminho";
        HttpPost request = new HttpPost(url);
        // ...
        return httpClient.execute(request);
    }

}
