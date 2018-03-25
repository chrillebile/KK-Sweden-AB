package server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.Models.Pallet;
import server.Repositories.PalletRepository;

@RestController
@RequestMapping(value = "/pallets")
public class PalletController {
    @Autowired
    private PalletRepository palletRepository;

    @RequestMapping(value = "/{id}")
    public ResponseEntity<Pallet> getPalletById(@PathVariable("id") String id) {
        return new ResponseEntity<>(palletRepository.getPallet(Integer.parseInt(id)), HttpStatus.OK);
    }
}
