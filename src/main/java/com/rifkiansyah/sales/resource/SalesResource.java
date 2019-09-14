package com.rifkiansyah.sales.resource;

import com.rifkiansyah.sales.model.Sales;
import com.rifkiansyah.sales.repository.SalesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/sales")
public class SalesResource {

    private SalesRepository salesRepository;

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

        Sales temp = new Sales();
        temp.setItemname(sales.getItemname());
        temp.setQuantity(sales.getQuantity());
        temp.setUsername(sales.getUsername());
        temp.setTransactionTime(sales.getTransactionTime());

        salesRepository.save(temp);
        return getSales(sales.getUsername());
    }

    @PostMapping("/delete/{username}")
    public boolean deleteSales(@PathVariable("username") String username){
        List<Sales> sales = salesRepository.findByUsername(username);
        salesRepository.delete((Sales) sales);

        return true;
    }
}
