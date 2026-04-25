package adopciones.v01.services.usuarios;

import adopciones.v01.dto.perfilpersona.PerfilPersonaDTO;
import adopciones.v01.dto.perfilpersona.PerfilPersonaDireccionDTO;
import adopciones.v01.dto.perfilpersona.PerfilPersonaImportanteDTO;
import adopciones.v01.models.usuarios.PersonaModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import adopciones.v01.repositories.usuarios.PersonaRepository;
import adopciones.v01.repositories.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonaService {

    private final PersonaRepository personaRepo;
    private final UsuarioRepository usuarioRepo;


    //*****************CRUD

    // LISTAR
    @Transactional(readOnly = true)
    public List<PerfilPersonaDTO> listarPersonas() {
        return personaRepo.findAll()
                .stream()
                .map(this::toPerfilPersonaDTO)
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

        PersonaModel nuevoPerfil = toPersonaModel(personaDTO);
        nuevoPerfil.setUsuario(usuario);
        PersonaModel guardado = personaRepo.save(nuevoPerfil);

        return toPerfilPersonaDTO(guardado);
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
        return toPerfilPersonaDTO(editado);
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
                .map(this::toPerfilPersonaDTO);
    }

    @Transactional(readOnly = true)
    public Optional<PerfilPersonaDTO> buscarPorDni(String dni) {
        return personaRepo.findByDni(dni)
                .map(this::toPerfilPersonaDTO);
    }

    @Transactional(readOnly = true)
    public Optional<PerfilPersonaDTO> buscarPorTelefono(String telefono) {
        return personaRepo.findByTelefono(telefono)
                .map(this::toPerfilPersonaDTO);
    }

    @Transactional(readOnly = true)
    public List<PerfilPersonaDTO> buscarPorCodPostal(String codPostal) {
        return personaRepo.findByCodpostal(codPostal)
                .stream()
                .map(this::toPerfilPersonaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PerfilPersonaDTO> buscarPorLocalidad(String localidad) {
        return personaRepo.findByLocalidad(localidad)
                .stream()
                .map(this::toPerfilPersonaDTO)
                .collect(Collectors.toList());
    }


    //*****************MAPPEOS

    private PerfilPersonaDTO toPerfilPersonaDTO(PersonaModel persona) {
        if (persona == null) return null;

        PerfilPersonaDireccionDTO direccion = new PerfilPersonaDireccionDTO();
        direccion.setDireccion(persona.getDireccion());
        direccion.setLocalidad(persona.getLocalidad());
        direccion.setProvincia(persona.getProvincia());
        direccion.setCodPostal(persona.getCodpostal());

        PerfilPersonaDTO dto = new PerfilPersonaDTO();
        dto.setIdUsuario(persona.getUsuario() != null ? persona.getUsuario().getId() : null);
        dto.setDni(persona.getDni());
        dto.setNombre(persona.getNombre());
        dto.setApellidos(persona.getApellidos());
        dto.setTelefono(persona.getTelefono());
        dto.setFechaNacimiento(persona.getFechaNacimiento());
        dto.setDireccionCompleta(direccion);

        return dto;
    }

//    private PerfilPersonaImportanteDTO toPerfilPersonaImportanteDTO(PersonaModel persona) {
//        if (persona == null) return null;
//
//        PerfilPersonaImportanteDTO dto = new PerfilPersonaImportanteDTO();
//        dto.setIdUsuario(persona.getUsuario() != null ? persona.getUsuario().getId() : null);
//        dto.setFechaNacimiento(persona.getFechaNacimiento());
//
//        return dto;
//    }

    private PersonaModel toPersonaModel(PerfilPersonaDTO dto) {
        if (dto == null) return null;

        PersonaModel model = new PersonaModel();
        model.setDni(dto.getDni());
        model.setNombre(dto.getNombre());
        model.setApellidos(dto.getApellidos());
        model.setTelefono(dto.getTelefono());
        model.setFechaNacimiento(dto.getFechaNacimiento());

        if (dto.getDireccionCompleta() != null) {
            model.setDireccion(dto.getDireccionCompleta().getDireccion());
            model.setLocalidad(dto.getDireccionCompleta().getLocalidad());
            model.setProvincia(dto.getDireccionCompleta().getProvincia());
            model.setCodpostal(dto.getDireccionCompleta().getCodPostal());
        }

        return model;
    }

    private void actualizarPersonaDesdeDTO(PersonaModel persona, PerfilPersonaDTO dto) {
        persona.setNombre(dto.getNombre());
        persona.setApellidos(dto.getApellidos());
        persona.setTelefono(dto.getTelefono());
        persona.setFechaNacimiento(dto.getFechaNacimiento());

        if (dto.getDireccionCompleta() != null) {
            persona.setDireccion(dto.getDireccionCompleta().getDireccion());
            persona.setLocalidad(dto.getDireccionCompleta().getLocalidad());
            persona.setProvincia(dto.getDireccionCompleta().getProvincia());
            persona.setCodpostal(dto.getDireccionCompleta().getCodPostal());
        }
    }
}