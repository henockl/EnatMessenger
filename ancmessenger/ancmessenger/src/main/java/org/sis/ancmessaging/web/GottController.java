package org.sis.ancmessaging.web;

import java.util.ArrayList;
import java.util.List;
import org.sis.ancmessaging.domain.Gott;
import org.sis.ancmessaging.domain.HealthPost;
import org.sis.ancmessaging.dto.GottDTO;
import org.sis.ancmessaging.json.CustomGenericResponse;
import org.sis.ancmessaging.json.CustomGottResponse;
import org.sis.ancmessaging.service.HealthPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/gott")
public class GottController { // NO_UCD
	@Autowired
	private HealthPostService healthPostService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMainPage(Model model) {
		List<HealthPost> healthPosts = healthPostService.getAll();
		model.addAttribute("healthPosts", healthPosts);
		GottDTO gottDTO = new GottDTO();
		model.addAttribute("gottDTO", gottDTO);
		return "gott";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String createGott(GottDTO gottDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("healthPosts", healthPostService.getAll());
			model.addAttribute("gottDTO", gottDTO);
			return "gott";
		} else {
			Gott gott = gottDTO.generateGott();
			HealthPost healthPost = healthPostService.findById(gottDTO.getPostId());
			healthPostService.addGottToHp(gott, healthPost);
			return "redirect:/healthpost/entries?pid=" + gottDTO.getPostId();
		}
		
	}
	
	@RequestMapping(value = "createajax", method = RequestMethod.POST)
	public @ResponseBody List<GottDTO> createGott(@RequestParam("postId") int postId, @RequestParam("gottName") String gottName) {
		Gott gott = new Gott();
		gott.setGottName(gottName);
		HealthPost healthPost = healthPostService.findById(postId);
		healthPostService.addGottToHp(gott, healthPost);
		List<Gott> gotts = healthPostService.getAllGottsForHealthPost(postId);
		List<GottDTO> gottDTOs = new ArrayList<GottDTO>();
		for (Gott g : gotts) {
			GottDTO gDTO = new GottDTO();
			gDTO.setGottId(g.getGottId());
			gDTO.setGottName(g.getGottName());
			gDTO.setPostId(postId);
			gottDTOs.add(gDTO);
		}
		return gottDTOs;
	}
	
	@RequestMapping(value = "getgotts", method = RequestMethod.GET)
	public @ResponseBody CustomGottResponse fetchGotts(@RequestParam("postId") int postId,
			@RequestParam("rows") int rows, @RequestParam("page") int page) {
		CustomGottResponse response = new CustomGottResponse();
		StringBuilder sb = new StringBuilder();
		//HealthPost healthPost = healthPostService.findById(postId);
		List<Gott> gotts = healthPostService.getGottsForHealthPost(postId, rows, page, sb);
		
		List<GottDTO> gottDTOs = new ArrayList<GottDTO>();
		for (Gott gott : gotts) {
			GottDTO gDTO = new GottDTO();
			gDTO.setGottId(gott.getGottId());
			gDTO.setGottName(gott.getGottName());
			gDTO.setPostId(postId);
			gottDTOs.add(gDTO);
		}
		response.setPage(String.valueOf(page));
		response.setRows(gottDTOs);
		response.setTotal(sb.toString());
		response.setRecords(String.valueOf(gottDTOs.size()));
		return response;
	}
	
	@RequestMapping(value = "getgottsajax", method = RequestMethod.GET)
	public @ResponseBody List<GottDTO> fetchGottsAjax(@RequestParam("postId") int postId) {
		HealthPost healthPost = healthPostService.findById(postId);
		List<Gott> gotts = healthPost.getGotts();
		List<GottDTO> gottDTOs = new ArrayList<GottDTO>();
		for (Gott gott : gotts) {
			GottDTO gDTO = new GottDTO();
			gDTO.setGottId(gott.getGottId());
			gDTO.setGottName(gott.getGottName());
			gDTO.setPostId(postId);
			gottDTOs.add(gDTO);
		}
		
		return gottDTOs;
	}
	
	@RequestMapping(value = "editgott", method = RequestMethod.POST)
	public @ResponseBody CustomGenericResponse editGott(
		@RequestParam("gottId") int gottId,	@RequestParam("gottName") String gottName) {
		
		Gott gott = healthPostService.findGottById(gottId);
		gott.setGottName(gottName);
		
		boolean result = healthPostService.persistGott(gott);
		CustomGenericResponse response = new CustomGenericResponse();
		
		if (result) {
			response.setMessage("Action Successful");
			response.setSuccess(true);
		} else {
			response.setMessage("Action Failed");
			response.setSuccess(false);
		}
		return response;
	}
}
