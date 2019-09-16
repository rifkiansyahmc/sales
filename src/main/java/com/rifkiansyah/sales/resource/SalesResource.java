package com.rifkiansyah.sales.resource;

import com.rifkiansyah.sales.model.Sales;
import com.rifkiansyah.sales.repository.SalesRepository;
import com.rifkiansyah.sales.service.CodeGenService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.aspectj.apache.bcel.classfile.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/sales")
public class SalesResource {

    @Autowired
    RestTemplate restTemplate;

    private final Logger log = LoggerFactory.getLogger(SalesResource.class);

    private SalesRepository salesRepository;
    private final CodeGenService codeGenService = new CodeGenService();

    public SalesResource(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @GetMapping("/{username}")
    public List<String> getSales(@PathVariable("username")
                                             String username){

        return salesRepository.findByUsername(username)
                .stream()
                .map(Sales::getItemname)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public List<String> addSales(@RequestBody Sales sales){
        log.debug("Request to add sales : {}", sales);
        String receipt = codeGenService.initGen("SLS");

        Sales temp = new Sales();
        temp.setItemname(sales.getItemname());
        temp.setQuantity(sales.getQuantity());
        temp.setUsername(sales.getUsername());
        temp.setTransactionTime(sales.getTransactionTime());
        temp.setItemcode(sales.getItemcode());
        temp.setReceiptId(receipt);
        temp.setPrice(sales.getPrice());

        salesRepository.save(temp);
        return getSales(sales.getUsername());
    }

    @PostMapping("/delete/{username}")
    public boolean deleteSales(@PathVariable("username") String username){
        log.debug("Request to delete all from username : {}", username);
        List<Sales> sales = salesRepository.findByUsername(username);
        salesRepository.delete((Sales) sales);

        return true;
    }

    @PostMapping("/report/{itemcode}")
    public void generateItemReport(@PathVariable("itemcode") Long itemcode) throws IOException {
        log.debug("Request to generate item report : {}", itemcode);
        final String CSV_FILE = "./sales-report.csv";

        List<Sales> temp = salesRepository.findByItemcode(itemcode);
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("ID", "Item Name", "Transaction Time", "Quantity", "User Name", "Item Code", "Receipt ID", "Price"));
        ) {
            csvPrinter.printRecords(temp);

            csvPrinter.flush();
        }

    }

    @PostMapping("/update-inventory")
    public void updateInventoryQuantity(Long quantity, Long itemcode){
        log.debug("Request to update quantity : {}", itemcode);
        //get update function from inventory service, pass the parameter above.
        ResponseEntity<List<String>> inventoryResponse = restTemplate.exchange("http://inventory-service/rest/inventory/" + itemcode, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<String>>() {
                });

        List<String> inventories = inventoryResponse.getBody();

    }

}
