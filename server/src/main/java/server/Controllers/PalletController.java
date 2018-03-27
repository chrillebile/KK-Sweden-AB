package server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.Models.Pallet;
import server.Repositories.PalletRepository;
import server.Resources.PalletResource;

@RestController
@RequestMapping(value = "/pallets")
public class PalletController {
    private final PalletRepository palletRepository;

    @Autowired
    public PalletController(PalletRepository palletRepository) {
        this.palletRepository = palletRepository;
    }

    @RequestMapping(value = "/{id}")
    public ResponseEntity<PalletResource> getPalletById(@PathVariable("id") String id) {
        Pallet pallet = palletRepository.getPallet(Integer.parseInt(id));

        return new ResponseEntity<>(new PalletResource(pallet), HttpStatus.OK);
    }
}
