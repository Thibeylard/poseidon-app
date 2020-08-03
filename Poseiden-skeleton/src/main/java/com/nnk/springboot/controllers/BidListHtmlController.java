package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.BidListAddDTO;
import com.nnk.springboot.dtos.BidListUpdateDTO;
import com.nnk.springboot.exceptions.ResourceIdNotFoundException;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class BidListHtmlController {
    private final BidListService bidListService;

    @Autowired
    public BidListHtmlController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @RequestMapping("/html/bidList/list")
    public String home(Model model) {
        model.addAttribute("allBids", bidListService.findAll());
        return "bidList/list";
    }

    @GetMapping("/html/bidList/add")
    public String addBidForm(BidListAddDTO bid, Model model) {
        model.addAttribute("bidListAddDTO", bid);
        return "bidList/add";
    }

    @PostMapping("/html/bidList/validate")
    public String validate(@Valid BidListAddDTO bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bidListAddDTO", bid);
            return "bidList/add";
        } else {
            bidListService.save(bid);
            return "redirect:/html/bidList/list";
        }
    }

    @GetMapping("/html/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceIdNotFoundException {
        model.addAttribute("bidListUpdateDTO", bidListService.findById(id));
        return "bidList/update";
    }

    @PostMapping("/html/bidList/update")
    public String updateBid(@Valid BidListUpdateDTO bidListUpdateDTO,
                            BindingResult result, Model model) throws ResourceIdNotFoundException {
        if (result.hasErrors()) {
            model.addAttribute("bidListUpdateDTO", bidListUpdateDTO);
            return "bidList/update";
        } else {
            bidListService.update(bidListUpdateDTO);
            return "redirect:/html/bidList/list";
        }
    }

    @GetMapping("/html/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) throws ResourceIdNotFoundException {
        bidListService.delete(id);
        return "redirect:/html/bidList/list";
    }
}