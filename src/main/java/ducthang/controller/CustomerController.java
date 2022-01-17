package ducthang.controller;

import ducthang.model.Categories;
import ducthang.model.Customer;
import ducthang.service.ICategoriesService;
import ducthang.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @Autowired
    ICategoriesService categoriesService;

    @GetMapping("")
    public ModelAndView showAll(){
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("customers", customerService.findAllCustomer());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("createCustomer");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("categories", categoriesService.findAllCategories());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute(value = "customer") Customer customer, @RequestParam int idCategories, @RequestParam MultipartFile upImg){
        Categories categories = new Categories();
        categories.setIdCategories(idCategories);
        customer.setCategories(categories);

        String nameFile = upImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(upImg.getBytes(), new File("D:\\Module 4\\Customer-Management-Repository-JPA\\src\\main\\webapp\\WEB-INF\\image\\" + nameFile));
            customer.setImg("/image/"+nameFile);
            customerService.save(customer);
        } catch (IOException e) {
            customer.setImg("/image/jimmy-nguyen.png");
            customerService.save(customer);
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        if (customerService.findById(id).getImg()!=null){
            File file = new File("D:\\Module 4\\Customer-Management-Repository-JPA\\src\\main\\webapp\\WEB-INF\\" +customerService.findById(id).getImg());
            file.delete();
        }
        customerService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public ModelAndView showEdit(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("/editCustomer");
        modelAndView.addObject("customer", customerService.findById(id));
        modelAndView.addObject("categories", categoriesService.findAllCategories());
        return modelAndView;
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute(value = "customer") Customer customer, @RequestParam int idCategories, @RequestParam MultipartFile upImg) {
        Categories categories = new Categories();
        categories.setIdCategories(idCategories);
        customer.setCategories(categories);

        if (upImg.getSize() != 0) {
            String imgFile = upImg.getOriginalFilename();

            String nameDelete1 = "D:\\Module 4\\Customer-Management-Repository-JPA\\src\\main\\webapp\\WEB-INF\\"+ customer.getImg();
            File file = new File(nameDelete1);
            file.delete();
            try {
                FileCopyUtils.copy(upImg.getBytes(), new File("D:\\Module 4\\Customer-Management-Repository-JPA\\src\\main\\webapp\\WEB-INF\\image\\" + imgFile));
                customer.setImg("/image/"+ imgFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                FileCopyUtils.copy(upImg.getBytes(), new File("D:\\Module 4\\Customer-Management-Repository-JPA\\src\\main\\webapp\\WEB-INF\\image\\" + imgFile));
//                if (customer.getImg()!=null){
//                    File file = new File("D:\\Module 4\\Customer-Management-Repository-JPA\\src\\main\\webapp\\WEB-INF\\" + customer.getImg());
//                    file.delete();
//                }
//                customer.setImg("/image/"+imgFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        customerService.save(customer);
        return "redirect:/";
    }
}
