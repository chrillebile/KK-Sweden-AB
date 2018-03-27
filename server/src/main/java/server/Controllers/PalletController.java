package server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.DataResponse;
import server.Models.Pallet;
import server.Repositories.PalletRepository;
import server.Resources.PalletResource;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/pallets")
public class PalletController {
    private final PalletRepository palletRepository;

    @Autowired
    public PalletController(PalletRepository palletRepository) {
        this.palletRepository = palletRepository;
    }

    /**
     * Retrieve all pallets saved in the database.
     *
     * @return
     */
    @RequestMapping(value = "")
    public ResponseEntity<DataResponse> getAllPallets() {
        List<Pallet> palletList = palletRepository.getPallets();

        List<PalletResource> palletResources = new ArrayList<>();
        for (Pallet pallet :
                palletList) {
            palletResources.add(new PalletResource(pallet));
        }

        return new ResponseEntity<>(new DataResponse(palletResources), HttpStatus.OK);
    }

    /**
     * Retrieve a single pallet, identified by it's id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}")
    public ResponseEntity<DataResponse> getPalletById(@PathVariable("id") String id) {
        Pallet pallet = palletRepository.getPallet(Integer.parseInt(id));

        return new ResponseEntity<>(new DataResponse(new PalletResource(pallet)), HttpStatus.OK);
    }
}
