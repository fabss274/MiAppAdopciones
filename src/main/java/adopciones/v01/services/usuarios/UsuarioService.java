package adopciones.v01.services.usuarios;

import adopciones.v01.dto.usuario.UsuarioRegistroDTO;
import adopciones.v01.dto.usuario.UsuarioRespuestaDTO;
import adopciones.v01.enums.rol;
import adopciones.v01.models.usuarios.UsuarioModel;
import adopciones.v01.repositories.usuarios.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    // LISTAR
    @Transactional(readOnly = true)
    public List<UsuarioRespuestaDTO> listarUsuarios() {
        return usuarioRepo.findAll()
                .stream()
                .map(this::convertirARespuestaDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR EMAIL
    @Transactional(readOnly = true)
    public Optional <UsuarioRespuestaDTO> buscarPorEmail(String email) {
        return usuarioRepo.findByEmail(email)
                .map(this::convertirARespuestaDTO);
    }

    // BUSCAR POR ID
    @Transactional(readOnly = true)
    public Optional<UsuarioRespuestaDTO> buscarPorId(Long id) {
        return usuarioRepo.findById(id)
                .map(this::convertirARespuestaDTO);
    }

    // BUSCAR POR ROL
    @Transactional(readOnly = true)
    public List<UsuarioRespuestaDTO> buscarPorRol(String rolString) {
        try {

            rol rolEnum = rol.valueOf(rolString.toUpperCase());
            return usuarioRepo.findByRolAndActivoTrue(rolEnum)
                    .stream()
                    .map(this::convertirARespuestaDTO)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    // BUSCAR POR CUENTAS ACTIVAS
    public List<UsuarioRespuestaDTO> buscarUsuariosActivos() {
        return usuarioRepo.findByActivoTrue()
                .stream()
                .map(this::convertirARespuestaDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR CUENTAS DESACTIVADAS
    public List<UsuarioRespuestaDTO> buscarUsuariosDesactivados() {
        return usuarioRepo.findByActivoFalse()
                .stream()
                .map(this::convertirARespuestaDTO)
                .collect(Collectors.toList());
    }

    // REGISTRAR
    public UsuarioRespuestaDTO crearUsuario(UsuarioRegistroDTO registroDTO) {
        if (usuarioRepo.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        UsuarioModel nuevoUsuario = convertirRegistroDTOAModel(registroDTO);
        UsuarioModel guardar = usuarioRepo.save(nuevoUsuario);
        return convertirARespuestaDTO(guardar);
    }

    // EDITAR
    public UsuarioRespuestaDTO editarUsuario(Long id, UsuarioRegistroDTO registroDTO) {
        //validamos que el email exista
        UsuarioModel usuarioAEditar = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado (id: " + id +")"));
        //validamos que el email no se repita
        if (!usuarioAEditar.getEmail().equals(registroDTO.getEmail())
                && usuarioRepo.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        usuarioAEditar.setEmail(registroDTO.getEmail());
        usuarioAEditar.setContrasena(passwordEncoder.encode(registroDTO.getContrasena()));
        usuarioAEditar.setRol(rol.valueOf(registroDTO.getRol().toUpperCase()));
        
        UsuarioModel editado = usuarioRepo.save(usuarioAEditar);
        return convertirARespuestaDTO(editado);
    }

    // ELIMINAR
    public boolean eliminarUsuario(Long id) {
        if (!usuarioRepo.existsById(id)) {
            return false;
        }
        usuarioRepo.deleteById(id);
        return true;
    }


    // ------- MAPPEOS

    // conversión modelo a DTO respuestsa
    private UsuarioRespuestaDTO convertirARespuestaDTO(UsuarioModel model) {
        UsuarioRespuestaDTO response = new UsuarioRespuestaDTO();
        response.setId(model.getId());
        response.setEmail(model.getEmail());
        response.setRol(model.getRol().name());
        response.setActivo(model.isActivo());
        return response;
    }


    // conversion registroDTO a model
    private UsuarioModel convertirRegistroDTOAModel(UsuarioRegistroDTO dto) {
        UsuarioModel model = new UsuarioModel();
        model.setEmail(dto.getEmail());
        model.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        model.setRol(rol.valueOf(dto.getRol().toUpperCase()));
        model.setActivo(true);
        return model;
    }

}
