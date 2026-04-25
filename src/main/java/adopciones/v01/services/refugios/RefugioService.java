package adopciones.v01.services.refugios;

import adopciones.v01.dto.refugio.RefugioDTO;
import adopciones.v01.models.refugios.RefugioModel;
import adopciones.v01.models.usuarios.UsuarioModel;
import adopciones.v01.repositories.refugios.RefugioRepository;
import adopciones.v01.repositories.usuarios.UsuarioRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
@Transactional
public class RefugioService {

    private final RefugioRepository refugioRepo;
    private final UsuarioRepository usuarioRepo;

    //***************  CRUD
    // LISTAR
    @Transactional(readOnly = true)
    public List<RefugioDTO> listarRefugios() {
        return refugioRepo.findAll()
                .stream()
                .map(this::convertirARefugioDTO)
                .collect(Collectors.toList());
    }

    // CREAR
    @Transactional
    public RefugioDTO crearRefugio(RefugioDTO refugioDTO) {
        if (refugioRepo.existsByNucleoZoologico(refugioDTO.getNucleoZoologico())) {
            throw new RuntimeException("El código de núcleo zoológico ya está registrado");
        }

        UsuarioModel usuario = usuarioRepo.findById(refugioDTO.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        RefugioModel nuevoRefugio = convertirARefugioModel(refugioDTO);
        nuevoRefugio.setUsuario(usuario);
        RefugioModel guardado = refugioRepo.save(nuevoRefugio);

        return convertirARefugioDTO(guardado);
    }

    // EDITAR
    public RefugioDTO editarRefugio(Long id, RefugioDTO refugioDTO) {
        RefugioModel refugioAEditar = refugioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado (id: " + id + ")"));

        if (!refugioAEditar.getNucleoZoologico().equals(refugioDTO.getNucleoZoologico()) &&
                refugioRepo.existsByNucleoZoologico(refugioDTO.getNucleoZoologico())) {
            throw new RuntimeException("El código de núcleo zoológico ya está en uso");
        }

        actualizarRefugioDesdeDTO(refugioAEditar, refugioDTO);

        RefugioModel editado = refugioRepo.save(refugioAEditar);
        return convertirARefugioDTO(editado);
    }

    // ELIMINAR
    @Transactional
    public boolean eliminarRefugio(Long id) {
        if (!refugioRepo.existsById(id)) {
            return false;
        }
        refugioRepo.deleteById(id);
        return true;
    }

    // buscar por refugio verificado
    @Transactional(readOnly = true)
    public List<RefugioDTO> listarRefugiosVerificados() {
        return refugioRepo.findByVerificadoTrue()
                .stream()
                .map(this::convertirARefugioDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR N.ZOOLOGICO
//    @Transactional(readOnly = true)
//    public Optional<RefugioDTO> buscarPorNucleoZoologico(String nucleoZoologico) {
//        return refugioRepo.findByCodigoNucleoZoologico(nucleoZoologico)
//                .map(this::convertirARefugioDTO);
//    }


    // **********  MAPEOS
    public RefugioDTO convertirARefugioDTO(RefugioModel model) {
        RefugioDTO response = new RefugioDTO();
        response.setId(model.getId());
        response.setUsuario(model.getUsuario());
        response.setNombreRefugio(model.getNombreRefugio());
        response.setNucleoZoologico(model.getNucleoZoologico());
        response.setDireccion(model.getDireccion());
        response.setVerificado(model.isVerificado());
        response.setDescripcion(model.getDescripcion());
        response.setTelefono(model.getTelefono());
        response.setAnimales(model.getAnimales());
        response.setFormularios(model.getFormularios());
        return response;
    }

    public RefugioModel convertirARefugioModel(RefugioDTO dto) {
        RefugioModel response = new RefugioModel();
        response.setId(dto.getId());
        response.setUsuario(dto.getUsuario());
        response.setNombreRefugio(dto.getNombreRefugio());
        response.setNucleoZoologico(dto.getNucleoZoologico());
        response.setDireccion(dto.getDireccion());
        response.setVerificado(dto.isVerificado());
        response.setDescripcion(dto.getDescripcion());
        response.setTelefono(dto.getTelefono());
        response.setAnimales(dto.getAnimales());
        response.setFormularios(dto.getFormularios());
        return response;
    }

    private void actualizarRefugioDesdeDTO(RefugioModel refugio, RefugioDTO dto) {
        refugio.setNombreRefugio(dto.getNombreRefugio());
        refugio.setNucleoZoologico(dto.getNucleoZoologico());
        refugio.setDireccion(dto.getDireccion());
        refugio.setVerificado(dto.isVerificado());
        refugio.setDescripcion(dto.getDescripcion());
        refugio.setTelefono(dto.getTelefono());
    }
}
