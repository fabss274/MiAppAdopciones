package adopciones.v01.services.refugios;

import adopciones.v01.dto.refugios.NucleoZooApiResponseDTO;
import adopciones.v01.dto.refugios.NucleoZooRecordDTO;
import adopciones.v01.enums.estadoNucleo;
import adopciones.v01.models.refugios.NucleoZooModel;
import adopciones.v01.repositories.refugios.NucleoZooRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class NucleoZooSyncService {

    private static final int PAGE_SIZE = 100;

    private final NucleoZooRepository repository;
    private final NucleoZooApiService apiService;

    @Transactional
    public int syncByProvincia(String provincia) {
        log.info("iniciando busqueda de la provincia: {}", provincia);

        int offset = 0;
        int totalInserted = 0;
        int totalCount = 0;
        Set<String> codigosExistentes = new HashSet<>();

        List<NucleoZooModel> existentes = repository.findAll();
        existentes.forEach(nucleo ->
                codigosExistentes.add(nucleo.getCodigoNucleoZoologico())
        );
        log.info("codigo existente en la bd: {}", codigosExistentes.size());

        NucleoZooApiResponseDTO response = null;

        do {
            try {
                response = apiService.fetchRecords(PAGE_SIZE, offset, provincia);

                if (totalCount == 0) {
                    totalCount = response.getTotalCount();
                    log.info("total registros en API: {}", totalCount);
                }

                if (response.getResults() == null || response.getResults().isEmpty()) {
                    log.info("📄 No hay más resultados en offset: {}", offset);
                    break;
                }

                List<NucleoZooModel> toSave = new ArrayList<>();
                for (NucleoZooRecordDTO dto : response.getResults()) {
                    if (!codigosExistentes.contains(dto.getCodigoNucleoZoologico())) {
                        toSave.add(mapToEntity(dto));
                        codigosExistentes.add(dto.getCodigoNucleoZoologico());
                    }
                }

                if (!toSave.isEmpty()) {
                    repository.saveAll(toSave);
                    totalInserted += toSave.size();
                    log.info("pagina {}: {} registros nuevos guardados", offset / PAGE_SIZE + 1, toSave.size());
                } else {
                    log.info("pagina {}: sin registros nuevos", offset / PAGE_SIZE + 1);
                }

                offset += PAGE_SIZE;

            } catch (Exception e) {
                log.error("error en pagina offset={}: {}", offset, e.getMessage());
                break;
            }

        } while (offset < totalCount && response != null && !response.getResults().isEmpty());

        log.info("sincronizacion completada");
        return totalInserted;
    }

    public boolean verificarExistenciaCodigo(String codigo) {

        boolean existeEnBD = repository.findByCodigoNucleoZoologico(codigo).isPresent();
        if (existeEnBD) {
            log.info("codigo {} encontrado en base de datos local", codigo);
            return true;
        }

        log.info("codigo {} no encontrado en bd, consultando API externa...", codigo);
        NucleoZooApiResponseDTO response = apiService.fetchByCodigo(codigo);

        boolean existeEnApi = response != null && response.getTotalCount() > 0;
        log.info("resultado API para codigo {}: {}", codigo, existeEnApi ? "ENCONTRADO" : "NO ENCONTRADO");

        return existeEnApi;
    }

    //*********  TRADUCTOR A DTO
    private NucleoZooModel mapToEntity(NucleoZooRecordDTO dto) {
        NucleoZooModel model = new NucleoZooModel();
        model.setCodigoNucleoZoologico(dto.getCodigoNucleoZoologico());
        model.setNombreTitularNucleoZoologico(dto.getNombreTitularNucleoZoologico());
        model.setEspecieGrupoDeEspecies(dto.getEspecieGrupoDeEspecies());
        model.setMuniExp(dto.getMuniExp());
        model.setProvExp(dto.getProvExp());
        model.setFecha(dto.getFecha());
        model.setEstadoNucleo(estadoNucleo.ACTIVO);
        model.setUltimaSincronizacion(LocalDateTime.now());
        return model;
    }
}