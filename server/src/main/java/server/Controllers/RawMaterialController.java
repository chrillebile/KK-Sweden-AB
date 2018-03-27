package server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.Models.RawMaterial;
import server.Repositories.RawMaterialRepository;
import server.Resources.RawMaterialResource;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rawMaterials")
public class RawMaterialController {

    private final RawMaterialRepository rawMaterialRepository;

    @Autowired
    public RawMaterialController(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RawMaterialResource>> getAllMaterials(){
        List<RawMaterial> rawMaterialList = rawMaterialRepository.getAllRawMaterial();
        List<RawMaterialResource> rawMaterialResourceList = new ArrayList<>();
        for (RawMaterial x : rawMaterialList) {
            rawMaterialResourceList.add(new RawMaterialResource(x));
        }
        return new ResponseEntity<>(rawMaterialResourceList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RawMaterialResource> findMaterialById(@PathVariable("id") String id){
        RawMaterial rawMaterial = rawMaterialRepository.getRawMaterial(Integer.parseInt(id));
        return new ResponseEntity<>(new RawMaterialResource(rawMaterial), HttpStatus.OK);
    }
}
