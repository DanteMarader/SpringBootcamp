package SpringBoot.SpringBootcamp.controllers;

import SpringBoot.SpringBootcamp.entities.Country;
import SpringBoot.SpringBootcamp.entities.Item;
import SpringBoot.SpringBootcamp.repositiries.CountryRepository;
import SpringBoot.SpringBootcamp.repositiries.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/item")
public class HomeController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CountryRepository countryRepository;

    @GetMapping(value = "/home")
    public String openHome(Model model) {

        model.addAttribute( "tovary", itemRepository.findAll());

        return "home";
    }

    @PostMapping(value = "/addItem")
    public String addItem(@RequestParam(name = "item_name") String name,
                          @RequestParam(name = "item_price") int price,
                          @RequestParam(name = "item_description") String description,
                          @RequestParam(name = "item_country") String country) {

        Item item = Item.builder()
                .name(name)
                .price(price)
                .description(description)
                .country(country)
                .sales(false)
                .build();

        itemRepository.save(item);

        return "redirect:home";
    }

    @GetMapping("/details/{id}")
    public String openDetailsItem(@PathVariable("id") Long id, Model model) {

        Item item = itemRepository.findAllById(id);
        model.addAttribute("tovar", item);

        return "details";
    }

    @PostMapping(value = "/update")
    public String updateItem(@RequestParam(name = "item_id") Long id ,
                             @RequestParam(name = "item_name") String name,
                             @RequestParam(name = "item_description") String description,
                             @RequestParam(name = "item_price") int price,
                             @RequestParam(name = "item_country") String country,
                             @RequestParam(name = "item_sales") boolean sales) {

        Item item = itemRepository.findAllById(id);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCountry(country);
        item.setSales(sales);

        itemRepository.save(item);

        return "redirect:home";
    }

    @GetMapping(value = "/add-country")
    public String openAddCountry() {
        return "add-country";
    }

    @PostMapping(value = "/add-country")
    public String addCountryPost(@RequestParam(name = "name_country") String name) {

        Country country = new Country();
        country.setName(name);

        countryRepository.save(country);

        return "redirect:home";
    }
    @PostMapping(value = "/delete")
    public String deleteItem(@RequestParam(name="item_delete") Long id){

        itemRepository.deleteById(id);

        return "redirect:home";
    }
}
