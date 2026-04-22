package adopciones.v01.services.refugios;

import adopciones.v01.dto.refugios.NucleoZooApiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class NucleoZooApiService {

    private static final String DATASET_PATH = "/catalog/datasets/nucleos-zoologicos-de-castilla-y-leon/records";
    private final RestClient nucleoZooWebClient;

    public NucleoZooApiResponseDTO fetchRecords(int limit, int offset, String provincia) {

        return nucleoZooWebClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(DATASET_PATH)
                            .queryParam("limit", limit)
                            .queryParam("offset", offset);

                    if (provincia != null && !provincia.isBlank()) {
                        uriBuilder.queryParam("refine", "prov_exp:" + provincia.trim());
                    }

                    return uriBuilder.build();
                })
                .retrieve()
                .body(NucleoZooApiResponseDTO.class);
    }

    public NucleoZooApiResponseDTO fetchByCodigo(String codigo) {

        return nucleoZooWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(DATASET_PATH)
                        .queryParam("where", "codigo_de_nucleo_zoologico=\"" + codigo + "\"")
                        .queryParam("limit", 1)
                        .build())
                .retrieve()
                .body(NucleoZooApiResponseDTO.class);
    }
}
