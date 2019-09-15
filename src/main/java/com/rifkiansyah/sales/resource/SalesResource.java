package com.rifkiansyah.sales.resource;

import com.rifkiansyah.sales.model.Sales;
import com.rifkiansyah.sales.repository.SalesRepository;
import com.rifkiansyah.sales.service.CodeGenService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.web.bind.annotation.*;

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
        List<Sales> sales = salesRepository.findByUsername(username);
        salesRepository.delete((Sales) sales);

        return true;
    }

    @PostMapping("/report/{itemcode}")
    public void generateItemReport(@PathVariable("itemcode") Long itemcode) throws IOException {
        final String CSV_FILE = "./sales-report.csv";

        List<Sales> temp = salesRepository.findByItemcode(itemcode);
        //should get result set.
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("Receipt ID", "Item Code", "Item Name", "Quantity", "Price", "User Name", "Transaction Time"));
        ) {
            csvPrinter.printRecord("1", "Sundar Pichai ♥", "CEO", "Google");
            csvPrinter.printRecord("2", "Satya Nadella", "CEO", "Microsoft");
            csvPrinter.printRecord("3", "Tim cook", "CEO", "Apple");

            csvPrinter.printRecord(Arrays.asList("4", "Mark Zuckerberg", "CEO", "Facebook"));

            csvPrinter.flush();
        }

    }

}
