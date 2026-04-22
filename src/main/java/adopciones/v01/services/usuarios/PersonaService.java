package adopciones.v01.services.usuarios;

import adopciones.v01.dto.perfilpersona.PerfilPersonaDTO;
import adopciones.v01.dto.perfilpersona.PerfilPersonaMapper;
import adopciones.v01.models.usuarios.PersonaModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import adopciones.v01.repositories.usuarios.PersonaRepository;
import adopciones.v01.repositories.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonaService {

    private final PersonaRepository personaRepo;
    private final UsuarioRepository usuarioRepo;
    private final PerfilPersonaMapper mapper;


    //*****************CRUD
    // LISTAR
    @Transactional(readOnly = true)
    public List<PerfilPersonaDTO> listarPersonas() {
        return personaRepo.findAll()
                .stream()
                .map(mapper::toPerfilPersonaDTO)
                .collect(Collectors.toList());
    }

    // CREAR
    @Transactional
    public PerfilPersonaDTO crearPerfilPersona(PerfilPersonaDTO personaDTO) {
        if (personaRepo.existsByDni(personaDTO.getDni())) {
            throw new RuntimeException("El DNI ya está registrado");
        }
        if (personaRepo.existsByTelefono(personaDTO.getTelefono())) {
            throw new RuntimeException("El teléfono ya está registrado");
        }
        UsuarioModel usuario = usuarioRepo.findById(personaDTO.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PersonaModel nuevoPerfil = mapper.toPersonaModel(personaDTO);
        nuevoPerfil.setUsuario(usuario);
        PersonaModel guardado = personaRepo.save(nuevoPerfil);

        return mapper.toPerfilPersonaDTO(guardado);
    }

    // EDITAR
    @Transactional
    public PerfilPersonaDTO editarPerfilPersona(Long id, PerfilPersonaDTO personaDTO) {
        PersonaModel personaAEditar = personaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado (id: " + id + ")"));

        if (!personaAEditar.getDni().equals(personaDTO.getDni()) &&
                personaRepo.existsByDni(personaDTO.getDni())) {
            throw new RuntimeException("El DNI ya está en uso");
        }

        if (!Objects.equals(personaAEditar.getTelefono(), personaDTO.getTelefono()) &&
                personaRepo.existsByTelefono(personaDTO.getTelefono())) {
            throw new RuntimeException("El teléfono ya está en uso");
        }

        actualizarPersonaDesdeDTO(personaAEditar, personaDTO);

        PersonaModel editado = personaRepo.save(personaAEditar);
        return mapper.toPerfilPersonaDTO(editado);
    }

    // ELIMINAR
    @Transactional
    public boolean eliminarPerfilPersona(Long id) {
        if (!personaRepo.existsById(id)) {
            return false;
        }
        personaRepo.deleteById(id);
        return true;
    }

    //*****************BUSQUEDAS
    @Transactional(readOnly = true)
    public Optional<PerfilPersonaDTO> buscarPorId(Long id) {
        return personaRepo.findById(id)
                .map(mapper::toPerfilPersonaDTO);
    }

    @Transactional(readOnly = true)
    public Optional<PerfilPersonaDTO> buscarPorDni(String dni) {
        return personaRepo.findByDni(dni)
                .map(mapper::toPerfilPersonaDTO);
    }

    @Transactional(readOnly = true)
    public Optional<PerfilPersonaDTO> buscarPorTelefono(String telefono) {
        return personaRepo.findByTelefono(telefono)
                .map(mapper::toPerfilPersonaDTO);
    }

    @Transactional(readOnly = true)
    public List<PerfilPersonaDTO> buscarPorCodPostal(String codPostal) {
        return personaRepo.findByCodpostal(codPostal)
                .stream()
                .map(mapper::toPerfilPersonaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PerfilPersonaDTO> buscarPorLocalidad(String localidad) {
        return personaRepo.findByLocalidad(localidad)
                .stream()
                .map(mapper::toPerfilPersonaDTO)
                .collect(Collectors.toList());
    }

    //*****************TRADUCTOR

    private void actualizarPersonaDesdeDTO(PersonaModel persona, PerfilPersonaDTO dto) {
        persona.setNombre(dto.getNombre());
        persona.setApellidos(dto.getApellidos());
        persona.setTelefono(dto.getTelefono());
        persona.setFechaNacimiento(dto.getFechaNacimiento());

        // Dirección anidada
        if (dto.getDireccionCompleta() != null) {
            persona.setDireccion(dto.getDireccionCompleta().getDireccion());
            persona.setLocalidad(dto.getDireccionCompleta().getLocalidad());
            persona.setProvincia(dto.getDireccionCompleta().getProvincia());
            persona.setCodpostal(dto.getDireccionCompleta().getCodPostal());
        }

    }
}
