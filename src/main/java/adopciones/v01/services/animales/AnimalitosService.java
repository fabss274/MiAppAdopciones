package adopciones.v01.services.animales;

import adopciones.v01.dto.animales.AnimalitoDTO;
import adopciones.v01.dto.animales.AnimalitoMapper;
import adopciones.v01.enums.estadoAnimal;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.refugios.RefugioModel;
import adopciones.v01.repositories.animales.AnimalitoRepository;
import adopciones.v01.repositories.refugios.RefugioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AnimalitosService {

    private final AnimalitoRepository animalitoRepo;
    private final RefugioRepository refugioRepo;
    private final AnimalitoMapper mapper;

    // ***************** CRUD

    @Transactional(readOnly = true)
    public List<AnimalitoDTO> listarAnimales() {
        return animalitoRepo.findAll()
                .stream()
                .map(mapper::toAnimalitoDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AnimalitoDTO crearAnimalito(AnimalitoDTO dto) {
        if (dto.getChip() != null && animalitoRepo.existsByChip(dto.getChip())) {
            throw new RuntimeException("El chip ya está registrado");
        }
        if (animalitoRepo.existsByNumFicha(dto.getNumFicha())) {
            throw new RuntimeException("El número de ficha ya está registrado");
        }

        RefugioModel refugio = refugioRepo.findById(dto.getRefugio().getId())
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado"));

        AnimalitoModel nuevoAnimal = mapper.toAnimalitoModel(dto);
        nuevoAnimal.setRefugio(refugio);
        nuevoAnimal.setCreated_at(LocalDateTime.now());
        nuevoAnimal.setUpdated_at(LocalDateTime.now());

        // Si no viene estado, por defecto DISPONIBLE
        if (nuevoAnimal.getEstadoAnimal() == null) {
            nuevoAnimal.setEstadoAnimal(estadoAnimal.DISPONIBLE);
        }

        AnimalitoModel guardado = animalitoRepo.save(nuevoAnimal);
        return mapper.toAnimalitoDTO(guardado);
    }

    @Transactional
    public AnimalitoDTO editarAnimalito(Long id, AnimalitoDTO dto) {
        AnimalitoModel animalAEditar = animalitoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado (id: " + id + ")"));

        // Validar chip si cambia
        if (dto.getChip() != null &&
                !Objects.equals(animalAEditar.getChip(), dto.getChip()) &&
                animalitoRepo.existsByChip(dto.getChip())) {
            throw new RuntimeException("El chip ya está en uso");
        }

        // Validar numFicha si cambia
        if (!animalAEditar.getNumFicha().equals(dto.getNumFicha()) &&
                animalitoRepo.existsByNumFicha(dto.getNumFicha())) {
            throw new RuntimeException("El número de ficha ya está en uso");
        }

        actualizarAnimalDesdeDTO(animalAEditar, dto);
        animalAEditar.setUpdated_at(LocalDateTime.now());

        AnimalitoModel editado = animalitoRepo.save(animalAEditar);
        return mapper.toAnimalitoDTO(editado);
    }

    @Transactional
    public boolean eliminarAnimalito(Long id) {
        if (!animalitoRepo.existsById(id)) {
            return false;
        }
        animalitoRepo.deleteById(id);
        return true;
    }

    // ***************** BUSQUEDAS

    @Transactional(readOnly = true)
    public Optional<AnimalitoDTO> buscarPorId(Long id) {
        return animalitoRepo.findById(id)
                .map(mapper::toAnimalitoDTO);
    }

    @Transactional(readOnly = true)
    public Optional<AnimalitoDTO> buscarPorChip(String chip) {
        return animalitoRepo.findByChip(chip)
                .map(mapper::toAnimalitoDTO);
    }

    @Transactional(readOnly = true)
    public Optional<AnimalitoDTO> buscarPorNumFicha(String numFicha) {
        return animalitoRepo.findByNumFicha(numFicha)
                .map(mapper::toAnimalitoDTO);
    }

    @Transactional(readOnly = true)
    public List<AnimalitoDTO> buscarPorRefugio(Long refugioId) {
        RefugioModel refugio = refugioRepo.findById(refugioId)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado (id: " + refugioId + ")"));
        return animalitoRepo.findByRefugio(refugio)
                .stream()
                .map(mapper::toAnimalitoDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AnimalitoDTO> buscarPorEspecie(String especie) {
        return animalitoRepo.findByEspecie(especie)
                .stream()
                .map(mapper::toAnimalitoDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AnimalitoDTO> buscarPorEstado(estadoAnimal estado) {
        return animalitoRepo.findByEstadoAnimal(estado)
                .stream()
                .map(mapper::toAnimalitoDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AnimalitoDTO> buscarPorEspecieYEstado(String especie, estadoAnimal estado) {
        return animalitoRepo.findByEspecieAndEstadoAnimal(especie, estado)
                .stream()
                .map(mapper::toAnimalitoDTO)
                .collect(Collectors.toList());
    }

    // ***************** TRADUCTOR

    private void actualizarAnimalDesdeDTO(AnimalitoModel animal, AnimalitoDTO dto) {
        animal.setChip(dto.getChip());
        animal.setNumFicha(dto.getNumFicha());
        animal.setNombre(dto.getNombre());
        animal.setEspecie(dto.getEspecie());
        animal.setRaza(dto.getRaza());
        animal.setSexo(dto.getSexo());
        animal.setTamano(dto.getTamano());
        animal.setEdad(dto.getEdad());
        animal.setDescripcion(dto.getDescripcion());
        animal.setEsterilizado(dto.isEsterilizado());
        animal.setEstadoAnimal(dto.getEstadoAnimal());
    }
}
