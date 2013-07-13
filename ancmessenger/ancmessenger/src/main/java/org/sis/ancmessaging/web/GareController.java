package org.sis.ancmessaging.web;

import org.sis.ancmessaging.domain.Gare;
import org.sis.ancmessaging.domain.Gott;
import org.sis.ancmessaging.dto.GareDTO;
import org.sis.ancmessaging.service.HealthPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: henock
 * Date: 2/13/13
 * Time: 6:22 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/admin/gare")
public class GareController {
  @Autowired
  private HealthPostService healthPostService;

  @RequestMapping(value = "create", method = RequestMethod.POST)
  public String createGare(GareDTO gareDTO, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("healthPosts", healthPostService.getAll());

      model.addAttribute("gareDTO", gareDTO);
      return "gott";
    } else {
      Gare gare = gareDTO.generateGare();
      Gott gott = healthPostService.findGottById(gareDTO.getGareGottId());
      healthPostService.addGareToGott(gare, gott);
      return "redirect:/healthpost/entries?pid=" + gott.getHealthPost().getPostId();
    }

  }

  @RequestMapping(value = "createajax", method = RequestMethod.POST)
  public @ResponseBody
  List<GareDTO> createGare(@RequestParam("gareId") int gareId, @RequestParam("gareName") String gareName,
                           @RequestParam("gareGottId") int gareGottId) {
    Gare gare = new Gare();
    gare.setGareName(gareName);
    Gott gott = healthPostService.findGottById(gareGottId);
    healthPostService.addGareToGott(gare, gott);
    List<Gare> gares = healthPostService.getAllGaresForGott(gareGottId);
    List<GareDTO> gareDTOs = new ArrayList<GareDTO>();
    for (Gare g : gares) {
      GareDTO gDTO = new GareDTO();
      gDTO.setGareId(g.getGareId());
      gDTO.setGareName(g.getGareName());
      gDTO.setGareGottId(gareGottId);
      gareDTOs.add(gDTO);
    }
    return gareDTOs;
  }
}
