package server.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.Models.RawMaterial;

@RestController
@RequestMapping(value = "/rawMaterials")
public class RawMaterialController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RawMaterial> getAllMaterials(){
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RawMaterial> findMaterialById(@PathVariable("id") String id){
        return null;
    }
}
