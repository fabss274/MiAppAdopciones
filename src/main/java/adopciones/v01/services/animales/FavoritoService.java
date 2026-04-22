package adopciones.v01.services.animales;

import adopciones.v01.dto.favorito.FavoritoDTO;
import adopciones.v01.models.animales.AnimalitoModel;
import adopciones.v01.models.animales.FavoritoModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import adopciones.v01.repositories.animales.AnimalitoRepository;
import adopciones.v01.repositories.animales.FavoritoRepository;
import adopciones.v01.repositories.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoritoService {
    private final FavoritoRepository favoritoRepo;
    private final UsuarioRepository usuarioRepo;
    private final AnimalitoRepository animalitoRepo;

    // ***************** CRUD

    @Transactional(readOnly = true)
    public List<FavoritoDTO> listarFavoritos() {
        return favoritoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FavoritoDTO agregarFavorito(FavoritoDTO dto) {
        UsuarioModel usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + dto.getUsuarioId() + ")"));

        AnimalitoModel animal = animalitoRepo.findById(dto.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal no encontrado (id: " + dto.getAnimalId() + ")"));

        if (favoritoRepo.existsByUsuarioAndAnimal(usuario, animal)) {
            throw new RuntimeException("Este animal ya está en favoritos del usuario");
        }

        FavoritoModel favorito = new FavoritoModel();
        favorito.setUsuario(usuario);
        favorito.setAnimal(animal);
        favorito.setCreated_at(LocalDateTime.now());

        return toDTO(favoritoRepo.save(favorito));
    }

    @Transactional
    public boolean eliminarFavorito(Long id) {
        if (!favoritoRepo.existsById(id)) {
            return false;
        }
        favoritoRepo.deleteById(id);
        return true;
    }

    @Transactional
    public boolean eliminarFavoritoPorUsuarioYAnimal(Long usuarioId, Long animalId) {
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));

        AnimalitoModel animal = animalitoRepo.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado (id: " + animalId + ")"));

        if (!favoritoRepo.existsByUsuarioAndAnimal(usuario, animal)) {
            return false;
        }
        favoritoRepo.deleteByUsuarioAndAnimal(usuario, animal);
        return true;
    }

    // ***************** BUSQUEDAS

    @Transactional(readOnly = true)
    public Optional<FavoritoDTO> buscarPorId(Long id) {
        return favoritoRepo.findById(id).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<FavoritoDTO> buscarPorUsuario(Long usuarioId) {
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));
        return favoritoRepo.findByUsuario(usuario)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FavoritoDTO> buscarPorAnimal(Long animalId) {
        AnimalitoModel animal = animalitoRepo.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado (id: " + animalId + ")"));
        return favoritoRepo.findByAnimal(animal)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean existeFavorito(Long usuarioId, Long animalId) {
        UsuarioModel usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + usuarioId + ")"));
        AnimalitoModel animal = animalitoRepo.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado (id: " + animalId + ")"));
        return favoritoRepo.existsByUsuarioAndAnimal(usuario, animal);
    }

    // ***************** TRADUCTOR

    private FavoritoDTO toDTO(FavoritoModel model) {
        FavoritoDTO dto = new FavoritoDTO();
        dto.setId(model.getId());
        dto.setUsuarioId(model.getUsuario().getId());
        dto.setAnimalId(model.getAnimal().getId());
        dto.setCreated_at(model.getCreated_at());
        return dto;
    }
}
