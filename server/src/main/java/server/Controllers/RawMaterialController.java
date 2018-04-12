package server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.DataResponse;
import server.Models.RawMaterial;
import server.Repositories.RawMaterialRepository;
import server.Resources.RawMaterialResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Raw Material.
 */
@RestController
@RequestMapping(value = "/rawMaterials")
public class RawMaterialController {

    private final RawMaterialRepository rawMaterialRepository;

    @Autowired
    public RawMaterialController(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    /**
     * Retrieve all raw materials.
     *
     * @return List of all raw materials as an api response.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getAllMaterials() {
        List<RawMaterial> rawMaterialList = rawMaterialRepository.getAllRawMaterial();
        List<RawMaterialResource> rawMaterialResourceList = new ArrayList<>();
        for (RawMaterial x : rawMaterialList) {
            rawMaterialResourceList.add(new RawMaterialResource(x));
        }
        return new ResponseEntity<>(new DataResponse(rawMaterialResourceList), HttpStatus.OK);
    }

    /**
     * Retrieve a given raw material.
     *
     * @param id Raw material ID.
     * @return Given raw material.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataResponse> findMaterialById(@PathVariable("id") String id) {
        RawMaterial rawMaterial = rawMaterialRepository.getRawMaterial(Integer.parseInt(id));
        return new ResponseEntity<>(new DataResponse(new RawMaterialResource(rawMaterial)), HttpStatus.OK);
    }
}
